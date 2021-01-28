package command;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the list of commands and defines a method to execute all commands in list
 */
public class CommandTaker {
    private final List<Command> commandList = new ArrayList<>();

    /**
     * @param command Add this command to the list
     */
    public void takeCommand(Command command) {
        commandList.add(command);
    }

    /**
     * Executed all commands in list
     */
    public void doCommands(){
        for(Command command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}
