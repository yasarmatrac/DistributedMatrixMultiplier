package bilm463proje.server;

import java.io.IOException;

/**
 * Server başlangıç sınıfı
 *
 * @author 07051102,07051124,07050941
 */
public class ServerStarter {

    /**
     *
     * @param args [0]: clientların dinleneceği bağlantı portu [1]: nodeların
     * dinlenileceği bağlantı portu
     *
     * Default client port : 6789 Default node port : 9876
     *
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                int clientConnectionPort = Integer.valueOf(args[0]);
                int nodeConnectionPort = Integer.valueOf(args[0]);
                Server server = new Server(clientConnectionPort, nodeConnectionPort);
                new Thread(server::listenNode).start();
                new Thread(server::listenClient).start();
                System.out.println("server kuruldu");
            } catch (NumberFormatException ex) {
                System.err.println("geçersiz parametreler");
                System.err.println("server kurulamadı");
            } catch (IOException ex) {
                System.err.println("server kurulamadı");
            }
        } else if (args.length == 0) {
            try {
                Server server = new Server(6789, 9876);
                new Thread(server::listenNode).start();
                new Thread(server::listenClient).start();
                System.out.println("server kuruldu");
            } catch (IOException ex) {
                System.err.println("server kurulamadı");
            }
        }
        else{
             System.err.println("geçersiz parametreler");                
        }

    }
}
