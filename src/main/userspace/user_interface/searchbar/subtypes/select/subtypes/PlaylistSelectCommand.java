package main.userspace.user_interface.searchbar.subtypes.select.subtypes;

import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public final class PlaylistSelectCommand extends SelectCommand {
    public PlaylistSelectCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayList<Playlist> playlists = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getPlaylistResults();
        if (playlists == null) {
            this.getObjectNode().put("message",
                    "Please conduct a search before making a selection.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (playlists.size() < this.getItemNumber()) {
            this.getObjectNode().put("message", "The selected ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(this.getUsername()).getSearchBar()
                .setSelectedPlaylist(playlists.get(this.getItemNumber() - 1));
        this.getObjectNode().put("message",
                "Successfully selected " + playlists.get(this.getItemNumber() - 1).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
