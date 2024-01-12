package main.userspace.user_interface.jump_commands.prev;

import main.userspace.Command;

public class PrevCommand extends Command {
    public PrevCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }
}
