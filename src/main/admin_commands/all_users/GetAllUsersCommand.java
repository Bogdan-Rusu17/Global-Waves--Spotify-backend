package main.admin_commands.all_users;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.UserInput;
import main.entities.Artist;
import main.entities.Host;
import main.globals.GlobalObjects;
import main.userspace.Command;

import java.util.ArrayList;

public final class GetAllUsersCommand extends Command {
    public GetAllUsersCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        this.getObjectNode().put("command", getCommand());
        this.getObjectNode().put("timestamp", getTimestamp());
        ArrayList<String> allUsers = new ArrayList<>();
        for (UserInput normalUser : GlobalObjects.getInstance().getLibrary().getUsers()) {
            allUsers.add(normalUser.getUsername());
        }
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            allUsers.add(artist.getUsername());
        }
        for (Host host : GlobalObjects.getInstance().getLibrary().getHosts()) {
            allUsers.add(host.getUsername());
        }
        ArrayNode usersNode = Command.getObjectMapper().createArrayNode();
        for (String user : allUsers) {
            usersNode.add(user);
        }
        this.getObjectNode().put("result", usersNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
