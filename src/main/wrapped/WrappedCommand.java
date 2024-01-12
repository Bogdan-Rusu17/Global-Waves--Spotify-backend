package main.wrapped;

import main.userspace.Command;

public class WrappedCommand extends Command {
    public WrappedCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }
}
