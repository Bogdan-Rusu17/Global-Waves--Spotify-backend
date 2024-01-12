package main.userspace.user_interface.modify_state_cmds.repeat.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.modify_state_cmds.repeat.RepeatCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class PodcastRepeatCommand extends RepeatCommand {
    public PodcastRepeatCommand(final Command command) {
        super(command);
    }

    /**
     * changes the repeat attribute of the song state taking into account the current
     * type of repeat that is employed
     */
    @Override
    public void execCommand() {
        outputBase();
        PodcastState currentPodcastState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPodcastState();
        String repeatType;
        if (currentPodcastState.getRepeat().equals("No Repeat")) {
            currentPodcastState.setRepeat("Repeat Once");
            repeatType = "repeat once";
        } else if (currentPodcastState.getRepeat().equals("Repeat Once")) {
            currentPodcastState.setRepeat("Repeat Infinite");
            repeatType = "repeat infinite";
        } else {
            currentPodcastState.setRepeat("No Repeat");
            repeatType = "no repeat";
        }
        this.getObjectNode().put("message", "Repeat mode changed to "
                + repeatType + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
