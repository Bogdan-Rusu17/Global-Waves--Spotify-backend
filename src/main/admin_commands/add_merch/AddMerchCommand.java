package main.admin_commands.add_merch;

import main.entities.Artist;
import main.entities.Merch;
import main.globals.GlobalObjects;
import main.notification_system.Notification;
import main.userspace.Command;

public final class AddMerchCommand extends Command {
    public AddMerchCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setName(command.getName());
        this.setDescription(command.getDescription());
        this.setPrice(command.getPrice());
        this.setUsername(command.getUsername());
    }

    /**
     *
     * @param artist to be queried
     * @param name to be verified of any prior existence
     * @return true if there already exists merch with the same name
     */
    public boolean hasSameMerch(final Artist artist, final String name) {
        for (Merch merch : artist.getPage().getMerchProducts()) {
            if (merch.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * verifies if the existing user is an artist and whether they
     * have the merch already, and adds it if they don't already have it
     */
    @Override
    public void execCommand() {
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.outputBase();
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Artist artist = GlobalObjects.getInstance().existsArtist(this.getUsername());
        if (artist == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " is not an artist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (hasSameMerch(artist, getName())) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " has merchandise with the same name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (getPrice() < 0) {
            this.outputBase();
            this.getObjectNode().put("message", "Price for merchandise can not be negative.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Merch newMerch = new Merch(getName(), getDescription(), getPrice());
        artist.getPage().getMerchProducts().add(newMerch);
        artist.notifyObservers(new Notification("New Merchandise", "New Merchandise from "
                + artist.getUsername() + "."));
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " has added new merchandise successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
