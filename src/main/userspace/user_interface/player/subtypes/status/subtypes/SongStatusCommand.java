package main.userspace.user_interface.player.subtypes.status.subtypes;

import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.status.StatusCommand;

public final class SongStatusCommand extends StatusCommand {
    public SongStatusCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .getSongState().outputState(this);
    }
}
