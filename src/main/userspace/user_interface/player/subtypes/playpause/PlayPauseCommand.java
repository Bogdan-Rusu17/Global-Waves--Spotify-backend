package main.userspace.user_interface.player.subtypes.playpause;

import main.userspace.Command;

public class PlayPauseCommand extends Command {
    public PlayPauseCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }
}
