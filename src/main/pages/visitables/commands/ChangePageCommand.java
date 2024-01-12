package main.pages.visitables.commands;

import main.pages.visitables.HomePage;
import main.pages.visitables.LikedContentPage;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class ChangePageCommand extends Command {
    public ChangePageCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
        this.setNextPage(command.getNextPage());
    }

    @Override
    public void execCommand() {
        outputBase();
        if (getNextPage().equals("Home")) {
            UserSpaceDb.getDatabase().get(getUsername()).setUserPage(new HomePage());
            outputErrorMessage(getUsername() + " accessed Home successfully.");
        } else if (getNextPage().equals("LikedContent")) {
            UserSpaceDb.getDatabase().get(getUsername()).setUserPage(new LikedContentPage());
            outputErrorMessage(getUsername() + " accessed LikedContent successfully.");
        } else {
            outputErrorMessage(getUsername() + " is trying to access a non-existent page.");
        }
    }
}
