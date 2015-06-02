package bilm463proje.server;

import bilm463proje.server.request.ServerCalculateRequest;
import bilm463proje.server.response.ServerResultResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * İşlemci node bağlantılarını ve socketini içeren sınıf. İşlemci Node'a matrix
 * işlemleri istekler bu sınıf üzerinden yollanır ve cevaplar buradan alınır.
 *
 * @author 07051102,07051124,07050941
 */
public class NodeConnection {

    private boolean available;
    private DatagramSocket socket;
    private InetAddress nodeAddress;
    private int nodePort;

    /**
     *
     * @param socket işlemci node'a veri gönderen sunucu udp socketi
     * @param nodeAddress işlemci node'un addres sınıfı
     * @param nodePort işlemci node'un bağlantı portu
     */
    public NodeConnection(DatagramSocket socket, InetAddress nodeAddress, int nodePort) {
        this.socket = socket;
        this.nodeAddress = nodeAddress;
        this.nodePort = nodePort;
    }

    /**
     *
     * @return İşlemci node'un addres sınıfı
     */
    public String getNodeHost() {
        return nodeAddress.getHostAddress();
    }

    /**
     *
     * @return işlemci node'un bağlantı portu
     */
    public int getNodePort() {
        return nodePort;
    }

    /**
     * Node bağlantısının müsaitlik bilgisini döndürür.
     *
     * @return bağlantı müsaitlik bilgisi
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Bağlantı müsaitlik bilgisi set edilir
     *
     * @param available set edilecek müsaitlik bilgisi
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nodePort;
        hash += getNodeHost().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NodeConnection)) {
            return false;
        }
        NodeConnection other = (NodeConnection) object;
        return this.getNodeHost().equals(other.getNodeHost()) && this.getNodePort() == other.getNodePort();
    }

    /**
     * İşlemci node'a satır sütun çarpımı bu fonksiyon ile yollanır
     *
     * @param request Satır sütun çarpım isteği
     * @throws IOException packet yollanırken oluşacak hata
     */
    public void sendMultipleRequest(ServerCalculateRequest request) throws IOException {
        byte[] data = DatagramPacketHelper.convertObjectToByte(request);
        DatagramPacket packet = new DatagramPacket(data, data.length, nodeAddress, nodePort);
        socket.send(packet);
    }

    /**
     *
     * @param size datagram boyutu
     * @return işlemci node'dan alınan datagram paketi
     * @throws IOException paket alınırken bağlantı hatası
     * @throws ClassNotFoundException bilinmeyen sınıf hatası
     */
    private DatagramPacket receiveDatagram(int size) throws IOException, ClassNotFoundException {
        byte[] data = new byte[size];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        return packet;
    }

    /**
     *
     * @return işlemci node'dan alınan cevap
     * @throws IOException paket alınırken bağlantı hatası
     * @throws ClassNotFoundException bilinmeyen sınıf hatası
     */
    public ServerResultResponse receiveMultipleResponse() throws IOException, ClassNotFoundException {
        DatagramPacket packet = receiveDatagram(1024);
        ServerResultResponse response = DatagramPacketHelper.convertDataToServerResponse(packet.getData());
        return response;
    }

}
