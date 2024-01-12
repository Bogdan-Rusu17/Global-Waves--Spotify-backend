package main.userspace.user_interface.playlist_commands;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

import java.util.ArrayList;

public final class AlbumAddRemoveInPlaylist extends Command {
    public AlbumAddRemoveInPlaylist(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setPlaylistId(command.getPlaylistId());
    }
    @Override
    public void execCommand() {
        this.outputBase();
        SongInput loadedSong = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getLoadedAlbum().getSongs()
                .get(UserSpaceDb.getDatabase().get(this.getUsername())
                        .getPlayer().getAlbumState().getSongIndex());
        ArrayList<Playlist> userPlaylists = UserSpaceDb.getDatabase().get(this
                .getUsername()).getPlaylistList();
        if (this.getPlaylistId() > userPlaylists.size()) {
            this.getObjectNode().put("message", "The specified playlist does not exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        String message;
        Playlist playlistToAddInto = userPlaylists.get(this.getPlaylistId() - 1);
        if (playlistToAddInto.getSongList().contains(loadedSong)) {
            playlistToAddInto.getSongList().remove(loadedSong);
            message = "Successfully removed from playlist.";
        } else {
            playlistToAddInto.getSongList().add(loadedSong);
            message = "Successfully added to playlist.";
        }
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
