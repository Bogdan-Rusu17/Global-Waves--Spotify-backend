package main.admin_commands.album_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Album;
import main.entities.AlbumSortingHelper;
import main.globals.GlobalObjects;
import main.userspace.Command;

import java.util.ArrayList;
import java.util.Collections;

public final class GetTop5AlbumsCommand extends Command {
    private static final int NO_MAX_ALBUMS = 5;
    public GetTop5AlbumsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * sorts the albums using the utility class albumHelper with the help
     * of a custom defined comparator
     */
    @Override
    public void execCommand() {
        ArrayList<AlbumSortingHelper> list = new ArrayList<>();
        for (Album album : GlobalObjects.getInstance().getLibrary().getAlbums()) {
            list.add(new AlbumSortingHelper(album, album.computeTotalLikes()));
        }
        Collections.sort(list, new AlbumSortingHelper.AlbumComparator());
        this.getObjectNode().put("command", getCommand());
        this.getObjectNode().put("timestamp", getTimestamp());
        ArrayNode resultNode = Command.getObjectMapper().createArrayNode();
        int cnt = 0;
        for (AlbumSortingHelper album : list) {
            cnt++;
            resultNode.add(album.getAlbum().getName());
            if (cnt >= NO_MAX_ALBUMS) {
                break;
            }
        }
        this.getObjectNode().put("result", resultNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
