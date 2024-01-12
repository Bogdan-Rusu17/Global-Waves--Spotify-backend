package main.userspace.user_interface.player.subtypes.state.subtypes;

import fileio.input.PodcastInput;

import java.util.HashMap;

/**
 * class used for knowing where to start back a podcast
 * that was loaded before
 * for each podcast we save in the resumeMap its state for every
 * command given by the user that watches said podcast
 */
public final class PodcastStateResumer {
    private HashMap<PodcastInput, PodcastState> resumeMap = new HashMap<>();

    public HashMap<PodcastInput, PodcastState> getResumeMap() {
        return resumeMap;
    }

    public void setResumeMap(final HashMap<PodcastInput, PodcastState> resumeMap) {
        this.resumeMap = resumeMap;
    }
}
