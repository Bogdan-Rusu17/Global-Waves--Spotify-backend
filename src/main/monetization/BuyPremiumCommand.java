package main.monetization;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class BuyPremiumCommand extends Command {
    public BuyPremiumCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        if (!GlobalObjects.getInstance().containsNormalUser(getUsername())) {
            this.getObjectNode().put("message", "The username "
                    + getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).isPremiumUser()) {
            this.getObjectNode().put("message", getUsername()
                    + " is already a premium user.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserSpaceDb.getDatabase().get(getUsername()).setPremiumUser(true);
        this.getObjectNode().put("message", getUsername()
                + " bought the subscription successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
