package main.admin_commands.podcast_commands;

import fileio.input.PodcastInput;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class RemovePodcastCommand extends Command {
    public RemovePodcastCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setName(command.getName());
    }
    @Override
    public void execCommand() {
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.outputBase();
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Host host = GlobalObjects.getInstance().existsHost(getUsername());
        if (host == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " is not a host.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        PodcastInput podcastToRemove = host.getPodcastByName(getName());
        if (podcastToRemove == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " doesn't have a podcast with the given name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (host.cantRemovePodcast(podcastToRemove)) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " can't delete this podcast.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        host.getPage().getPodcasts().remove(podcastToRemove);
        GlobalObjects.getInstance().getLibrary().getPodcasts().remove(podcastToRemove);
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " deleted the podcast successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
