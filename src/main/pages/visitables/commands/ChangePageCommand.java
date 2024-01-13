package main.pages.visitables.commands;

import fileio.input.PodcastInput;
import main.entities.Artist;
import main.entities.Host;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.pages.visitables.HomePage;
import main.pages.visitables.LikedContentPage;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class ChangePageCommand extends Command {
    public ChangePageCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
        this.setNextPage(command.getNextPage());
    }

    @Override
    public void execCommand() {
        outputBase();
        switch (getNextPage()) {
            case "Home" -> {
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .saveStateMemento(UserSpaceDb.getDatabase().get(getUsername()));
                UserSpaceDb.getDatabase().get(getUsername()).setUserPage(new HomePage());
                outputErrorMessage(getUsername() + " accessed Home successfully.");
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .clearForwardHistory();
            }
            case "LikedContent" -> {
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .saveStateMemento(UserSpaceDb.getDatabase().get(getUsername()));
                UserSpaceDb.getDatabase().get(getUsername()).setUserPage(new LikedContentPage());
                outputErrorMessage(getUsername() + " accessed LikedContent successfully.");
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .clearForwardHistory();
            }
            case "Artist" -> {
                Artist artist = null;
                if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedAlbum() != null) {
                    artist = GlobalObjects.getInstance().existsArtist(
                            UserSpaceDb.getDatabase().get(getUsername())
                                    .getPlayer().getLoadedAlbum().getOwner());
                }
                if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong() != null) {
                    artist = GlobalObjects.getInstance().existsArtist(
                            UserSpaceDb.getDatabase().get(getUsername())
                                    .getPlayer().getLoadedSong().getArtist());
                }
                if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedPlaylist() != null) {
                    Playlist loadedPlaylist = UserSpaceDb.getDatabase().get(getUsername())
                            .getPlayer().getLoadedPlaylist();
                    int songIdx = UserSpaceDb.getDatabase().get(getUsername())
                            .getPlayer().getPlaylistState().getSongIndex();
                    artist = GlobalObjects.getInstance().existsArtist(
                            loadedPlaylist.getSongList().get(songIdx).getArtist());
                }
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .saveStateMemento(UserSpaceDb.getDatabase().get(getUsername()));
                UserSpaceDb.getDatabase().get(getUsername()).setUserPage(artist.getPage());
                outputErrorMessage(getUsername() + " accessed Artist successfully.");
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .clearForwardHistory();
            }
            case "Host" -> {
                PodcastInput loadedPodcast = UserSpaceDb.getDatabase().get(getUsername())
                        .getPlayer().getLoadedPodcast();
                Host host = GlobalObjects.getInstance().existsHost(loadedPodcast.getOwner());
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .saveStateMemento(UserSpaceDb.getDatabase().get(getUsername()));
                UserSpaceDb.getDatabase().get(getUsername()).setUserPage(host.getPage());
                UserSpaceDb.getDatabase().get(getUsername()).getHistory()
                        .clearForwardHistory();
                outputErrorMessage(getUsername() + " accessed Host successfully.");
            }
            default -> outputErrorMessage(getUsername() + " is trying to access a non-existent page.");
        }
    }
}
