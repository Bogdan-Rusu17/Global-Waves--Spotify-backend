package main.admin_commands.delete_user;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import main.entities.Album;
import main.entities.Artist;
import main.entities.Host;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

import java.util.Map;

public final class DeleteUserCommand extends Command {
    public DeleteUserCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    /**
     *
     * @param artist to be removed
     * @return true if they can be removed
     */
    public boolean canBeDeletedArtist(final Artist artist) {
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (UserSpaceDb.getDatabase().get(user.getUsername()).getUserPage()
                    == artist.getPage()) {
                return false;
            }
            if (artist.getPage().getAlbums().contains(UserSpaceDb.getDatabase()
                    .get(user.getUsername()).getPlayer().getLoadedAlbum())) {
                return false;
            }
            for (Album album : artist.getPage().getAlbums()) {
                if (album.getSongs().contains(UserSpaceDb.getDatabase()
                        .get(user.getUsername()).getPlayer().getLoadedSong())) {
                    return false;
                }
                for (SongInput song : album.getSongs()) {
                    if (UserSpaceDb.getDatabase().get(user.getUsername())
                            .getPlayer().getLoadedPlaylist() != null) {
                            if (UserSpaceDb.getDatabase().get(user.getUsername()).getPlayer()
                                    .getLoadedPlaylist().getSongList().contains(song)) {
                                return false;
                            }
                    }
                }
            }
        }
        return true;
    }
    /**
     *
     * @param normal, user to be removed
     * @return true if they can be removed
     */
    public boolean canBeDeletedNormalUser(final UserInput normal) {
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (user != normal) {
                Playlist userPlaylistLoaded = UserSpaceDb.getDatabase()
                        .get(user.getUsername()).getPlayer().getLoadedPlaylist();
                if (UserSpaceDb.getDatabase().get(normal.getUsername())
                        .getPlaylistList().contains(userPlaylistLoaded)) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     *
     * @param host to be removed
     * @return true if they can be removed
     */
    public boolean canBeDeletedHost(final Host host) {
        for (PodcastInput podcast : host.getPage().getPodcasts()) {
            if (host.cantRemovePodcast(podcast)) {
                return false;
            }
        }
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (UserSpaceDb.getDatabase().get(user.getUsername())
                    .getUserPage() == host.getPage()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execCommand() {
        Artist artist = GlobalObjects.getInstance().existsArtist(getUsername());
        outputBase();
        if (!GlobalObjects.getInstance().existsUsername(getUsername())) {
            outputErrorMessage("The username " + getUsername() + " doesn't exist.");
        }
        if (artist != null) {
            if (canBeDeletedArtist(artist)) {
                for (Album album : artist.getPage().getAlbums()) {
                    for (SongInput song : album.getSongs()) {
                        GlobalObjects.getInstance().getLibrary().getSongs().remove(song);
                        LikeDB.getLikedSongs().remove(song);
                        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
                            UserSpaceDb.getDatabase().get(user.getUsername())
                                    .getLikedSongs().remove(song);
                        }
                        for (Playlist playlist : PlaylistDB.getPlaylists()) {
                            playlist.getSongList().remove(song);
                        }
                    }
                    GlobalObjects.getInstance().getLibrary().getAlbums().remove(album);
                }
                GlobalObjects.getInstance().getLibrary().getArtists().remove(artist);
                outputErrorMessage(artist.getUsername() + " was successfully deleted.");
            } else {
                outputErrorMessage(artist.getUsername() + " can't be deleted.");
            }
            return;
        }
        UserInput normalUser = GlobalObjects.getInstance().existsNormalUser(getUsername());
        if (normalUser != null) {
            if (canBeDeletedNormalUser(normalUser)) {
                for (Playlist playlist : UserSpaceDb.getDatabase()
                        .get(normalUser.getUsername()).getPlaylistList()) {
                    PlaylistDB.getPlaylists().remove(playlist);
                    for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
                        UserSpaceDb.getDatabase().get(user.getUsername())
                                .getIsFollowingMap().remove(playlist);
                    }
                }
                for (SongInput song : UserSpaceDb.getDatabase()
                        .get(normalUser.getUsername()).getLikedSongs()) {
                    LikeDB.getLikedSongs().replace(song, LikeDB.getLikedSongs().get(song) - 1);
                }
                for (Map.Entry<Playlist, Integer> entry : UserSpaceDb.getDatabase()
                        .get(normalUser.getUsername()).getIsFollowingMap().entrySet()) {
                    if (entry.getValue() == 1) {
                        entry.getKey().setFollowers(entry.getKey().getFollowers() - 1);
                    }
                }
                GlobalObjects.getInstance().getLibrary().getUsers().remove(normalUser);
                outputErrorMessage(getUsername() + " was successfully deleted.");
            } else {
                outputErrorMessage(getUsername() + " can't be deleted.");
            }
            return;
        }
        Host host = GlobalObjects.getInstance().existsHost(getUsername());
        if (host != null) {
            if (canBeDeletedHost(host)) {
                for (PodcastInput podcast : host.getPage().getPodcasts()) {
                    GlobalObjects.getInstance().getLibrary().getPodcasts().remove(podcast);
                }
                GlobalObjects.getInstance().getLibrary().getHosts().remove(host);
                outputErrorMessage(getUsername() + " was successfully deleted.");
            } else {
                outputErrorMessage(getUsername() + " can't be deleted.");
            }
        }
    }
}
