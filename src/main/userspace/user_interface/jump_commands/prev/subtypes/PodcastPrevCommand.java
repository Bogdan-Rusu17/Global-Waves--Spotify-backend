package main.userspace.user_interface.jump_commands.prev.subtypes;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.jump_commands.prev.PrevCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class PodcastPrevCommand extends PrevCommand {
    public PodcastPrevCommand(final Command command) {
        super(command);
    }

    /**
     * if the current episode has spent at least 1 second running, we will just
     * return it to the beginning
     * if it's the first episode in the podcast, it just starts it again
     * else, we return to the beginning of the previous episode
     */
    @Override
    public void execCommand() {
        outputBase();
        PodcastState currentPodcastState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getPodcastState();
        if (currentPodcastState.getRemainedTime()
                < currentPodcastState.getPodcast().getEpisodes().get(currentPodcastState
                .getEpisodeIndex()).getDuration()) {
            currentPodcastState.setRemainedTime(currentPodcastState.getPodcast()
                    .getEpisodes().get(currentPodcastState.getEpisodeIndex()).getDuration());
            currentPodcastState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentPodcastState.getPodcast().getEpisodes()
                            .get(currentPodcastState.getEpisodeIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        int prevEpIdx = currentPodcastState.getEpisodeIndex() - 1;
        if (prevEpIdx == -1) {
            currentPodcastState.setRemainedTime(currentPodcastState.getPodcast()
                    .getEpisodes().get(currentPodcastState.getEpisodeIndex()).getDuration());
            currentPodcastState.setPaused(false);
            this.getObjectNode().put("message",
                    "Returned to previous track successfully. The current track is "
                            + currentPodcastState.getPodcast().getEpisodes()
                            .get(currentPodcastState.getEpisodeIndex()).getName() + ".");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        this.getObjectNode().put("message",
                "Returned to previous track successfully. The current track is "
                        + currentPodcastState.getPodcast()
                        .getEpisodes().get(prevEpIdx).getName() + ".");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        currentPodcastState.setEpisodeIndex(prevEpIdx);
        currentPodcastState.setRemainedTime(currentPodcastState.getPodcast()
                .getEpisodes().get(prevEpIdx).getDuration());
        currentPodcastState.setName(currentPodcastState.getPodcast()
                .getEpisodes().get(prevEpIdx).getName());
        currentPodcastState.setPaused(false);
    }
}
