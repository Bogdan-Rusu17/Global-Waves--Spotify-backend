package main.admin_commands.announcement_commands;

import main.entities.Announcement;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.notification_system.Notification;
import main.userspace.Command;

public final class AddAnnouncementCommand extends Command {
    public AddAnnouncementCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setName(command.getName());
        this.setDescription(command.getDescription());
        this.setTimestamp(command.getTimestamp());
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
        if (host.hasSameAnnouncement(getName()) != null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " has already added an announcement with this name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Announcement newAnnounce = new Announcement(getName(), getDescription());
        host.getPage().getAnnouncements().add(newAnnounce);
        host.notifyObservers(new Notification("New Announcement",
                "New Announcement from " + host.getUsername() + "."));
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " has successfully added new announcement.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
