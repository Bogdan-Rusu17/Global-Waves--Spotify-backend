package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class ArtistSearchCommand extends SearchCommand {
    private static final int NO_MAX_RESULTS = 5;
    public ArtistSearchCommand(final Command command) {
        super(command);
    }

    /**
     * outputs the results to the arrayNode
     * @param results of ArtistSearch
     */
    public void printResults(final ArrayList<Artist> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (Artist artist : results) {
            resultArray.add(artist.getUsername());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setArtistResults(results);
    }
    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersArtist();
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Artist> results = new ArrayList<>();
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(artist)) {
                    isValid = false;
                }
            }
            if (isValid) {
                results.add(artist);
            }
        }
        if (results.size() > NO_MAX_RESULTS) {
            this.printResults((ArrayList<Artist>) results.subList(0, NO_MAX_RESULTS));
            return;
        }
        this.printResults(results);
    }
}
