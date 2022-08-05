package Client;

import java.io.*;

public class Interpretator implements Serializable{

    public static Object  receiver(byte[] bytes) throws Exception{
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        return objectStream.readObject();
    }


    public static byte[] sender(Object o) throws Exception{

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);
        try {
            outputStream.writeObject(o);
        } catch (Exception e) {
            System.err.println(e);
        }

        return byteStream.toByteArray();

    }
}
