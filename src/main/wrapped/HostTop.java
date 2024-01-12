package main.wrapped;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import main.globals.GlobalObjects;

import java.util.HashMap;

public class HostTop {
    private HashMap<String, Integer> fans = new HashMap<>();
    private HashMap<String, Integer> topEpisodes = new HashMap<>();
    public HostTop() {

    }
    public void getListenerEpisode(EpisodeInput episode, int times, String listener) {
        if (!topEpisodes.containsKey(episode.getName())) {
            topEpisodes.put(episode.getName(), times);
        } else {
            int noListens = topEpisodes.get(episode.getName()) + times;
            topEpisodes.replace(episode.getName(), noListens);
        }
        getListenerFan(times, listener);
    }
    public void getListenerFan(int times, String listener) {
        if (!fans.containsKey(listener)) {
            fans.put(listener, times);
        } else {
            int noListens = fans.get(listener) + times;
            fans.replace(listener, noListens);
        }
    }

    public HashMap<String, Integer> getFans() {
        return fans;
    }

    public void setFans(HashMap<String, Integer> fans) {
        this.fans = fans;
    }

    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }
}
