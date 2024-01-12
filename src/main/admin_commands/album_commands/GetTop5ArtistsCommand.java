package main.admin_commands.album_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Artist;
import main.entities.ArtistSortingHelper;
import main.globals.GlobalObjects;
import main.userspace.Command;

import java.util.ArrayList;

public final class GetTop5ArtistsCommand extends Command {
    private static final int NO_MAX_ARTISTS = 5;
    public GetTop5ArtistsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }
    /**
     * lambda sorting artists using a helper class that maintains
     * the total likes an artist has on all their songs
     */
    @Override
    public void execCommand() {
        ArrayList<ArtistSortingHelper> list = new ArrayList<>();
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            list.add(new ArtistSortingHelper(artist, artist.computeTotalLikes()));
        }
        list.sort((artist1, artist2) -> Integer.compare(artist2.getLikes(), artist1.getLikes()));

        this.getObjectNode().put("command", getCommand());
        this.getObjectNode().put("timestamp", getTimestamp());
        ArrayNode resultNode = Command.getObjectMapper().createArrayNode();
        int cnt = 0;
        for (ArtistSortingHelper artist : list) {
            cnt++;
            resultNode.add(artist.getArtist().getUsername());
            if (cnt >= NO_MAX_ARTISTS) {
                break;
            }
        }
        this.getObjectNode().put("result", resultNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
