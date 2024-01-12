package main.userspace.user_interface.jump_commands.next.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.next.NextCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.AlbumState;

public final class AlbumNextCommand extends NextCommand {
    public AlbumNextCommand(final Command command) {
        super(command);
    }

    /**
     * we go into the next song of the album
     * if there is none we output an error message
     */
    @Override
    public void execCommand() {
        outputBase();
        AlbumState currentAlbumState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getAlbumState();
        currentAlbumState.setPaused(false);
        currentAlbumState.changeState(currentAlbumState.getRemainedTime(),
                this.getUsername());
        if (currentAlbumState.isFinished()) {
            this.getObjectNode().put("message", "Please load a source before skipping"
                    + " to the next track.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        String message = "Skipped to next track successfully. The current track is ";
        this.getObjectNode().put("message", message + currentAlbumState.getAlbum()
                .getSongs().get(currentAlbumState.getSongIndex()).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
