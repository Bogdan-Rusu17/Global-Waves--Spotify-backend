package main.admin_commands.announcement_commands;

import main.entities.Announcement;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class RemoveAnnouncementCommand extends Command {
    public RemoveAnnouncementCommand(final Command command) {
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
        Announcement announcement = host.hasSameAnnouncement(getName());
        if (announcement == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " has no announcement with the given name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        host.getPage().getAnnouncements().remove(announcement);
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " has successfully deleted the announcement.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
