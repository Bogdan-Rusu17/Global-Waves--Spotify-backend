package main.userspace.user_interface.player.subtypes.state.subtypes;

import fileio.input.PodcastInput;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.State;

public final class PodcastState extends State {
    private PodcastInput podcast;
    private int episodeIndex;
    private boolean finished;
    public PodcastState(final String name, final int remainedTime,
                        final PodcastInput podcast, final int episodeIndex) {
        super(name, remainedTime);
        this.podcast = podcast;
        this.episodeIndex = episodeIndex;
    }

    @Override
    public void changeState(final int time, final String username) {
        int timestamp = time;
        if (this.isPaused()) {
            return;
        }
        if (this.getRepeat().equals("No Repeat")) {
            if (timestamp < this.getRemainedTime()) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                return;
            }
            if (episodeIndex + 1 == podcast.getEpisodes().size()) {
                this.setOnFinished();
                finished = true;
                return;
            }
            episodeIndex++;
            timestamp -= this.getRemainedTime();
            while (episodeIndex < podcast.getEpisodes().size() && timestamp >= podcast.getEpisodes()
                    .get(episodeIndex).getDuration()) {
                timestamp -= podcast.getEpisodes().get(episodeIndex).getDuration();
                UserSpaceDb.getDatabase().get(username).getTop()
                        .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
                episodeIndex++;
            }

            if (episodeIndex == podcast.getEpisodes().size()) {
                this.setOnFinished();
                finished = true;
                return;
            }
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
            this.setName(podcast.getEpisodes().get(episodeIndex).getName());
            this.setRemainedTime(podcast.getEpisodes().get(episodeIndex)
                    .getDuration() - timestamp);
            return;
        } else if (this.getRepeat().equals("Repeat Once")) {
            if (timestamp < this.getRemainedTime()) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                return;
            }
            if (timestamp < this.getRemainedTime() + podcast.getEpisodes()
                    .get(episodeIndex).getDuration()) {
                this.setRemainedTime(this.getRemainedTime() + podcast.getEpisodes()
                        .get(episodeIndex).getDuration() - timestamp);
                UserSpaceDb.getDatabase().get(username).getTop()
                        .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
                return;
            }
            if (episodeIndex + 1 == podcast.getEpisodes().size()) {
                this.setOnFinished();
                finished = true;
                UserSpaceDb.getDatabase().get(username).resetAllSelectLoad();
                return;
            }

            timestamp -= this.getRemainedTime() + podcast.getEpisodes()
                    .get(episodeIndex).getDuration();
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
            episodeIndex++;
            while (episodeIndex < podcast.getEpisodes().size()
                    && timestamp >= podcast.getEpisodes().get(episodeIndex).getDuration()) {
                timestamp -= podcast.getEpisodes().get(episodeIndex).getDuration();
                UserSpaceDb.getDatabase().get(username).getTop()
                        .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
                episodeIndex++;
            }

            if (episodeIndex == podcast.getEpisodes().size()) {
                this.setOnFinished();
                finished = true;
                UserSpaceDb.getDatabase().get(username).resetAllSelectLoad();
                return;
            }
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenEpisode(podcast.getEpisodes().get(episodeIndex), 1);
            this.setName(podcast.getEpisodes().get(episodeIndex).getName());
            this.setRemainedTime(podcast.getEpisodes().
                    get(episodeIndex).getDuration() - timestamp);
            return;
        }
        if (this.getRemainedTime() > timestamp) {
            this.setRemainedTime(this.getRemainedTime() - timestamp);
            return;
        }
        this.setRemainedTime(podcast.getEpisodes()
                .get(episodeIndex).getDuration() - (timestamp - this.getRemainedTime())
                % podcast.getEpisodes().get(episodeIndex).getDuration());
        int times = (timestamp - this.getRemainedTime())
                / podcast.getEpisodes().get(episodeIndex).getDuration() + 1;
        UserSpaceDb.getDatabase().get(username).getTop()
                .listenEpisode(podcast.getEpisodes().get(episodeIndex), times);
    }

    public PodcastInput getPodcast() {
        return podcast;
    }

    public void setPodcast(final PodcastInput podcast) {
        this.podcast = podcast;
    }

    public int getEpisodeIndex() {
        return episodeIndex;
    }

    public void setEpisodeIndex(final int episodeIndex) {
        this.episodeIndex = episodeIndex;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }
}
