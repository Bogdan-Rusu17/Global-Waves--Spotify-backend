package main.userspace.user_interface.player.subtypes.load.subtypes;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.load.LoadCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public final class PodcastLoadCommand extends LoadCommand {
    public PodcastLoadCommand(final Command command) {
        super(command);
    }

    /**
     * sets the loadedPodcast parameter of the player to the selected podcast
     * instances a new, default state for a podcast
     * and instances a new resumer for this podcast that helps the user
     * start back the podcast where he left before, if he listened to it before
     */
    @Override
    public void execCommand() {
        this.outputBase();
        PodcastInput selectedPodcast = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getSelectedPodcast();
        this.getObjectNode().put("message", "Playback loaded successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadedPodcast(selectedPodcast);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadTimestamp(this.getTimestamp());
        if (!UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().getResumer()
                .getResumeMap().containsKey(selectedPodcast)) {
            EpisodeInput firstEpisode = selectedPodcast.getEpisodes().get(0);
            UserSpaceDb.getDatabase().get(this.getUsername()).getTop().listenEpisode(firstEpisode, 1);
            if (GlobalObjects.getInstance().existsHost(selectedPodcast.getOwner()) != null)
                GlobalObjects.getInstance().existsHost(selectedPodcast.getOwner()).getTop()
                    .getListenerEpisode(firstEpisode, 1, getUsername());
            PodcastState newState = new PodcastState(firstEpisode.getName(),
                    firstEpisode.getDuration(), selectedPodcast, 0);
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setPodcastState(newState);
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().getResumer()
                    .getResumeMap().put(selectedPodcast, newState);
            return;
        }
        PodcastState previousState = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getResumer().getResumeMap().get(selectedPodcast);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setPodcastState(previousState);
        UserSpaceDb.getDatabase().get(this.getUsername()).getTop().listenEpisode(previousState.getPodcast()
                .getEpisodes().get(previousState.getEpisodeIndex()), 1);
        if (GlobalObjects.getInstance().existsHost(selectedPodcast.getOwner()) != null)
            GlobalObjects.getInstance().existsHost(selectedPodcast.getOwner()).getTop()
                    .getListenerEpisode(previousState.getPodcast()
                            .getEpisodes().get(previousState.getEpisodeIndex()), 1, getUsername());
        this.deleteSelection();
    }
}
