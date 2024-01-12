package main.userspace.user_interface.like_commands;

import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

public final class LikeFromPlaylistCommand extends Command {
    public LikeFromPlaylistCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * verifies the current playlist state and adds/removes the like from the
     * general database of liked songs and adds/removes it respectively from the users
     * liked songs list
     */
    @Override
    public void execCommand() {
        this.outputBase();
        PlaylistState playlistState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPlaylistState();
        SongInput crtSong = playlistState.getPlaylist().getSongList()
                .get(playlistState.getSongIndex());
        String message;
        if (UserSpaceDb.getDatabase().get(this.getUsername())
                .getLikedSongs().contains(crtSong)) {
            message = "Unlike registered successfully.";
            LikeDB.removeLike(crtSong);
            UserSpaceDb.getDatabase().get(this.getUsername())
                    .getLikedSongs().remove(crtSong);
        } else {
            LikeDB.addLike(crtSong);
            UserSpaceDb.getDatabase().get(this.getUsername())
                    .getLikedSongs().add(crtSong);
            message = "Like registered successfully.";
        }
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
