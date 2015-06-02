/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bilm463proje.server;

import bilm463proje.node.request.NodeRequest;
import bilm463proje.node.response.NodeResponse;
import bilm463proje.server.request.ServerRequest;
import bilm463proje.server.response.ServerResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * DatagramPacket sınıfından okunan byte[] datayı nesneye, ve nesneyi byte[]
 * arrayine çeviren yardımcı sınıftır.
 *
 * @author 07051102,07051124,07050941
 */
public class DatagramPacketHelper {

    /**
     *
     * Çevirme işleminin olabilmesi için @param object 'in Serializable olması
     * gereklidir
     *
     * @param object byte[] arrayine çevirilecek nesne
     * @return objenin byte[] array hali
     * @throws IOException çevirme sırasında olabilecek hata sonucu
     */
    public static byte[] convertObjectToByte(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     *
     * @param <T> Generic sınıf Object sınıfından miras alır
     * @param data Generic sınıfa çevirilecek byte[] arrayi
     * @return okunan byte[] arrayi döndürülen nesneye çevilir
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak istisna
     */
    public static <T extends Object> T convertDataToObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (T) objectInputStream.readObject();
    }

    /**
     *
     * @param <T> Generic sınıf NodeRequest sınıfından miras alır
     * @param data Generic sınıfa çevirilecek byte[] arrayi
     * @return okunan byte[] arrayi döndürülen nesneye çevilir
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak istisna
     */
    public static <T extends NodeRequest> T convertDataToNodeRequest(byte[] data) throws IOException, ClassNotFoundException {
        return convertDataToObject(data);
    }

    /**
     *
     * @param <T> Generic sınıf NodeResponse sınıfından miras alır
     * @param data Generic sınıfa çevirilecek byte[] arrayi
     * @return okunan byte[] arrayi döndürülen nesneye çevilir
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak istisna
     */
    public static <T extends NodeResponse> T convertDataToNodeResponse(byte[] data) throws IOException, ClassNotFoundException {
        return convertDataToObject(data);
    }

    /**
     *
     * @param <T> Generic sınıf ServerRequest sınıfından miras alır
     * @param data Generic sınıfa çevirilecek byte[] arrayi
     * @return okunan byte[] arrayi döndürülen nesneye çevilir
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak istisna
     */
    public static <T extends ServerRequest> T convertDataToServerRequest(byte[] data) throws IOException, ClassNotFoundException {
        return convertDataToObject(data);
    }

    /**
     *
     * @param <T> Generic sınıf ServerResponse sınıfından miras alır
     * @param data Generic sınıfa çevirilecek byte[] arrayi
     * @return okunan byte[] arrayi döndürülen nesneye çevilir
     * @throws IOException Okuma hatası olduğunda fırlatılacak istisna
     * @throws ClassNotFoundException Bilinmeyen sınıf olduğunda fırlatılacak istisna
     */
    public static <T extends ServerResponse> T convertDataToServerResponse(byte[] data) throws IOException, ClassNotFoundException {
        return convertDataToObject(data);
    }

}
