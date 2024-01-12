package main.userspace.user_interface.searchbar.subtypes.select;

import main.userspace.Command;

public class SelectCommand extends Command {
    public SelectCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setItemNumber(command.getItemNumber());
    }
}
