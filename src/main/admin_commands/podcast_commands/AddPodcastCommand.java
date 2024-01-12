package main.admin_commands.podcast_commands;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.notification_system.Notification;
import main.userspace.Command;

import java.util.ArrayList;

public final class AddPodcastCommand extends Command {
    public AddPodcastCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setName(command.getName());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
        this.setEpisodes(command.getEpisodes());
    }

    /**
     *
     * @param episodes list of episodes to be added
     * @return true if there exist two episodes that have the same name
     */
    public boolean hasIdenticalEpisodes(final ArrayList<EpisodeInput> episodes) {
        for (EpisodeInput ep1 : episodes) {
            for (EpisodeInput ep2 : episodes) {
                if (ep1.getName().equals(ep2.getName()) && ep1 != ep2) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void execCommand() {
        this.outputBase();
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Host host = GlobalObjects.getInstance().existsHost(this.getUsername());
        if (host == null) {
            this.getObjectNode().put("message", this.getUsername()
                    + " is not a host.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (host.hasSamePodcast(this.getName())) {
            this.getObjectNode().put("message", this.getUsername()
                    + " has another podcast with the same name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        if (hasIdenticalEpisodes(this.getEpisodes())) {
            this.getObjectNode().put("message", this.getUsername()
                    + " has the same episode in this podcast.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        PodcastInput newPodcast = new PodcastInput();
        newPodcast.setOwner(getUsername());
        newPodcast.setEpisodes(getEpisodes());
        newPodcast.setName(getName());
        host.getPage().getPodcasts().add(newPodcast);
        host.notifyObservers(new Notification("New Podcast", "New Podcast from " + host.getUsername() + "."));
        GlobalObjects.getInstance().getLibrary().getPodcasts().add(newPodcast);
        this.getObjectNode().put("message", this.getUsername()
                + " has added new podcast successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
