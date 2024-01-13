package main.admin_commands.album_commands;

import fileio.input.SongInput;
import fileio.input.UserInput;
import main.entities.*;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class RemoveAlbumCommand extends Command {
    public RemoveAlbumCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setName(command.getName());
    }

    /**
     *
     * @param album to verify if it can be removed
     * @return true if there is no conflict between the album
     * supposed to be deleted and other users' interaction with its content
     */
    public boolean cantRemoveAlbum(final Album album) {
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (UserSpaceDb.getDatabase().get(user.getUsername())
                    .getPlayer().getLoadedAlbum() == album) {
                return true;
            }
            for (SongInput song : album.getSongs()) {
                if (UserSpaceDb.getDatabase().get(user.getUsername())
                        .getPlayer().getLoadedSong() == song) {
                    return true;
                }
                if (UserSpaceDb.getDatabase().get(user.getUsername())
                        .getPlayer().getLoadedPlaylist() != null) {
                    if (UserSpaceDb.getDatabase().get(user.getUsername()).
                            getPlayer().getLoadedPlaylist().getSongList().contains(song)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void execCommand() {
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.outputBase();
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Artist artist = GlobalObjects.getInstance().existsArtist(getUsername());
        if (artist == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " is not an artist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Album albumToRemove = artist.getAlbumByName(getName());
        if (albumToRemove == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " doesn't have an album with the given name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (cantRemoveAlbum(albumToRemove)) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " can't delete this album.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        GlobalObjects.getInstance().getLibrary().getAlbums().remove(albumToRemove);
        artist.getPage().getAlbums().remove(albumToRemove);
        for (SongInput song : albumToRemove.getSongs()) {
            GlobalObjects.getInstance().getLibrary().getSongs().remove(song);
            for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
                UserSpaceDb.getDatabase().get(user.getUsername()).getLikedSongs().remove(song);
            }
            LikeDB.getLikedSongs().remove(song);
            for (Playlist playlist : PlaylistDB.getPlaylists()) {
                playlist.getSongList().remove(song);
            }
        }
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " deleted the album successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
