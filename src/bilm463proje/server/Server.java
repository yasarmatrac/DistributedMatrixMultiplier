package bilm463proje.server;

import bilm463proje.exception.ConnectionFailedException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Sunucu nesnesi. Hesaplama yapmak isteyen işlemciler ve işlem yapılacak
 * nodeların bağlantıları burada handle edilir. 
 *
 * @author 07051102,07051124,07050941
 */
public class Server {

    private boolean running = true;
    private int clientConnectionPort;
    private int nodeConnectionPort;

    private DatagramSocket nodeServerSocket;
    private ServerSocket clientServerSocket;
    private NodeConnectionManager nodeConnectionManager;
    private MatrixMultipleCalculator calculator;

    /**
     * 
     * @param clientConnectionPort clientların dinleneceği bağlantı portu
     * @param nodeConnectionPort nodeların dinlenileceği bağlantı portu
     * @throws IOException  socket oluşumnda olaşabilecek hata
     */
    public Server(int clientConnectionPort, int nodeConnectionPort) throws IOException {
        this.clientConnectionPort = clientConnectionPort;
        this.nodeConnectionPort = nodeConnectionPort;
        clientServerSocket = new ServerSocket(clientConnectionPort);
        nodeServerSocket = new DatagramSocket(nodeConnectionPort);
        nodeConnectionManager = new NodeConnectionManager();
        calculator = new MatrixMultipleCalculator(nodeConnectionManager);
    }

    /**
     * Client bağlantıları bu fonksiyonda dinlenilir
     */
    public void listenClient() {
        System.out.println("Client bağlantısı hazır");
        ExecutorService executor = Executors.newWorkStealingPool();
        while (running) {
            Socket connectionSocket = null;
            try {
                connectionSocket = clientServerSocket.accept();
                ServerClientWorker runnable = new ServerClientWorker(calculator, connectionSocket);
                executor.submit(runnable);
            } catch (IOException e) {
                System.out.println("Client bağlantısı oluşturulamadı " + connectionSocket.getInetAddress().getHostAddress());
            }
        }
    }

    /**
     * İşlemci Node bağlantıları bu fonksiyonda dinlenilir
     */
    public void listenNode() {
        System.out.println("Node bağlantısı hazır");
        while (running) {
            DatagramPacket incomingPacket = null;
            try {
                byte[] incomingData = new byte[1024];
                incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                nodeServerSocket.receive(incomingPacket);
                new Thread(new ServerNodeConnectionWorker(nodeConnectionManager, incomingPacket)).start();
            } catch (IOException | ConnectionFailedException ex) {
                System.err.println(incomingPacket.getAddress().getHostAddress() + incomingPacket.getPort() + " bağlantısında sorun oluştu");
            }
        }
    }

}
