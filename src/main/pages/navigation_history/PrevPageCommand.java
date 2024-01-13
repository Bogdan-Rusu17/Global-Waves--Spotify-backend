package main.pages.navigation_history;

import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class PrevPageCommand extends Command {
    public PrevPageCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        if (UserSpaceDb.getDatabase().get(getUsername()).getHistory().jumpPrev(UserSpaceDb.getDatabase().get(getUsername()))) {
            this.outputErrorMessage("The user " + getUsername() + " has navigated successfully to the previous page.");
            return;
        }
        this.outputErrorMessage("There are no pages left to go back.");
    }
}
