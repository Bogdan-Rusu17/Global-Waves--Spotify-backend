package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class SongSearchCommand extends SearchCommand {
    private static final int NO_MAX_RESULTS = 5;
    public SongSearchCommand(final Command command) {
        super(command);
    }

    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersSongs();
    }
    /**
     * outputs the results to the arrayNode
     * @param results of SongSearch
     */
    public void printResults(final ArrayList<SongInput> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (SongInput song : results) {
            resultArray.add(song.getName());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar().setSongResults(results);
    }
    /**
     *
     */
    public void execCommand() {
        this.outputBase();
        ArrayList<SongInput> results = new ArrayList<>();
        for (SongInput song : GlobalObjects.getInstance().getLibrary().getSongs()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(song)) {
                    isValid = false;
                }
            }
            if (isValid) {
                results.add(song);
            }
        }
        if (results.size() > NO_MAX_RESULTS) {
            this.printResults(new ArrayList<>(results.subList(0, NO_MAX_RESULTS)));
            return;
        }
        this.printResults(results);
    }
}
