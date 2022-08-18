package Commands;

import Movie.Movie;
import Interaction.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ExecuteScript extends CommandObject {
    private final String argument;

    public ExecuteScript(String[] commandArgs) {
        this.argument = commandArgs[0];
    }


    @Override
    public Message execute(Hashtable<String, Movie> collection) throws Exception {
        if (!isAuthorized()) return getUnauthorizedMessage();
        try {
            Stream<String> stream = Files.lines(Paths.get(argument));
            List<String> list = stream.collect(Collectors.toList());

            String line;
            int lineNum = 0;
            while (!list.isEmpty()) {
                line = list.get(0);
                list.remove(0);
                if (line.equals("execute_script Script.txt")) {
                    return new Message(true, "Executing script from another is not available.");
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Command command = CommandExecution.proceedCommand(line, true, new ScriptInteraction(list));
                    if (command == null) {
                        continue;
                    }
                } catch (Exception e) {
                    return new Message(false,"An error occurred while executing line " + lineNum + ":\n" + line);
                }
                lineNum ++;
            }
        } catch (FileNotFoundException e) {
            return new Message(true, "Entered file doesn't exist!");
        }

        return new Message(true, "Execution of script '"+ argument + "' finished successful.");
    }


    public String getArgument() {
        return argument;
    }


}
