package main.notification_system;

import fileio.input.UserInput;
import main.entities.Artist;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class UnsubscribeCommand extends Command {
    public UnsubscribeCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        UserInput user = GlobalObjects.getInstance().existsNormalUser(getUsername());
        if (user == null) {
            this.outputErrorMessage("The username " + getUsername() + " doesn't exist.");
            return;
        }
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            if (UserSpaceDb.getDatabase().get(getUsername()).getUserPage() == artist.getPage()) {
                artist.detach(UserSpaceDb.getDatabase().get(getUsername()));
                this.outputErrorMessage(getUsername() + " unsubscribed from "
                        + artist.getUsername() + " successfully.");
                return;
            }
        }
        for (Host host : GlobalObjects.getInstance().getLibrary().getHosts()) {
            if (UserSpaceDb.getDatabase().get(getUsername()).getUserPage() == host.getPage()) {
                host.detach(UserSpaceDb.getDatabase().get(getUsername()));
                this.outputErrorMessage(getUsername() + " unsubscribed from "
                        + host.getUsername() + " successfully.");
                return;
            }
        }
        this.outputErrorMessage("To subscribe you need to be on the page of an artist or host.");
    }
}
