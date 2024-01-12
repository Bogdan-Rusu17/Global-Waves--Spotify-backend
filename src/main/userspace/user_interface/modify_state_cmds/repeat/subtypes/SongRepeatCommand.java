package main.userspace.user_interface.modify_state_cmds.repeat.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.modify_state_cmds.repeat.RepeatCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.SongState;

public final class SongRepeatCommand extends RepeatCommand {
    public SongRepeatCommand(final Command command) {
        super(command);
    }

    /**
     * changes the repeat attribute of the song state taking into account the current
     * type of repeat that is employed
     */
    @Override
    public void execCommand() {
        outputBase();
        String repeatType;

        SongState currentSongState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getSongState();
        if (currentSongState.getRepeat().equals("No Repeat")) {
            currentSongState.setRepeat("Repeat Once");
            repeatType = "repeat once";
        } else if (currentSongState.getRepeat().equals("Repeat Once")) {
            currentSongState.setRepeat("Repeat Infinite");
            repeatType = "repeat infinite";
        } else {
            currentSongState.setRepeat("No Repeat");
            repeatType = "no repeat";
        }
        this.getObjectNode().put("message", "Repeat mode changed to "
                + repeatType + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
