package main.notification_system;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class GetNotificationsCommand extends Command {
    public GetNotificationsCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayNode notificationsNode = Command.getObjectMapper().createArrayNode();
        for (Notification notification : UserSpaceDb.getDatabase().get(getUsername()).getNotifications()) {
            ObjectNode node = Command.getObjectMapper().createObjectNode();
            node.put("name", notification.getName());
            node.put("description", notification.getDescription());
            notificationsNode.add(node);
        }
        this.getObjectNode().put("notifications", notificationsNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(getUsername()).getNotifications().clear();
    }
}
