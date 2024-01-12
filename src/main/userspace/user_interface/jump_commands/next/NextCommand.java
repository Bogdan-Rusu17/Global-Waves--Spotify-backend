package main.userspace.user_interface.jump_commands.next;

import main.userspace.Command;

public class NextCommand extends Command {
    public NextCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }
}
