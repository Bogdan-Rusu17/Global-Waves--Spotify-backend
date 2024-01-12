package main.userspace.user_interface.modify_state_cmds.repeat;

import main.userspace.Command;

public class RepeatCommand extends Command {
    public RepeatCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }
}
