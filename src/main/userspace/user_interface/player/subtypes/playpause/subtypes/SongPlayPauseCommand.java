package main.userspace.user_interface.player.subtypes.playpause.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.playpause.PlayPauseCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.SongState;

public final class SongPlayPauseCommand extends PlayPauseCommand {
    public SongPlayPauseCommand(final Command command) {
        super(command);
    }

    /**
     * changes the state of the loadedSong to be put on pause, if it was unpaused
     * before, and reverse the operation if the loadedSong was paused
     */
    @Override
    public void execCommand() {
        this.outputBase();
        SongState state = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getSongState();
        String message;
        message = (!state.isPaused()) ? "Playback paused successfully."
                : "Playback resumed successfully.";
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        state.setPaused(!state.isPaused());
    }
}
