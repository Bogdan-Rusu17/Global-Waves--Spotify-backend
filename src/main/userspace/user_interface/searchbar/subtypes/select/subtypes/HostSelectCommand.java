package main.userspace.user_interface.searchbar.subtypes.select.subtypes;

import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public final class HostSelectCommand extends SelectCommand {
    public HostSelectCommand(final Command command) {
        super(command);
    }
    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Host> hosts = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getHostResults();
        if (hosts == null) {
            this.getObjectNode().put("message",
                    "Please conduct a search before making a selection.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (hosts.size() < this.getItemNumber()) {
            this.getObjectNode().put("message", "The selected ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(this.getUsername())
                .setUserPage(hosts.get(this.getItemNumber() - 1).getPage());
        this.getObjectNode().put("message",
                "Successfully selected " + hosts
                        .get(this.getItemNumber() - 1).getUsername() + "'s page.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
