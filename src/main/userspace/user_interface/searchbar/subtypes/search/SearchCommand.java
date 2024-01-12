package main.userspace.user_interface.searchbar.subtypes.search;

import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class SearchCommand extends Command {
    public SearchCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setType(command.getType());
        this.setFilters(command.getFilters());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSongResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setPodcastResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setPlaylistResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setArtistResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setAlbumResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setHostResults(null);

        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedSong(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedPodcast(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedPlaylist(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedAlbum(null);

        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setLoadedPlaylist(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setLoadedSong(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setLoadedPodcast(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setLoadedAlbum(null);
    }

}
