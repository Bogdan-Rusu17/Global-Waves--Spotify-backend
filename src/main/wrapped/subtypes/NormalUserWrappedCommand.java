package main.wrapped.subtypes;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.wrapped.WrappedCommand;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NormalUserWrappedCommand extends WrappedCommand {
    public NormalUserWrappedCommand(Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        int cnt = 0;
        HashMap<String, Integer> topArtists = UserSpaceDb.getDatabase()
                .get(getUsername()).getTop().getTopArtists().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        ObjectNode topNode = Command.getObjectMapper().createObjectNode();
        ObjectNode artistNode = Command.getObjectMapper().createObjectNode();
        for (Map.Entry<String, Integer> entry : topArtists.entrySet()) {
            artistNode.put(entry.getKey(), entry.getValue());
            cnt++;
        }
        topNode.put("topArtists", artistNode);

        HashMap<String, Integer> topGenres = UserSpaceDb.getDatabase()
                .get(getUsername()).getTop().getTopGenres().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        ObjectNode genreNode = Command.getObjectMapper().createObjectNode();
        for (Map.Entry<String, Integer> entry : topGenres.entrySet()) {
            genreNode.put(entry.getKey(), entry.getValue());
            cnt++;
        }
        topNode.put("topGenres", genreNode);

        HashMap<String, Integer> topSongs = UserSpaceDb.getDatabase()
                .get(getUsername()).getTop().getTopSongs().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        ObjectNode songNode = Command.getObjectMapper().createObjectNode();
        for (Map.Entry<String, Integer> entry : topSongs.entrySet()) {
            songNode.put(entry.getKey(), entry.getValue());
            cnt++;
        }
        topNode.put("topSongs", songNode);

        HashMap<String, Integer> topAlbums = UserSpaceDb.getDatabase()
                .get(getUsername()).getTop().getTopAlbums().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        ObjectNode albumNode = Command.getObjectMapper().createObjectNode();
        for (Map.Entry<String, Integer> entry : topAlbums.entrySet()) {
            albumNode.put(entry.getKey(), entry.getValue());
            cnt++;
        }
        topNode.put("topAlbums", albumNode);

        HashMap<String, Integer> topEpisodes = UserSpaceDb.getDatabase()
                .get(getUsername()).getTop().getTopEpisodes().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        ObjectNode episodeNode = Command.getObjectMapper().createObjectNode();
        for (Map.Entry<String, Integer> entry : topEpisodes.entrySet()) {
            episodeNode.put(entry.getKey(), entry.getValue());
            cnt++;
        }
        topNode.put("topEpisodes", episodeNode);
        if (cnt == 0) {
            this.outputErrorMessage("No data to show for user " + getUsername() + ".");
        } else {
            this.getObjectNode().put("result", topNode);
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        }
    }
}
