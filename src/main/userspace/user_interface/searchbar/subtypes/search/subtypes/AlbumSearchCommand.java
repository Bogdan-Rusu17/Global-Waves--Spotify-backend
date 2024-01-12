package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Album;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class AlbumSearchCommand extends SearchCommand {
    private static final int NO_MAX_RESULTS = 5;
    public AlbumSearchCommand(final Command command) {
        super(command);
    }
    /**
     * outputs the results to the arrayNode
     * @param results of AlbumSearch
     */
    public void printResults(final ArrayList<Album> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (Album album : results) {
            resultArray.add(album.getName());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setAlbumResults(results);
    }
    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersAlbum();
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Album> results = new ArrayList<>();
        for (Album album : GlobalObjects.getInstance().getLibrary().getAlbums()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(album)) {
                    isValid = false;
                }
            }
            if (isValid) {
                results.add(album);
            }
        }
        if (results.size() > NO_MAX_RESULTS) {
            this.printResults(new ArrayList<>(results.subList(0, NO_MAX_RESULTS)));
            return;
        }
        this.printResults(results);
    }
}
