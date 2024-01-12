package main.wrapped.subtypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.wrapped.WrappedCommand;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ArtistWrappedCommand extends WrappedCommand {
    public ArtistWrappedCommand(Command command) {
        super(command);
    }
    @Override
    public void execCommand() {
        this.outputBase();
        int cnt = 0;
        Artist artist = GlobalObjects.getInstance().existsArtist(getUsername());
        ObjectNode topNode = Command.getObjectMapper().createObjectNode();

        HashMap<String, Integer> topAlbums = artist.getTop().getTopAlbums().entrySet().stream()
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

        HashMap<String, Integer> topSongs = artist.getTop().getTopSongs().entrySet().stream()
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

        HashMap<String, Integer> topFans = artist.getTop().getTopFans().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        ArrayNode fansNode = Command.getObjectMapper().createArrayNode();
        for (Map.Entry<String, Integer> entry : topFans.entrySet()) {
            fansNode.add(entry.getKey());
            cnt++;
        }
        topNode.put("topFans", fansNode);
        topNode.put("listeners", artist.getTop().getTopFans().size());
        if (cnt == 0) {
            this.outputErrorMessage("No data to show for artist " + getUsername() + ".");
        } else {
            this.getObjectNode().put("result", topNode);
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        }
    }
}
