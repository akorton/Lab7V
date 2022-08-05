package Interaction;


import Client.Client;
import Client.Interpretator;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class ServerInteraction implements UserInteraction {
    InetAddress inetAddr;
    int port;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buff;
    private static final Logger log = Logger.getLogger(Client.class.getName());


    public ServerInteraction(DatagramSocket socket, InetAddress inetAddr, int port) {
        this.socket = socket;
        this.inetAddr = inetAddr;
        this.port = port;
    }
;

    @Override
    public void print(boolean newLine, String message) {
        byte[] buff;
        try {
            if(newLine) {
                buff = (message+"\n").getBytes();
            } else {
                buff = message.getBytes();
            }
            packet = new DatagramPacket(buff, buff.length);
        } catch (Exception e) {}
    }

    @Override
    public String getData() {
        try {
            return new String(packet.getData());
        } catch (Exception e) {
            return "";
        }
    }

    public void sendData(Object obj) throws IOException, SocketTimeoutException {
        try {
            socket.setSoTimeout(1000);
            buff = Interpretator.sender(obj);
            packet = new DatagramPacket(buff, buff.length, inetAddr, port);
            socket.send(packet);
        } catch (SocketTimeoutException e){
            log.info("Time is out! ");
        } catch (Exception e) {
            log.info("Connection lost! ");
        }


    }

    public Object readData() throws  IOException, SocketTimeoutException {
        socket.setSoTimeout(1000);
        byte[] buff = new byte[2000000];
        packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        Object obj = new Object();
        try {
            obj = Interpretator.receiver(packet.getData());
        } catch (SocketTimeoutException e){
            log.info("Time is out! ");
        } catch (Exception e) {
            log.info("Connection lost! ");
        }
        return obj;
    }
}
