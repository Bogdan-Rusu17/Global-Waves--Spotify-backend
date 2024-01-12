package main.userspace.user_interface.like_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.userspace.Command;

import java.util.Comparator;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public final class GetTop5SongsCommand extends Command {
    private static final int SONGS_NO = 4;
    public GetTop5SongsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * sorts all entries in the like database decreasingly using a lambda function
     * according to the number of likes and taking into account their
     * priority, aka when their order in the hashmap
     */
    @Override
    public void execCommand() {
        List<Map.Entry<SongInput, Integer>> entriesList
                = new ArrayList<>(LikeDB.getLikedSongs().entrySet());
        Collections.sort(entriesList, Comparator
                .<Map.Entry<SongInput, Integer>>comparingInt(entry -> entry.getValue())
                .thenComparingInt(entry -> LikeDB.getPriority().get(entry.getKey()))
                .reversed());
        int counter = 0;
        ArrayNode results = Command.getObjectMapper().createArrayNode();
        for (Map.Entry<SongInput, Integer> entry : entriesList) {
            results.add(entry.getKey().getName());
            counter++;
            if (counter > SONGS_NO) {
                break;
            }
        }
        this.getObjectNode().put("command", this.getCommand());
        this.getObjectNode().put("timestamp", this.getTimestamp());
        this.getObjectNode().put("result", results);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
