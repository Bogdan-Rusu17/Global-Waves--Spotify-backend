package main.userspace.user_interface.jump_commands.next.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.next.NextCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

public final class PlaylistNextCommand extends NextCommand {
    public PlaylistNextCommand(final Command command) {
        super(command);
    }

    /**
     * we go into the next song of the playlist
     * if there is none we output an error message
     */
    @Override
    public void execCommand() {
        outputBase();
        PlaylistState currentPlaylistState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPlaylistState();
        currentPlaylistState.setPaused(false);
        currentPlaylistState.changeState(currentPlaylistState.getRemainedTime(),
                this.getUsername());
        if (currentPlaylistState.isFinished()) {
            this.getObjectNode().put("message", "Please load a source before skipping"
                    + " to the next track.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        String message = "Skipped to next track successfully. The current track is ";
        this.getObjectNode().put("message", message + currentPlaylistState.getPlaylist()
                .getSongList().get(currentPlaylistState.getSongIndex()).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
