package bilm463proje.server;

import bilm463proje.exception.ConnectionFailedException;
import bilm463proje.node.request.NodeAcknowledgementRequest;
import bilm463proje.node.request.NodeConnectionRequest;
import bilm463proje.node.request.NodeRequest;
import bilm463proje.node.request.NodeRequestType;
import bilm463proje.node.response.NodeAffirmativeResponse;
import bilm463proje.node.response.NodeResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Server - işlemci node arası bağlantıları multithread dinleyebilmesi için
 * çalışan runnable sınıf. Node istekleri bu sınıfın run fonksiyonunda
 * dinlenilir
 *
 * @author 07051102,07051124,07050941
 */
public class ServerNodeConnectionWorker implements Runnable {

    private NodeConnectionManager nodeConnectionManager;
    private DatagramSocket socket;
    private InetAddress nodeInetAddres;
    private int nodePort;
    private DatagramPacket datagramPacket;

    /**
     *
     * @param nodeConnectionManager node bağlantı yöneticisi referansı
     * @param datagramPacket node bağlantısı oluşturulurken alınan ilk paket, bu
     * paket sayesinde istek yollayan node adresini ve portunu öğreniriz
     * @throws SocketException
     * @throws ConnectionFailedException
     */
    public ServerNodeConnectionWorker(NodeConnectionManager nodeConnectionManager, DatagramPacket datagramPacket) throws SocketException, ConnectionFailedException {
        this.nodeConnectionManager = nodeConnectionManager;
        this.socket = new DatagramSocket();
        this.nodeInetAddres = datagramPacket.getAddress();
        this.nodePort = datagramPacket.getPort();
        this.datagramPacket = datagramPacket;
    }

    /**
     * Protokol çalıştırılır. bağlantı sağlanır
     */
    @Override
    public void run() {
        try {
            System.out.println(nodeInetAddres.getHostAddress() + ":" + nodePort + " Node Bağlantı isteğinde bulundu.");
            startConnectionProtocol(this.datagramPacket);
            System.out.println(nodeInetAddres.getHostAddress() + ":" + nodePort + " Node Bağlantısı gerçekleşti.");
        } catch (ConnectionFailedException ex) {
            System.out.println(nodeInetAddres.getHostAddress() + ":" + nodePort + " Node Bağlantı isteği başarısız.");
        }
    }

    /**
     * İşlemci node ile çalıştırılacak protokol
     *
     * @param datagramPacket gelen ilk istek paketi referansı
     * @throws ConnectionFailedException protokole uyulmadığında oluşturulacak
     */
    private void startConnectionProtocol(DatagramPacket datagramPacket) throws ConnectionFailedException {
        try {
            NodeConnectionRequest connectionRequest = DatagramPacketHelper.convertDataToObject(datagramPacket.getData());
            if (!connectionRequest.getType().equals(NodeRequestType.CONNECT)) {
                throw new ConnectionFailedException();
            }

            int randomValue = (int) (Math.random() * 10000);
            NodeAffirmativeResponse affirmativeResponse = new NodeAffirmativeResponse(connectionRequest.getConnectionId(), randomValue);
            sendNodeResponse(affirmativeResponse);
            NodeAcknowledgementRequest acknowledgementRequest = (NodeAcknowledgementRequest) receiveRequest();
            if (!acknowledgementRequest.getType().equals(NodeRequestType.ACK)) {
                throw new ConnectionFailedException();
            }
            if (acknowledgementRequest.getValue() != randomValue) {
                throw new ConnectionFailedException();
            }
            NodeConnection connection = new NodeConnection(socket, datagramPacket.getAddress(), datagramPacket.getPort());
            nodeConnectionManager.addNodeConnection(connection);
        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionFailedException();
        }
    }

    /**
     * İşlemci node'dan gelen istekleri yakalamaya yarayan fonksiyon
     *
     * @return İşlemci node'dan gelen istek mesajı döndürülür
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak
     * istisna
     */
    public NodeRequest receiveRequest() throws IOException, ClassNotFoundException {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        return DatagramPacketHelper.convertDataToNodeRequest(packet.getData());
    }

    /**
     * İşlemci node'a mesaj gönderen fonksiyon
     *
     * @param response İşlemci sunucuya yollanacak cevap mesajı
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     */
    public void sendNodeResponse(NodeResponse response) throws IOException {
        byte[] data = DatagramPacketHelper.convertObjectToByte(response);
        DatagramPacket packet = new DatagramPacket(data, data.length, nodeInetAddres, nodePort);
        socket.send(packet);
    }
}
