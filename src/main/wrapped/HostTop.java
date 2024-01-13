package main.wrapped;

import fileio.input.EpisodeInput;

import java.util.HashMap;

public final class HostTop {
    private HashMap<String, Integer> fans = new HashMap<>();
    private HashMap<String, Integer> topEpisodes = new HashMap<>();
    public HostTop() {

    }

    /**
     *
     * @param episode the listener listened to
     * @param times number of times the listener listened to the episode
     * @param listener who listened to the episode
     * the method increments the hashmap value of the episode
     * to how many times the user listened to it
     * and calls the getListenerFan() method to account for how many
     * times the user listened to the episode
     */
    public void getListenerEpisode(final EpisodeInput episode, final int times,
                                   final String listener) {
        if (!topEpisodes.containsKey(episode.getName())) {
            topEpisodes.put(episode.getName(), times);
        } else {
            int noListens = topEpisodes.get(episode.getName()) + times;
            topEpisodes.replace(episode.getName(), noListens);
        }
        getListenerFan(times, listener);
    }

    /**
     *
     * @param times number of times the listener listened to the episode
     * @param listener who listened to the episode
     * the method increments the hashmap value of the user listens
     * to how many times the user listened to it
     */
    public void getListenerFan(final int times, final String listener) {
        if (!fans.containsKey(listener)) {
            fans.put(listener, times);
        } else {
            int noListens = fans.get(listener) + times;
            fans.replace(listener, noListens);
        }
    }

    /**
     *
     *
     */
    public HashMap<String, Integer> getFans() {
        return fans;
    }
    /**
     *
     *
     */
    public void setFans(final HashMap<String, Integer> fans) {
        this.fans = fans;
    }
    /**
     *
     *
     */
    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }
    /**
     *
     *
     */
    public void setTopEpisodes(final HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }
}
