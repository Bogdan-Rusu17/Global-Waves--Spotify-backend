package main.userspace.user_interface.jump_commands.backward;

import fileio.input.EpisodeInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class BackwardCommand extends Command {
    private static final int JUMP = 90;
    public BackwardCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * if after the jump we'd be at a negative timestamp, we just make the
     * podcast episode start again
     * else, we have to update the state of the podcast, taking into account
     * that time jump of 90 as if we'd have 90 plus seconds of the remaining time
     */
    @Override
    public void execCommand() {
        outputBase();
        PodcastState currentPodcastState = UserSpaceDb.getDatabase().get(this
                .getUsername()).getPlayer().getPodcastState();
        EpisodeInput currentEpisode = currentPodcastState.getPodcast().getEpisodes()
                .get(currentPodcastState.getEpisodeIndex());
        if (currentEpisode.getDuration() - currentPodcastState.getRemainedTime() < JUMP) {
            currentPodcastState.setRemainedTime(currentEpisode.getDuration());
            this.getObjectNode().put("message", "Rewound successfully.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        currentPodcastState.setRemainedTime(currentPodcastState.getRemainedTime() + JUMP);
        this.getObjectNode().put("message", "Rewound successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());

    }
}
