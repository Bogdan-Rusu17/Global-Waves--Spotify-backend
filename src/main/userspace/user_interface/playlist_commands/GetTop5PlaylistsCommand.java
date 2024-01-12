package main.userspace.user_interface.playlist_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.globals.PlaylistDB;
import main.userspace.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public final class GetTop5PlaylistsCommand extends Command {
    private static final int PLAYLISTS_NO = 4;
    public GetTop5PlaylistsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        List<Playlist> orderedList = new ArrayList<>(PlaylistDB.getPlaylists());
        Collections.sort(orderedList, Comparator.comparingInt(Playlist::getFollowers).reversed());
        int counter = 0;
        ArrayNode results = Command.getObjectMapper().createArrayNode();
        for (Playlist playlist : orderedList) {
            results.add(playlist.getName());
            counter++;
            if (counter > PLAYLISTS_NO) {
                break;
            }
        }
        this.getObjectNode().put("command", this.getCommand());
        this.getObjectNode().put("timestamp", this.getTimestamp());
        this.getObjectNode().put("result", results);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
