package main.userspace.user_interface.jump_commands.next.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.next.NextCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class PodcastNextCommand extends NextCommand {
    public PodcastNextCommand(final Command command) {
        super(command);
    }

    /**
     * we go into the next episode of the podcast
     * if there is none we output an error message
     */
    @Override
    public void execCommand() {
        outputBase();
        PodcastState currentPodcastState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getPodcastState();
        currentPodcastState.setPaused(false);
        currentPodcastState.changeState(currentPodcastState.getRemainedTime(),
                this.getUsername());
        if (currentPodcastState.isFinished()) {
            this.getObjectNode().put("message", "Please load a source before skipping"
                    + " to the next track.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        String message = "Skipped to next track successfully. The current track is ";
        this.getObjectNode().put("message", message +  currentPodcastState.getPodcast()
                .getEpisodes().get(currentPodcastState.getEpisodeIndex()).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
