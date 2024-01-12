package main.userspace.user_interface.jump_commands.prev.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.prev.PrevCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.AlbumState;

public final class AlbumPrevCommand extends PrevCommand {
    public AlbumPrevCommand(final Command command) {
        super(command);
    }
    /**
     * if the current song has spent at least 1 second running, we will just
     * return it to the beginning
     * if it's the first song on the album, it just starts it again
     * else, we return to the beginning of the previous song
     */
    @Override
    public void execCommand() {
        outputBase();
        AlbumState currentAlbumState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getAlbumState();
        if (currentAlbumState.getRemainedTime()
                < currentAlbumState.getAlbum().getSongs().get(currentAlbumState
                .getSongIndex()).getDuration()) {
            currentAlbumState.setRemainedTime(currentAlbumState.getAlbum().getSongs()
                    .get(currentAlbumState.getSongIndex()).getDuration());
            currentAlbumState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentAlbumState.getAlbum().getSongs()
                            .get(currentAlbumState.getSongIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        int prevSongIdx = currentAlbumState.getPrevSong(currentAlbumState.getSongIndex());
        if (prevSongIdx == -1) {
            currentAlbumState.setRemainedTime(currentAlbumState.getAlbum().getSongs()
                    .get(currentAlbumState.getSongIndex()).getDuration());
            currentAlbumState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentAlbumState.getAlbum().getSongs()
                            .get(currentAlbumState.getSongIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        this.getObjectNode().put("message",
                "Returned to previous track successfully. The current track is "
                        + currentAlbumState.getAlbum().getSongs()
                        .get(prevSongIdx).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        currentAlbumState.setSongIndex(prevSongIdx);
        currentAlbumState.setRemainedTime(currentAlbumState.getAlbum().getSongs()
                .get(prevSongIdx).getDuration());
        currentAlbumState.setName(currentAlbumState.getAlbum().getSongs()
                .get(prevSongIdx).getName());
        currentAlbumState.setPaused(false);
    }
}
