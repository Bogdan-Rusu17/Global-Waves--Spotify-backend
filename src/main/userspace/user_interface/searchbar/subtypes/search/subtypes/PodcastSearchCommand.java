package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.PodcastInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class PodcastSearchCommand extends SearchCommand {
    private static final int NO_MAX_PODCASTS = 5;
    public PodcastSearchCommand(final Command command) {
        super(command);
    }
    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersPodcast();
    }
    /**
     * outputs the results to the arrayNode
     * @param results of PodcastSearch
     */
    public void printResults(final ArrayList<PodcastInput> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (PodcastInput podcast : results) {
            resultArray.add(podcast.getName());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setPodcastResults(results);
    }

    /**
     *
     */
    public void execCommand() {
        this.outputBase();
        ArrayList<PodcastInput> results = new ArrayList<>();
        for (PodcastInput podcast : GlobalObjects.getInstance().getLibrary().getPodcasts()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(podcast)) {
                    isValid = false;
                }
            }
            if (isValid) {
                results.add(podcast);
            }
        }
        if (results.size() > NO_MAX_PODCASTS) {
            this.printResults(new ArrayList<>(results.subList(0, NO_MAX_PODCASTS)));
            return;
        }
        this.printResults(results);
    }

}
