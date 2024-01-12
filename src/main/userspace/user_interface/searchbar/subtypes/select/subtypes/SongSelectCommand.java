package main.userspace.user_interface.searchbar.subtypes.select.subtypes;

import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public final class SongSelectCommand extends SelectCommand {
    public SongSelectCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<SongInput> songs = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getSongResults();
        if (songs == null) {
            this.getObjectNode().put("message",
                    "Please conduct a search before making a selection.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (songs.size() < this.getItemNumber()) {
            this.getObjectNode().put("message", "The selected ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(this.getUsername())
                .getSearchBar().setSelectedSong(songs.get(this.getItemNumber() - 1));
        this.getObjectNode().put("message",
                "Successfully selected " + songs.get(this.getItemNumber() - 1).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
