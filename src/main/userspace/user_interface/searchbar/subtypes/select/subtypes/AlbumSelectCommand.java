package main.userspace.user_interface.searchbar.subtypes.select.subtypes;

import main.entities.Album;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public final class AlbumSelectCommand extends SelectCommand {
    public AlbumSelectCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Album> albums = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getAlbumResults();
        if (albums == null) {
            this.getObjectNode().put("message",
                    "Please conduct a search before making a selection.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (albums.size() < this.getItemNumber()) {
            this.getObjectNode().put("message", "The selected ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setSelectedAlbum(albums.get(this.getItemNumber() - 1));
        this.getObjectNode().put("message",
                "Successfully selected " + albums.get(this.getItemNumber() - 1).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
