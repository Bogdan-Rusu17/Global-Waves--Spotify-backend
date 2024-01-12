package main.wrapped.subtypes;

import com.fasterxml.jackson.databind.node.ObjectNode;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.wrapped.WrappedCommand;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HostWrappedCommand extends WrappedCommand {
    public HostWrappedCommand(Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        int cnt = 0;
        Host host = GlobalObjects.getInstance().existsHost(getUsername());
        ObjectNode topNode = Command.getObjectMapper().createObjectNode();

        HashMap<String, Integer> topEpisodes = host.getTop().getTopEpisodes().entrySet().stream()
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
        topNode.put("listeners", host.getTop().getFans().size());
        if (cnt == 0) {
            this.outputErrorMessage("No data to show for artist " + getUsername() + ".");
        } else {
            this.getObjectNode().put("result", topNode);
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        }
    }
}
