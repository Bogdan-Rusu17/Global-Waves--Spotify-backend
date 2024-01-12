package main.admin_commands.add_merch;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.entities.Merch;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class SeeMerchCommand extends Command {
    public SeeMerchCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        ArrayNode merchNode = Command.getObjectMapper().createArrayNode();
        for (Merch merch : UserSpaceDb.getDatabase().get(getUsername()).getBoughtMerch()) {
            merchNode.add(merch.getName());
        }
        this.getObjectNode().put("result", merchNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
