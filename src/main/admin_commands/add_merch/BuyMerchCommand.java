package main.admin_commands.add_merch;

import fileio.input.UserInput;
import main.entities.Artist;
import main.entities.Merch;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class BuyMerchCommand extends Command {
    public BuyMerchCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setName(command.getName());
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
                Merch merchToBuy = artist.getMerchByName(getName());
                if (merchToBuy == null) {
                    this.outputErrorMessage("The merch " + getName() + " doesn't exist.");
                    return;
                }
                UserSpaceDb.getDatabase().get(getUsername()).getBoughtMerch().add(merchToBuy);
                artist.getTop().setMerchRevenue(artist.getTop().getMerchRevenue() + merchToBuy.getPrice());
                this.outputErrorMessage(getUsername() + " has added new merch successfully.");
                return;
            }
        }
        this.outputErrorMessage("Cannot buy merch from this page.");
    }
}
