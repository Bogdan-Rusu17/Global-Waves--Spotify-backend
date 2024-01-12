package main.userspace.user_interface.jump_commands.forward;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class ForwardCommand extends Command {
    private static final int JUMP = 90;
    public ForwardCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * if the jump makes us go out of the current episode, we change to state
     * of the podcast to go to the next episode if there is one
     * else, we just advance the state of the podcast with 90 seconds
     */
    @Override
    public void execCommand() {
        outputBase();
        PodcastState currentPodcastState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPodcastState();
        if (currentPodcastState.getRemainedTime() < JUMP) {
            currentPodcastState.changeState(currentPodcastState
                    .getRemainedTime(), this.getUsername());
            this.getObjectNode().put("message", "Skipped forward successfully.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        currentPodcastState.changeState(JUMP, this.getUsername());
        this.getObjectNode().put("message", "Skipped forward successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
