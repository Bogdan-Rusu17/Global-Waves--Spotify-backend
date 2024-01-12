package main.userspace.user_interface.like_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class ShowPreferredSongsCommand extends Command {
    public ShowPreferredSongsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * outputs all the liked songs of the command issuer through an arrayNode
     */
    @Override
    public void execCommand() {
        outputBase();
        ArrayNode likedSongs = Command.getObjectMapper().createArrayNode();
        for (SongInput song : UserSpaceDb.getDatabase().get(this.getUsername()).getLikedSongs()) {
            likedSongs.add(song.getName());
        }
        this.getObjectNode().put("result", likedSongs);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
