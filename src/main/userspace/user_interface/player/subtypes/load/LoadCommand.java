package main.userspace.user_interface.player.subtypes.load;

import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class LoadCommand extends Command {
    public LoadCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    /**
     * a new load should reset all the previous selections
     */
    public void deleteSelection() {
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSongResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setPodcastResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setPlaylistResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setAlbumResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setArtistResults(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setHostResults(null);

        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedAlbum(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedSong(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedPodcast(null);
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSelectedPlaylist(null);
    }
}
