package Server;

import Client.Interpretator;
import Client.Client;
import Database.Database;
import Xml.Xml;
import Commands.*;
import Movie.*;
import Interaction.*;
import com.sun.rmi.rmid.ExecPermission;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Server {

    private static final UserInteraction interaction = new ConsoleInteraction();
    private static Hashtable<String, Movie> collection = new Hashtable<>();
    public static File file;
    private static DatagramChannel serverSocket;
    public static final int port = 7182;
    private static LocalDateTime creationDate;
    private static LocalDateTime initDate;
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static SocketAddress remoteAddr;
    private static byte[] buffBytes;

    public static void main(String[] args) throws Exception {
        try {
            log.info("Connecting to database...");
            Database.connectToDatabase();
            log.info("Successfully connected!!!\n");
        } catch (SQLException e){
            interaction.print("Some error occurred while trying to connect to database!\n" +
                    "WARNING!!!\nNone of the changes of this session won't be consistent(saved)!");
        }

        try {
            log.info("Uploading collection from database...");
            collection = Database.getCollection();
            log.info("Successfully loaded!");
        } catch (Exception e){
            e.printStackTrace();
            interaction.print("Some error occurred while uploading collection from database!");
        }

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(Xml.toXml(new HashtableInfo(collection, creationDate)));
                    fileWriter.flush();
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
                log.info("Server stop.");
            }));
        } catch (Exception e) {
            log.info("Failed to set exit condition.");
            return;
        }


        buffBytes = new byte[2000000];
        SocketAddress addr;

        try {
            addr = new InetSocketAddress("localhost", port);
            serverSocket = DatagramChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.bind(addr);
        } catch (IOException e) {
            log.info(String.format("Unable to start server (%s)%n", e.getMessage()));
            return;
        }

        log.info("Server launched at: " + addr);
        ExecutorService poolRead = Executors.newCachedThreadPool();
        ExecutorService poolExecute = Executors.newCachedThreadPool();
        ExecutorService poolSend = ForkJoinPool.commonPool();
        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_READ);
        try {
            while (true) {
                if (selector.select() == 0) continue;
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (Iterator<SelectionKey> it = keySet.iterator(); it.hasNext(); ) {
                    SelectionKey sk = it.next();
                    it.remove();
                    if (sk.isReadable()) {
                        sk.interestOps(sk.interestOps() & ~SelectionKey.OP_READ);
                        Future<Command> command = poolRead.submit(() -> readInfoFromClient());
                        poolExecute.submit(() -> executeCommand(command, sk));
                    }
                    if (sk.isWritable()) {
                        sk.interestOps(sk.interestOps() & ~SelectionKey.OP_WRITE);
                        System.out.println(1);
                        poolSend.submit(() -> sendMessageToClient((Message) sk.attachment(), sk));
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            log.info("Connection lost!!!");
        }
    }

    private static void uploadInformation() throws FileNotFoundException, IllegalAccessException, NoSuchFieldException {
        log.info("Uploading file " + file);
        HashtableInfo hashtableInfo = Xml.fromXml(file);
        collection = Objects.requireNonNull(hashtableInfo).getCollection();
        creationDate = hashtableInfo.getCreationDate();
        log.info("Collection upload successfully!\n");
    }

    private static boolean prepare() {
        try {
            uploadInformation();
        } catch (FileNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            if (e instanceof NoSuchFieldException || e instanceof IllegalAccessException || e instanceof NullPointerException) {
                log.info("Problems processing the file. Data not read. We create a new file.");
            }
            initDate = LocalDateTime.now();
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.close();
            } catch (IOException ex) {
                log.info("The file could not be created, there are insufficient permissions, or the format of the file name is incorrect.");
                log.info("Error message: " + ex.getMessage());
                return false;
            }
        }
        return true;
    }

    private static Command readInfoFromClient(){
        ByteBuffer buffRead = ByteBuffer.wrap(buffBytes);
        Command command = null;
        try {
            do {
                remoteAddr = serverSocket.receive(buffRead);
            } while (remoteAddr == null);

            log.info(String.format("Client %s:%s connected!", remoteAddr, serverSocket.getLocalAddress()));

            command = (Command) Interpretator.receiver(buffBytes);
            buffRead.clear();
            log.info("Entered command " + command);
        } catch (Exception e){
            e.printStackTrace();
        }
        return command;
    }

    private static Message executeCommand(Future<Command> commandFuture, SelectionKey selectionKey) {
        while (!commandFuture.isDone()) {
        }
        Message message = null;
        try {
            Command command = commandFuture.get();
            log.info(command + " is being executed.");
            if (command instanceof Date) {
                message = ((Date) command).execute(collection, initDate);
            } else {
                message = command.execute(collection);
            }
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.attach(message);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            selectionKey.selector().wakeup();
        }
        return message;
    }

    private static void sendMessageToClient(Message message, SelectionKey selectionKey) {
        try {
            ByteBuffer buffSend;
            byte[] answer = Interpretator.sender(message);
            buffSend = ByteBuffer.wrap(answer);
            serverSocket.send(buffSend, remoteAddr);
            log.info("Response send.");
            buffSend.clear();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            selectionKey.interestOps(SelectionKey.OP_READ);
            selectionKey.selector().wakeup();
        }
    }
}
