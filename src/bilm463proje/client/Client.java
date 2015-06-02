package bilm463proje.client;

import bilm463proje.exception.ConnectionFailedException;
import bilm463proje.client.request.ClientAcknowledgementRequest;
import bilm463proje.client.request.ClientConnectionRequest;
import bilm463proje.client.response.ClientResponse;
import bilm463proje.client.request.ClientRequest;
import bilm463proje.client.response.ClientAffirmativeResponse;
import bilm463proje.client.response.ClientResponseType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * İstemci sınıftır. İstemciden gelen bağlantı ve matrix hesaplama isteklerini
 * sunucuya iletir. TCP kullanır
 *
 * @author 07051102,07051124,07050941
 */
public class Client {

    private String connectionIp;
    private int connectionPort;
    private Socket socket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     * İstemci nesnesinin yapıcı sınıfıdır.
     *
     * @param connectionIp Sunucunun bağlantı adresi
     * @param connectionPort Sunucunun bağlantı portu
     */
    public Client(String connectionIp, int connectionPort) {
        this.connectionIp = connectionIp;
        this.connectionPort = connectionPort;
    }

    /**
     * İstemci nesnesinin bağlantısını hazırlar. Yeni socket oluşturup, socketin
     * verdiği I/O streamleri ObjectI/O Stream'e çevirir
     *
     * @throws IOException I/O bağlantısı oluşmadığı zaman fırlatılır
     */
    private void prepareConnection() throws IOException {
        socket = new Socket(connectionIp, connectionPort);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

    }

    /**
     * Sunucuya bağlanır. Bağlanma protokolü çalışır.
     *
     * @throws ConnectionFailedException Başarısız bağlantıda atılır.
     */
    public void connect() throws ConnectionFailedException {
        try {
            prepareConnection();
            //Bağlantı isteği hazırlama
            ClientConnectionRequest connectionRequest = new ClientConnectionRequest();
            sendRequest(connectionRequest);
            //Serverdan ok mesajı alma
            ClientAffirmativeResponse affirmativeResponse = (ClientAffirmativeResponse) receiveResponse();
            if (!affirmativeResponse.getType().equals(ClientResponseType.OK)) {
                throw new ConnectionFailedException();
            }
            int randomValue = affirmativeResponse.getValue();
            //Servera ACK mesajı yollama
            ClientAcknowledgementRequest acknowledgementRequest = new ClientAcknowledgementRequest(randomValue);
            sendRequest(acknowledgementRequest);

        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionFailedException();
        }
    }

    /**
     * Sunucuya istek gönderimini sağlar
     *
     * @param request Sunucuya gönderilmesi istenen istek.
     * @throws IOException Sunucuya istek gönderilemediğinde fırlatılır
     */
    public void sendRequest(ClientRequest request) throws IOException {
        outputStream.writeObject(request);
    }

    /**
     *
     * @return Sunucudan gelen istekleri dinler ve ClientResponse nesnesi olarak
     * dönüştürür.
     * @throws IOException Sunucuya gönderilmesi istenen istek.
     * @throws ClassNotFoundException Sunucudan gelen cevabın tipinin beklenen
     * cevapla eşit olmadığında fırlatılır.
     */
    public ClientResponse receiveResponse() throws IOException, ClassNotFoundException {
        return (ClientResponse) inputStream.readObject();
    }

}
