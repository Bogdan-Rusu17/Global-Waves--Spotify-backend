package main.userspace.user_interface.searchbar.subtypes.select.subtypes;

import fileio.input.PodcastInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public final class PodcastSelectCommand extends SelectCommand {
    public PodcastSelectCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<PodcastInput> podcasts = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getPodcastResults();
        if (podcasts == null) {
            this.getObjectNode().put("message",
                    "Please conduct a search before making a selection.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (podcasts.size() < this.getItemNumber()) {
            this.getObjectNode().put("message", "The selected ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setSelectedPodcast(podcasts.get(this.getItemNumber() - 1));
        this.getObjectNode().put("message",
                "Successfully selected " + podcasts.get(this.getItemNumber() - 1).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
