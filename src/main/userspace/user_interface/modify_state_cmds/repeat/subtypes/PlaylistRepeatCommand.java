package main.userspace.user_interface.modify_state_cmds.repeat.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.modify_state_cmds.repeat.RepeatCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

public final class PlaylistRepeatCommand extends RepeatCommand {
    public PlaylistRepeatCommand(final Command command) {
        super(command);
    }

    /**
     * changes the repeat attribute of the song state taking into account the current
     * type of repeat that is employed
     */
    @Override
    public void execCommand() {
        outputBase();
        PlaylistState currentPlaylistState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPlaylistState();
        String repeatType;
        if (currentPlaylistState.getRepeat().equals("No Repeat")) {
            currentPlaylistState.setRepeat("Repeat All");
            repeatType = "repeat all";
        } else if (currentPlaylistState.getRepeat().equals("Repeat All")) {
            currentPlaylistState.setRepeat("Repeat Current Song");
            repeatType = "repeat current song";
        } else {
            currentPlaylistState.setRepeat("No Repeat");
            repeatType = "no repeat";
        }
        this.getObjectNode().put("message", "Repeat mode changed to "
                + repeatType + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
