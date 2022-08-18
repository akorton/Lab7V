package Client;

import Commands.*;
import Interaction.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client {
    private static final UserInteraction interaction = new ConsoleInteraction();
    private static ServerInteraction serverInteraction;
    private static int port;
    private static DatagramSocket clientSocket;
    private static InetAddress inetAddr;
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static int userId;


    public static void main(String[] args) {
        try {
            String[] argument = args[0].split(":");
            if (argument.length != 1) {
                throw new Exception();
            }
            port = Integer.parseInt(argument[0]);
        } catch (Exception e) {
            log.info("Incorrect address or port specified. Enter in format '*.*.*.*:****'");
            return;
        }
        connect();
        run();
    }


    public static void connect() {
        try {
            inetAddr = InetAddress.getByName("localhost");
            clientSocket = new DatagramSocket();
            serverInteraction = new ServerInteraction(clientSocket, inetAddr, port);
            clientSocket.connect(new InetSocketAddress(inetAddr, port));
        } catch (Exception e) {
            log.info("Failed connection to server " + e.getMessage());
            }
    }

    public static void run() {
        interaction.print(true, "For a complete list of commands, type 'help'.");
        boolean run = true;
        boolean reconnect = false;
        while (run) {

            try {
                if (reconnect | clientSocket.isClosed() | !clientSocket.isConnected()) {
                    connect();
                    reconnect = false;
                }
                interaction.print(false, "\nEnter command: ");
                String potencialCommand = interaction.getData();

                if (potencialCommand == null) {
                    continue;
                }


                CommandObject command = CommandExecution.proceedCommand(potencialCommand, false, interaction);
                if (command == null) {
                    continue;
                }
                command.setUserId(userId);
                if (command instanceof Exit) {
                    return;
                }
                if (command instanceof Preprocessing) {
                    ((Preprocessing) command).preprocess(interaction);
                }
                if (command instanceof ExecuteScript) {
                    boolean result = true;

                    try {
                        Stream<String> stream = Files.lines(Paths.get(((ExecuteScript) command).getArgument()));
                        List<String> list = stream.collect(Collectors.toList());

                        String line;
                        int lineNum = 0;
                        while (!list.isEmpty()) {
                            line = list.get(0);
                            list.remove(0);
                            try {
                                ScriptInteraction scriptInteraction = new ScriptInteraction(list);
                                CommandObject command1 = CommandExecution.proceedCommand(line, true, scriptInteraction);
                                if (command1 == null) {
                                    continue;
                                }
                                command1.setUserId(userId);
                                if (command1 instanceof Exit) {
                                    return;
                                }
                                if (command1 instanceof Preprocessing) {
                                    ((Preprocessing) command1).preprocess(scriptInteraction);
                                }
                                try {
                                    serverInteraction.sendData(command1);
                                    Message message = (Message) serverInteraction.readData();
                                    if (!message.isSuccessful()) {
                                        throw new Exception();
                                    }

                                } catch (SocketTimeoutException e) {
                                    System.out.println("vremya vishlo");

                                }
                            } catch (Exception e) {
                                log.info("Error at " + lineNum);
                                result = false;
                                break;
                            }
                            lineNum ++;
                        }
                    } catch (FileNotFoundException e) {
                        interaction.print(true, "Entered file doesn't exist!");
                        continue;
                    }
                    if (result) {
                        log.info("Commands ran successfully!");
                    }
                    continue;
                }
                Boolean underRun = true;
                while (underRun) {
                    try {

                        serverInteraction.sendData(command);
                        Message message = (Message) serverInteraction.readData();
                        interaction.print(true, message.getText());
                        if (!message.isSuccessful()) {
                            run = false;
                        }
                        if (message.getUserId() != 0) userId = message.getUserId();
                        underRun = false;
                    } catch (SocketTimeoutException e) {
                        log.info("Error. " + e.getMessage());
                        interaction.print(true, "It seems server's very busy and can't answer. Would you try to reconnect? Enter 'yes' or 'no'.");
                        String resend = interaction.getData();
                        while (!resend.equals("yes")) {
                            if (resend.equals("no")) {
                                System.exit(0);
                            } else interaction.print(true, "Enter 'yes' or 'no'!");
                            resend = interaction.getData();
                        }
                    } catch (Exception e) {
                        log.info("Error. " + e.getMessage());
                        reconnect = true;
                        continue;
                    }
                }


            } catch (Exception e) {
                log.info("Error. " + e.getMessage());
            }
        }
    }
}
