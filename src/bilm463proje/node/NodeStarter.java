package bilm463proje.node;

import bilm463proje.exception.ConnectionFailedException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * İşlemci nodu başlatma sınıfı
 *
 * @author 07051102,07051124,07050941
 */
public class NodeStarter {

    /**
     * İşlemci nodu başlatma fonksiyonu Sunucu adres ve portu girilir. Girilmez
     * ise default localhost:9876
     *
     * @param args args[0]: Sunucu adresi, args[1]: Sunucu portu, args[2]:
     * Oluşturulacak Node Sayısı
     *
     * Default Sunucu adresi : 'localhost' Default Sunucu portu : 9876 Default
     * oluşturulacak node sayısı: 1
     */
    public static void main(String[] args) {
        if (args.length == 3) {
            String serverURL = args[0];
            int serverConnectionPort = Integer.valueOf(args[1]);
            int nodeAmount = Integer.valueOf(args[2]);
            try {
                for (int i = 0; i < nodeAmount; i++) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                MatrixMultiplierNode node = new MatrixMultiplierNode(serverURL, serverConnectionPort);
                                node.connect();
                                node.listen();
                            } catch (UnknownHostException | ConnectionFailedException ex) {
                                Logger.getLogger(NodeStarter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start();
                }
            } catch (NumberFormatException ex) {
                System.err.println("Geçersiz parametre");
            }
        } else if (args.length == 0) {
            try {
                MatrixMultiplierNode node = new MatrixMultiplierNode("localhost", 9876);
                node.connect();
                node.listen();
            } catch (UnknownHostException | ConnectionFailedException ex) {
                Logger.getLogger(NodeStarter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
