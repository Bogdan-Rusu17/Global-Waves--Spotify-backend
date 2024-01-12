package main.admin_commands.add_user.subtypes;

import fileio.input.UserInput;
import main.admin_commands.add_user.AddUserCommand;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.UserInterface;
import main.userspace.user_interface.player.Player;
import main.userspace.user_interface.searchbar.SearchBar;

public final class AddNormalUserCommand extends AddUserCommand {
    public AddNormalUserCommand(final Command command) {
        super(command);
    }
    /**
     * verifies if a user with the same name already exists and if not
     * adds it to the corresponding section in the library
     */
    @Override
    public void execCommand() {
        this.outputBase();
        boolean alreadyTaken = false;
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(getUsername())) {
                alreadyTaken = true;
                break;
            }
        }
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            if (artist.getUsername().equals(this.getUsername())) {
                alreadyTaken = true;
                break;
            }
        }
        if (alreadyTaken) {
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " is already taken.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        UserInput newUser = new UserInput();
        newUser.setAge(getAge());
        newUser.setCity(getCity());
        newUser.setUsername(getUsername());
        GlobalObjects.getInstance().getLibrary().getUsers().add(newUser);
        UserInterface newUI = new UserInterface();
        newUI.setSearchBar(new SearchBar());
        newUI.setPlayer(new Player());
        UserSpaceDb.getDatabase().put(getUsername(), newUI);
        this.getObjectNode().put("message", "The username "
                + this.getUsername() + " has been added successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
