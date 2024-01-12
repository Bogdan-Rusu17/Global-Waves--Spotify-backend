package main.entities;

import fileio.input.SongInput;
import main.globals.LikeDB;
import main.notification_system.Notification;
import main.notification_system.Observer;
import main.notification_system.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * class used for the playlist Audio Entity
 */
public final class Playlist implements Subject {
    private ArrayList<SongInput> songList = new ArrayList<>();
    private String name;
    private String owner;
    private String visibility;
    private int followers;
    private int totalLikes;
    private List<Observer> observers = new ArrayList<>();

    /**
     *
     * @param name for playlist name
     * @param owner for playlist owner
     * @param visibility for playlist visibility
     * @param followers for number of playlist followers
     */
    public Playlist(final String name, final String owner, final String visibility,
                    final int followers) {
        this.name = name;
        this.owner = owner;
        this.visibility = visibility;
        this.followers = followers;
    }
    @Override
    public void attach(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void dettach(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(Notification notification) {
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }
    /**
     *
     * @param song for song to be added into current playlist instance
     */
    public void addSong(final SongInput song) {
        songList.add(song);
    }

    public ArrayList<SongInput> getSongList() {
        return songList;
    }

    public void setSongList(final ArrayList<SongInput> songList) {
        this.songList = songList;
    }

    /**
     *
     * @return total likes across all songs contained in this playlist
     */
    public int computeTotalLikes() {
        int total = 0;
        for (SongInput song : songList) {
            if (LikeDB.getLikedSongs().containsKey(song)) {
                total += LikeDB.getLikedSongs().get(song);
            }
        }
        return total;
    }
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(final int followers) {
        this.followers = followers;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(final int totalLikes) {
        this.totalLikes = totalLikes;
    }
}
