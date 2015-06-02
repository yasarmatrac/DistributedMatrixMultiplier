package bilm463proje.server;

import bilm463proje.exception.NotEqualColumnRow;
import bilm463proje.exception.InvalidRequestException;
import bilm463proje.exception.ConnectionFailedException;
import bilm463proje.client.request.ClientAcknowledgementRequest;
import bilm463proje.client.request.ClientConnectionRequest;
import bilm463proje.client.request.ClientMatrixMultipleRequest;
import bilm463proje.client.request.ClientRequest;
import bilm463proje.client.request.ClientRequestType;
import bilm463proje.client.response.ClientAffirmativeResponse;
import bilm463proje.client.response.ClientErrorResponse;
import bilm463proje.client.response.ClientMatrixMultipleResponse;
import bilm463proje.client.response.ClientResponse;
import bilm463proje.exception.HasNoNodeException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Server - client arası bağlantıları multithread dinleyebilmesi için çalışan runnable
 * sınıf. Client istekleri bu sınıfın run fonksiyonunda dinlenilir
 *
 * @author 07051102,07051124,07050941
 */
class ServerClientWorker implements Runnable {

    private Socket connectionSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private MatrixMultipleCalculator calculator;

    /**
     *
     * @param calculator Matrix çarpınımın işlemci node'lara dağıtan sınıfın
     * refeansı
     * @param connectionSocket Client ile iletişimde olan socket
     * @throws IOException
     */
    public ServerClientWorker(MatrixMultipleCalculator calculator, Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        inputStream = new ObjectInputStream(connectionSocket.getInputStream());
        this.calculator = calculator;
    }

    /**
     * protocol çalıştırılır başarılı olursa client istekleri dinlenilir
     */
    @Override
    public void run() {
        try {
            startConnectionProtocol();
            System.out.println("Bağlandı :  " + connectionSocket.getInetAddress().getHostName() + ":" + connectionSocket.getPort());
            while (true) {
                try {
                    ClientMatrixMultipleRequest matrixMultipleRequest = (ClientMatrixMultipleRequest) receiveRequest();
                    if (!matrixMultipleRequest.getType().equals(ClientRequestType.MULTIPLY_MATRIX)) {
                        throw new InvalidRequestException();
                    }
                    Matrix matrixA = matrixMultipleRequest.getMatrixA();
                    Matrix matrixB = matrixMultipleRequest.getMatrixB();

                    if (matrixA.getColumnLength() != matrixB.getRowLength()) {
                        throw new NotEqualColumnRow();
                    }

                    MatrixMultipleTask task = new MatrixMultipleTask(matrixA, matrixB);
                    Matrix resultMatrix = calculator.calculateMatrixMultiple(task);
                    ClientMatrixMultipleResponse response = new ClientMatrixMultipleResponse(resultMatrix);
                    sendRespose(response);

                } catch (IOException ex) {
                    System.out.println("İstek alınırken hata oluştu - " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    System.out.println("Bağlantı Koptu - " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    break;
                } catch (InvalidRequestException | ClassNotFoundException ex) {
                    System.out.println("Hatalı istek alındı - " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    ClientErrorResponse response = new ClientErrorResponse("Hatalı istek alındı");
                    try {
                        sendRespose(response);
                    } catch (IOException ex1) {
                        System.out.println("Hata mesajı gönderilemedi Client: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    }
                } catch (HasNoNodeException ex) {
                    System.out.println("Node yok - " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    ClientErrorResponse response = new ClientErrorResponse("Hesaplayıcı nodelar yok.");
                    try {
                        sendRespose(response);
                    } catch (IOException ex1) {
                        System.out.println("Hata mesajı gönderilemedi Client: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    }
                } catch (NotEqualColumnRow ex) {
                    System.out.println("NotEqualColumnRow hatası Client:" + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    ClientErrorResponse response = new ClientErrorResponse("İlk matrix'in sütun sayısı ile diğer matrix'in satır sayısı eşit değil.");
                    try {
                        sendRespose(response);
                    } catch (IOException ex1) {
                        System.out.println("Hata mesajı gönderilemedi Client: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());
                    }
                }
            }
            //System.out.println("Bağlantı Sonlandı :  " + connectionSocket.getInetAddress().getHostName() + ":" + connectionSocket.getPort());
        } catch (ConnectionFailedException e) {
            System.out.println("Bağlantı başarısız :  " + connectionSocket.getInetAddress().getHostName() + ":" + connectionSocket.getPort());
        }
    }

    /**
     * İstemcinin sunucuya bağlantı protokolünü içerir
     *
     * @throws ConnectionFailedException protokol hatası veya bağlantı hatası
     * olduğunda fırlatılır
     */
    public void startConnectionProtocol() throws ConnectionFailedException {
        try {
            //Client Connection İsteği yolladı
            ClientConnectionRequest connectionRequest = (ClientConnectionRequest) receiveRequest();
            if (!connectionRequest.getType().equals(ClientRequestType.NEW_CONNECTION)) {
                throw new ConnectionFailedException();
            }
            //Server random sayı belirleyip OK mesajı yolluyor
            int randomValue = (int) (Math.random() * 1000000);
            ClientAffirmativeResponse affirmativeResponse = new ClientAffirmativeResponse(randomValue);
            sendRespose(affirmativeResponse);

            //Client yollanan random sayı ile ack mesajı yolluyor
            ClientAcknowledgementRequest acknowledgementRequest = (ClientAcknowledgementRequest) receiveRequest();
            if (!acknowledgementRequest.getType().equals(ClientRequestType.ACKNOWLEDGEMENT)) {
                throw new ConnectionFailedException();
            }
            if (acknowledgementRequest.getValue() != randomValue) {
                throw new ConnectionFailedException();
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionFailedException();
        }
    }

    /**
     * Client'tan istek mesajını alan fonksiyon
     *
     * @return Client'dan alınan isteği döndürür
     * @throws IOException okuma yazma hatası olduğunda fırlatılır
     * @throws ClassNotFoundException bilinmeyen sınıf alındığında oluşan hata
     * fırlatılır
     */
    public ClientRequest receiveRequest() throws IOException, ClassNotFoundException {
        return (ClientRequest) inputStream.readObject();
    }

    /**
     * Client'a cevap mesajını yollayan fonksiyon
     *
     * @param response clienta gönderilen cevap
     * @throws IOException okuma yazma hatası olduğunda fırlatılır
     */
    public void sendRespose(ClientResponse response) throws IOException {
        outputStream.writeObject(response);
    }

}
