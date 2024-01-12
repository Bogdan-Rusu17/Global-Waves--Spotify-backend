package main.userspace.user_interface.searchbar.subtypes.search.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;

import java.util.ArrayList;

public final class HostSearchCommand extends SearchCommand {
    private static final int NO_MAX_RESULTS = 5;
    public HostSearchCommand(final Command command) {
        super(command);
    }
    /**
     * outputs the results to the arrayNode
     * @param results of HostSearch
     */
    public void printResults(final ArrayList<Host> results) {
        ArrayNode resultArray = Command.getObjectMapper().createArrayNode();
        for (Host host : results) {
            resultArray.add(host.getUsername());
        }
        this.getObjectNode().put("message", "Search returned " + results.size() + " results");
        this.getObjectNode().put("results", resultArray);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setHostResults(results);
    }
    /**
     * init filters arraylist
     */
    public void initCommand() {
        this.getFilters().initSubFiltersHost();
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Host> results = new ArrayList<>();
        for (Host host : GlobalObjects.getInstance().getLibrary().getHosts()) {
            boolean isValid = true;
            for (Filter filter : this.getFilters().getSubFilters()) {
                if (!filter.isMetFilter(host)) {
                    isValid = false;
                }
            }
            if (isValid) {
                results.add(host);
            }
        }
        if (results.size() > NO_MAX_RESULTS) {
            this.printResults((ArrayList<Host>) results.subList(0, NO_MAX_RESULTS));
            return;
        }
        this.printResults(results);
    }
}
