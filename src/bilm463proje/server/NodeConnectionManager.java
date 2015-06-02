package bilm463proje.server;

import bilm463proje.exception.HasNoNodeException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * İşlemci node bağlantılarının yöneten sınıf. yeni bağlantı ekleme, bağlantı
 * çıkarma, bağlantıyı kullanak için alma ve geri bırakma işlemlerini yapar
 *
 * @author 07051102,07051124,07050941
 */
public class NodeConnectionManager {

    private Lock persistenceLock;
    private List<NodeConnection> nodeConnections;
    private Lock nodeStateLock;
    private Condition nodeStateCondition;
    private int availableConnectionSize = 0;

    /**
     * Default contructorß
     */
    public NodeConnectionManager() {
        nodeConnections = new ArrayList<>();
        persistenceLock = new ReentrantLock();
        nodeStateLock = new ReentrantLock();
        nodeStateCondition = nodeStateLock.newCondition();
    }

    /**
     * Yeni bağlantı eklemeye yarar. synchronized fonksiyondur
     *
     * @param nodeConnection eklenecek bağlantı
     */
    public void addNodeConnection(NodeConnection nodeConnection) {
        persistenceLock.lock();
        nodeStateLock.lock();
        try {
            nodeConnection.setAvailable(true);
            nodeConnections.add(nodeConnection);
            availableConnectionSize++;
            nodeStateCondition.signalAll();

        } finally {
            nodeStateLock.unlock();
            persistenceLock.unlock();
        }
        System.out.println("Node eklendi " + nodeConnection.getNodeHost() + ":" + nodeConnection.getNodePort());
    }

    /**
     * Verilen bağlantıyı eklemeye yarar. synchronized fonksiyondur
     *
     * @param nodeConnection silinecek bağlantı
     */
    public void removeConnection(NodeConnection nodeConnection) {
        persistenceLock.lock();
        try {
            nodeConnections.remove(nodeConnection);
            if (nodeConnection.isAvailable() == true) {
                nodeStateLock.lock();
                try {
                    availableConnectionSize--;
                } finally {
                    nodeStateLock.unlock();
                }
            }
        } finally {
            persistenceLock.unlock();
        }
        System.out.println("Node silindi " + nodeConnection.getNodeHost() + ":" + nodeConnection.getNodePort());
    }

    /**
     *
     * @return mevcut olan bağlantı sayıları.
     */
    public int connectionSize() {
        return nodeConnections.size();
    }

    /**
     * Müsait olan bağlantıyı döndürür ve durumunu notAvailable yapar.
     *
     * @return müsait olan bağlantı
     * @throws HasNoNodeException bağlantı olmadığında fırlatılacak istisna
     * @throws InterruptedException thread interrupt hatası
     * nodeStateCondition.await fonksionunda atılabilir
     */
    public NodeConnection takeConnection() throws HasNoNodeException, InterruptedException {
        if (connectionSize() == 0) {
            throw new HasNoNodeException();
        }
        nodeStateLock.lock();
        while (availableConnectionSize == 0) {
            try {
                nodeStateCondition.await(15, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                nodeStateLock.unlock();
                throw ex;
            }
            if (connectionSize() == 0) {
                nodeStateLock.unlock();
                throw new HasNoNodeException();
            }
        }
        availableConnectionSize--;
        persistenceLock.lock();
        NodeConnection nodeConnection = null;
        try {
            for (NodeConnection nc : nodeConnections) {
                if (nc.isAvailable()) {
                    nc.setAvailable(false);
                    nodeConnection = nc;
                    break;
                }
            }
        } finally {
            persistenceLock.unlock();
            nodeStateLock.unlock();
        }
        return nodeConnection;
    }

    /**
     * Alınan bağlantıyı serbest bırakır durmunu available olur.
     *
     * @param connection serbest bırakılacak node bağlantısı
     */
    public void leaveConnection(NodeConnection connection) {
        nodeStateLock.lock();
        try {
            connection.setAvailable(true);
            availableConnectionSize++;
            nodeStateCondition.signalAll();
        } finally {
            nodeStateLock.unlock();
        }
    }
}
