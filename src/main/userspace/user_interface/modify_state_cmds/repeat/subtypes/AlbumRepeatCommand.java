package main.userspace.user_interface.modify_state_cmds.repeat.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.modify_state_cmds.repeat.RepeatCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.AlbumState;

public class AlbumRepeatCommand extends RepeatCommand {
    public AlbumRepeatCommand(final Command command) {
        super(command);
    }

    /**
     * changes the repeat attribute of the song state taking into account the current
     * type of repeat that is employed
     */
    @Override
    public void execCommand() {
        outputBase();
        AlbumState currentAlbumState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getAlbumState();
        String repeatType;
        if (currentAlbumState.getRepeat().equals("No Repeat")) {
            currentAlbumState.setRepeat("Repeat All");
            repeatType = "repeat all";
        } else if (currentAlbumState.getRepeat().equals("Repeat All")) {
            currentAlbumState.setRepeat("Repeat Current Song");
            repeatType = "repeat current song";
        } else {
            currentAlbumState.setRepeat("No Repeat");
            repeatType = "no repeat";
        }
        this.getObjectNode().put("message", "Repeat mode changed to "
                + repeatType + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
