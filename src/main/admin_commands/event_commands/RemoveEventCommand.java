package main.admin_commands.event_commands;

import main.entities.Artist;
import main.entities.Event;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class RemoveEventCommand extends Command {
    public RemoveEventCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setName(command.getName());
    }

    @Override
    public void execCommand() {
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.outputBase();
            this.getObjectNode().put("message",
                    "The username " + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Artist artist = GlobalObjects.getInstance().existsArtist(getUsername());
        if (artist == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " is not an artist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Event eventToRemove = artist.getEventByName(getName());
        if (eventToRemove == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " doesn't have an event with the given name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        artist.getPage().getEvents().remove(eventToRemove);
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " deleted the event successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
