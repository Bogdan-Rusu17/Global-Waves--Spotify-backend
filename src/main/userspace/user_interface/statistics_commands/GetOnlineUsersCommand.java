package main.userspace.user_interface.statistics_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public final class GetOnlineUsersCommand extends Command {
    public GetOnlineUsersCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
    }
    @Override
    public void execCommand() {
        this.getObjectNode().put("command", "getOnlineUsers");
        this.getObjectNode().put("timestamp", getTimestamp());
        ArrayList<String> onlineList = new ArrayList<>();
        for (Map.Entry<String, UserInterface> entry : UserSpaceDb.getDatabase().entrySet()) {
            if (entry.getValue().getConnectionStat()) {
                onlineList.add(entry.getKey());
            }
        }
        Collections.sort(onlineList);
        ArrayNode onlineUsers = Command.getObjectMapper().createArrayNode();
        for (String username : onlineList) {
            onlineUsers.add(username);
        }
        this.getObjectNode().put("result", onlineUsers);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
