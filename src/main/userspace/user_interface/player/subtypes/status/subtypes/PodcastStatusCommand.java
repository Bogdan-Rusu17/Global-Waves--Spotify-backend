package main.userspace.user_interface.player.subtypes.status.subtypes;

import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.status.StatusCommand;

public final class PodcastStatusCommand extends StatusCommand {
    public PodcastStatusCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .getPodcastState().outputState(this);
    }
}
