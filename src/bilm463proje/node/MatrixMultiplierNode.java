package bilm463proje.node;

import bilm463proje.exception.ConnectionFailedException;
import bilm463proje.exception.InvalidRequestException;
import bilm463proje.node.request.NodeAcknowledgementRequest;
import bilm463proje.node.request.NodeConnectionRequest;
import bilm463proje.node.request.NodeRequest;
import bilm463proje.node.response.NodeAffirmativeResponse;
import bilm463proje.node.response.NodeResponseType;
import bilm463proje.server.DatagramPacketHelper;
import bilm463proje.server.request.ServerCalculateRequest;
import bilm463proje.server.request.ServerRequestType;
import bilm463proje.server.response.ServerResponse;
import bilm463proje.server.response.ServerResultResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Matrixin satır ve sütın çarpımını gerçekleştirecek Node sınıfıdır. Data
 * transferi için UDP kullanır
 *
 * @author 07051102,07051124,07050941
 */
public class MatrixMultiplierNode {

    private InetAddress connectionAddress;
    private int connectionPort;
    private InetAddress dataAddress;
    private int dataPort;
    private DatagramSocket socket;

    /**
     * Constructor
     *
     * @param connectionIp Bağlanılacak sunucu adresi
     * @param connectionPort Bağlanılacak sunucu portu
     * @throws UnknownHostException Bilinmeyen sunucu adresinde fırlatılacak
     * istisna sınıfı
     */
    public MatrixMultiplierNode(String connectionIp, int connectionPort) throws UnknownHostException {
        connectionAddress = InetAddress.getByName(connectionIp);
        this.connectionPort = connectionPort;
    }

    /**
     * Node'u socketini bağlantıya hazırlar.
     *
     * @throws SocketException socket oluşturma hatası
     * @throws UnknownHostException bilinmeyen sunucu hatası
     */
    private void prepareConnection() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
    }

    /**
     * Sunucuya bağlanma protokolünü çalıştırır.
     *
     * @throws ConnectionFailedException Bağlantı başarısız olduğunda
     * fırlatılır.
     */
    public void connect() throws ConnectionFailedException {
        try {
            prepareConnection();
            NodeConnectionRequest connectionRequest = new NodeConnectionRequest(connectionPort);
            sendNodeRequest(connectionRequest, connectionAddress, connectionPort);
            DatagramPacket packet = receiveDatagram(1024);
            dataAddress = packet.getAddress();
            dataPort = packet.getPort();
            NodeAffirmativeResponse affirmativeResponse = DatagramPacketHelper.convertDataToNodeResponse(packet.getData());
            if (!affirmativeResponse.getType().equals(NodeResponseType.OK)) {
                throw new ConnectionFailedException();
            }
            NodeAcknowledgementRequest acknowledgementRequest = new NodeAcknowledgementRequest(affirmativeResponse.getValue());
            sendNodeRequest(acknowledgementRequest, dataAddress, dataPort);
            System.out.println(socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() + " Nodu " + dataAddress + ":" + dataPort + " sunucuya bağlandı ");
        } catch (IOException | ClassNotFoundException ex) {
            throw new ConnectionFailedException();
        }
    }

    /**
     * İşlemci node'a gelen matrix çarpım işlemlerini dinler.
     */
    public void listen() {
        while (true) {
            try {
                DatagramPacket packet = receiveDatagram(1024);
                ServerCalculateRequest request = DatagramPacketHelper.convertDataToServerRequest(packet.getData());
                if (!request.getType().equals(ServerRequestType.CALCULATE)) {
                    throw new InvalidRequestException();
                }
                double[] column = request.getColumn();
                double[] row = request.getRow();
                int rowIndex = request.getRowIndex();
                int columnIndex = request.getColumnIndex();
                double result = multipleRowToColumn(row, column);
                ServerResultResponse response = new ServerResultResponse(rowIndex, columnIndex, result);
                sendServerResponse(response, dataAddress, dataPort);
                System.out.println(dataAddress.getHostAddress() + ":" + dataPort + "row:" + rowIndex + "column: " + columnIndex + " result: " + result);
            } catch (IOException | ClassNotFoundException | InvalidRequestException ex) {
                Logger.getLogger(MatrixMultiplierNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Sunucunun istediği matrix çarpımlarını udp socket ile gönderen fonksiyon
     *
     * @param response Gönderilecek cevap mesaj nesnesi
     * @param address Gönderilecek adres
     * @param port Gönderilecek port
     * @throws IOException Gönderme hatası
     */
    private void sendServerResponse(ServerResponse response, InetAddress address, int port) throws IOException {
        byte data[] = DatagramPacketHelper.convertObjectToByte(response);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }

    /**
     * İşlemci Node'un Connect ve ACK gibi mesajlarını sunucuya gönderir
     * @param request Gönderilecek istek mesaj nesnesi
     * @param address Gönderilecek adres
     * @param port Gönderilecek port
     * @throws IOException Gönderme hatası
     */
    private void sendNodeRequest(NodeRequest request, InetAddress address, int port) throws IOException {
        byte data[] = DatagramPacketHelper.convertObjectToByte(request);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }

    /**
     * Sunucudan datagram almaya yarayan fonksiyon
     * @param size datagram büyüklüğü 
     * @return alınan datagram paketi
     * @throws IOException datagram alırken oluşacak istisna
     */
    private DatagramPacket receiveDatagram(int size) throws IOException {
        byte[] data = new byte[size];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        return packet;
    }

    /**
     * gelen satır ile sütunu çaprmaya yarayan fonksiyon
     * @param row matrix satırı
     * @param column matrix sütunu
     * @return çarpım sonucu
     */
    private double multipleRowToColumn(double[] row, double[] column) {
        double result = 0;
        for (int i = 0; i < row.length; i++) {
            result += row[i] * column[i];
        }
        return result;
    }

}
