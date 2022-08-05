package Interaction;

import java.util.List;


public class ScriptInteraction implements UserInteraction {
    private final List<String> lines;



    public ScriptInteraction(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void print(boolean newLine, String message) {
        new ConsoleInteraction().print(newLine, message);
    }

    @Override
    public String getData(){
        String line;
        line = lines.get(0);
        lines.remove(0);

        return line;
    }

}
