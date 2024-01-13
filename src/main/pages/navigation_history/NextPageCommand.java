package main.pages.navigation_history;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class NextPageCommand extends Command {
    public NextPageCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        if (UserSpaceDb.getDatabase().get(getUsername()).getHistory().jumpNext(UserSpaceDb.getDatabase().get(getUsername()))) {
            this.outputErrorMessage("The user " + getUsername() + " has navigated successfully to the next page.");
            return;
        }
        this.outputErrorMessage("There are no pages left to go forward.");
    }
}
