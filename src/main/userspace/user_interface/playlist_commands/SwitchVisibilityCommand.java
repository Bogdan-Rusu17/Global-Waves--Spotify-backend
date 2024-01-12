package main.userspace.user_interface.playlist_commands;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class SwitchVisibilityCommand extends Command {
    public SwitchVisibilityCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setPlaylistId(command.getPlaylistId());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        outputBase();
        if (this.getPlaylistId() > UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlaylistList().size()) {
            this.getObjectNode().put("message", "The specified playlist ID is too high.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        String message;
        if (UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList()
                .get(this.getPlaylistId() - 1).getVisibility().equals("public")) {
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList()
                    .get(this.getPlaylistId() - 1).setVisibility("private");
            message = "Visibility status updated successfully to private.";
        } else {
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList()
                    .get(this.getPlaylistId() - 1).setVisibility("public");
            message = "Visibility status updated successfully to public.";
        }
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
