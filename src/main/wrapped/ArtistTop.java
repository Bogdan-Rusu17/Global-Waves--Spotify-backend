package main.wrapped;

import fileio.input.SongInput;
import main.entities.Album;
import main.globals.GlobalObjects;

import java.util.HashMap;

public final class ArtistTop {
    private HashMap<String, Integer> topFans = new HashMap<>();
    private HashMap<String, Integer> topSongs = new HashMap<>();
    private HashMap<String, Integer> topAlbums = new HashMap<>();
    private double songRevenue = 0;
    private double merchRevenue = 0;
    private HashMap<String, Double> revenuePerSongs = new HashMap<>();

    public ArtistTop() {

    }

    /**
     *
     * @param song listened
     * @param times number of times it was listened
     * @param listener who listened
     * adds the currently listened song to the hashmap of top songs
     * and the first time the song is encountered the revenue for that song
     * is set to 0
     * we also increment the current fan (listener) in the hashmap of listeners
     * and if the song belongs to an album we also increment the listenCount for
     * that album
     */
    public void getListenerSong(final SongInput song, final int times, final String listener) {
        if (!topSongs.containsKey(song.getName())) {
            topSongs.put(song.getName(), times);
            revenuePerSongs.put(song.getName(), 0.0);
        } else {
            int noListens = topSongs.get(song.getName()) + times;
            topSongs.replace(song.getName(), noListens);
        }
        if (GlobalObjects.getInstance().getAlbumWithSong(song) != null) {
            getListenerAlbum(GlobalObjects.getInstance().getAlbumWithSong(song), times);
        }
        getListenerFan(times, listener);
    }

    /**
     *
     * @param album to be incremented that it is being listened
     * @param times to increment
     */
    public void getListenerAlbum(final Album album, final int times) {
        if (!topAlbums.containsKey(album.getName())) {
            topAlbums.put(album.getName(), times);
        } else {
            int noListens = topAlbums.get(album.getName()) + times;
            topAlbums.replace(album.getName(), noListens);
        }
    }

    /**
     *
     * @param times to increment
     * @param listener to be marked as having listened to the artist for times @param times
     */
    public void getListenerFan(final int times, final String listener) {
        if (!topFans.containsKey(listener)) {
            topFans.put(listener, times);
        } else {
            int noListens = topFans.get(listener) + times;
            topFans.replace(listener, noListens);
        }
    }

    /**
     *
     * @return total merch revenue of artist
     */
    public double getMerchRevenue() {
        return merchRevenue;
    }

    /**
     *
     *
     */
    public void setMerchRevenue(final double merchRevenue) {
        this.merchRevenue = merchRevenue;
    }

    /**
     *
     * @return get total song revenue of artist
     */
    public double getSongRevenue() {
        return songRevenue;
    }

    /**
     *
     *
     */
    public void setSongRevenue(final double songRevenue) {
        this.songRevenue = songRevenue;
    }

    /**
     *
     *
     */
    public HashMap<String, Double> getRevenuePerSongs() {
        return revenuePerSongs;
    }
    /**
     *
     *
     */
    public void setRevenuePerSongs(final HashMap<String, Double> revenuePerSongs) {
        this.revenuePerSongs = revenuePerSongs;
    }
    /**
     *
     *
     */
    public HashMap<String, Integer> getTopFans() {
        return topFans;
    }
    /**
     *
     *
     */
    public void setTopFans(final HashMap<String, Integer> topFans) {
        this.topFans = topFans;
    }
    /**
     *
     *
     */
    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }
    /**
     *
     *
     */
    public void setTopSongs(final HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }
    /**
     *
     *
     */
    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }
    /**
     *
     *
     */
    public void setTopAlbums(final HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }
}
