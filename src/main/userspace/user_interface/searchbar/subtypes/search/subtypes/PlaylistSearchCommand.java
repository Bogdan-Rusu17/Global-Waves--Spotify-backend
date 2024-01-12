package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class PlaylistSearchCommand extends SearchCommand {
    private static final int NO_MAX_RESULTS = 5;
    public PlaylistSearchCommand(final Command command) {
        super(command);
    }

    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersPlaylist();
    }

    /**
     * outputs the results to the arrayNode
     * @param results of PlaylistSearch
     */
    public void printResults(final ArrayList<Playlist> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (Playlist playlist : results) {
            resultArray.add(playlist.getName());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setPlaylistResults(results);
    }
    /**
     *
     */
    public void execCommand() {
        this.outputBase();
        ArrayList<Playlist> results = new ArrayList<>();
        for (Playlist playlist : PlaylistDB.getPlaylists()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(playlist)) {
                    isValid = false;
                }
            }
            if (isValid && (playlist.getVisibility().equals("public") || playlist.getOwner()
                    .equals(this.getUsername()))) {
                results.add(playlist);
            }
        }
        if (results.size() > NO_MAX_RESULTS) {
            this.printResults((ArrayList<Playlist>) results.subList(0, NO_MAX_RESULTS));
            return;
        }
        this.printResults(results);
    }
}
