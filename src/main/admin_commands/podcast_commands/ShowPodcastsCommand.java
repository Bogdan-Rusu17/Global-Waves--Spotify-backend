package main.admin_commands.podcast_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class ShowPodcastsCommand extends Command {
    public ShowPodcastsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        outputBase();
        Host host = GlobalObjects.getInstance().existsHost(getUsername());
        ArrayNode resultNode = Command.getObjectMapper().createArrayNode();
        for (PodcastInput podcast : host.getPage().getPodcasts()) {
            ObjectNode podcastNode = Command.getObjectMapper().createObjectNode();
            podcastNode.put("name", podcast.getName());
            ArrayNode episodesNode = Command.getObjectMapper().createArrayNode();
            for (EpisodeInput episode : podcast.getEpisodes()) {
                episodesNode.add(episode.getName());
            }
            podcastNode.put("episodes", episodesNode);
            resultNode.add(podcastNode);
        }
        this.getObjectNode().put("result", resultNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
