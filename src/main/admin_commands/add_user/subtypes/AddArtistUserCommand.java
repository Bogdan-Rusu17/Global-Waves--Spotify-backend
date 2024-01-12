package main.admin_commands.add_user.subtypes;

import fileio.input.UserInput;
import main.admin_commands.add_user.AddUserCommand;
import main.entities.Artist;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class AddArtistUserCommand extends AddUserCommand {
    public AddArtistUserCommand(final Command command) {
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
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            if (artist.getUsername().equals(this.getUsername())) {
                alreadyTaken = true;
                break;
            }
        }
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (user.getUsername().equals(this.getUsername())) {
                alreadyTaken = true;
                break;
            }
        }
        for (Host host : GlobalObjects.getInstance().getLibrary().getHosts()) {
            if (host.getUsername().equals(this.getUsername())) {
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
        Artist newArtist = new Artist(this.getUsername(), this.getAge(), this.getCity());
        GlobalObjects.getInstance().getLibrary().getArtists().add(newArtist);
        this.getObjectNode().put("message", "The username "
                + this.getUsername() + " has been added successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());

    }
}
