package main.userspace.user_interface.jump_commands.prev.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.prev.PrevCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

public final class PlaylistPrevCommand extends PrevCommand {
    public PlaylistPrevCommand(final Command command) {
        super(command);
    }

    /**
     * if the current song has spent at least 1 second running, we will just
     * return it to the beginning
     * if it's the first song in the playlist, it just starts it again
     * else, we return to the beginning of the previous song
     */
    @Override
    public void execCommand() {
        outputBase();
        PlaylistState currentPlaylistState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getPlaylistState();
        if (currentPlaylistState.getRemainedTime()
                < currentPlaylistState.getPlaylist().getSongList().get(currentPlaylistState
                        .getSongIndex()).getDuration()) {
            currentPlaylistState.setRemainedTime(currentPlaylistState.getPlaylist().getSongList()
                    .get(currentPlaylistState.getSongIndex()).getDuration());
            currentPlaylistState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentPlaylistState.getPlaylist().getSongList()
                            .get(currentPlaylistState.getSongIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        int prevSongIdx = currentPlaylistState.getPrevSong(currentPlaylistState.getSongIndex());
        if (prevSongIdx == -1) {
            currentPlaylistState.setRemainedTime(currentPlaylistState.getPlaylist()
                    .getSongList().get(currentPlaylistState.getSongIndex()).getDuration());
            currentPlaylistState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentPlaylistState.getPlaylist().getSongList()
                            .get(currentPlaylistState.getSongIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        this.getObjectNode().put("message",
                "Returned to previous track successfully. The current track is "
                        + currentPlaylistState.getPlaylist()
                        .getSongList().get(prevSongIdx).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        currentPlaylistState.setSongIndex(prevSongIdx);
        currentPlaylistState.setRemainedTime(currentPlaylistState.getPlaylist()
                .getSongList().get(prevSongIdx).getDuration());
        currentPlaylistState.setName(currentPlaylistState.getPlaylist()
                .getSongList().get(prevSongIdx).getName());
        currentPlaylistState.setPaused(false);
    }
}
