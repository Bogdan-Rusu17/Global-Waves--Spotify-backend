package main.wrapped;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.user_interface.UserInterface;

import java.util.HashMap;

public final class UserTop {
    private HashMap<String, Integer> topArtists = new HashMap<>();
    private HashMap<String, Integer> topGenres = new HashMap<>();
    private HashMap<String, Integer> topSongs = new HashMap<>();
    private HashMap<String, Integer> topAlbums = new HashMap<>();
    private HashMap<String, Integer> topEpisodes = new HashMap<>();
    private HashMap<String, Integer> timeFrameListenedSongs = new HashMap<>();
    private HashMap<String, Integer> monetizedListenedSongs = new HashMap<>();
    private UserInterface ui;

    public UserTop(final UserInterface ui) {
        this.ui = ui;
    }

    /**
     *
     * @param song to be marked in the hashmap as listened to
     * @param times number of times to be marked as listened to
     */
    public void listenSong(final SongInput song, final int times) {
        if (!topSongs.containsKey(song.getName())) {
            topSongs.put(song.getName(), times);
        } else {
            int noListens = topSongs.get(song.getName()) + times;
            topSongs.replace(song.getName(), noListens);

        }
        if (ui.isPremiumUser()) {
            if (!timeFrameListenedSongs.containsKey(song.getName())) {
                timeFrameListenedSongs.put(song.getName(), times);
            } else {
                int timeFrameListened = timeFrameListenedSongs.get(song.getName()) + times;
                timeFrameListenedSongs.replace(song.getName(), timeFrameListened);
            }
        } else {
            if (!monetizedListenedSongs.containsKey(song.getName())) {
                monetizedListenedSongs.put(song.getName(), times);
            } else {
                int timeFrameListened = monetizedListenedSongs.get(song.getName()) + times;
                monetizedListenedSongs.replace(song.getName(), timeFrameListened);
            }
        }
        listenArtist(GlobalObjects.getInstance().existsArtist(song.getArtist()), times);
        listenGenre(song.getGenre(), times);
        if (GlobalObjects.getInstance().getAlbumWithSong(song) != null) {
            listenAlbum(GlobalObjects.getInstance().getAlbumWithSong(song), times);
        }
    }
    /**
     * @param genre to be marked in the hashmap as listened to
     * @param times number of times to be marked as listened to
     */
    public void listenGenre(final String genre, final int times) {
        if (!topGenres.containsKey(genre)) {
            topGenres.put(genre, times);
        } else {
            int noListens = topGenres.get(genre) + times;
            topGenres.replace(genre, noListens);
        }
    }
    /**
     * @param artist to be marked in the hashmap as listened to
     * @param times number of times to be marked as listened to
     */
    public void listenArtist(final Artist artist, final int times) {
        if (!topArtists.containsKey(artist.getUsername())) {
            topArtists.put(artist.getUsername(), times);
        } else {
            int noListens = topArtists.get(artist.getUsername()) + times;
            topArtists.replace(artist.getUsername(), noListens);
        }
    }
    /**
     * @param album to be marked in the hashmap as listened to
     * @param times number of times to be marked as listened to
     */
    public void listenAlbum(final Album album, final int times) {
        if (!topAlbums.containsKey(album.getName())) {
            topAlbums.put(album.getName(), times);
        } else {
            int noListens = topAlbums.get(album.getName()) + times;
            topAlbums.replace(album.getName(), noListens);
        }
    }
    /**
     * @param episode to be marked in the hashmap as listened to
     * @param times number of times to be marked as listened to
     */
    public void listenEpisode(final EpisodeInput episode, final int times) {
        if (!topEpisodes.containsKey(episode.getName())) {
            topEpisodes.put(episode.getName(), times);
        } else {
            int noListens = topEpisodes.get(episode.getName()) + times;
            topEpisodes.replace(episode.getName(), noListens);
        }
    }

    public HashMap<String, Integer> getMonetizedListenedSongs() {
        return monetizedListenedSongs;
    }

    public void setMonetizedListenedSongs(final HashMap<String, Integer> monetizedListenedSongs) {
        this.monetizedListenedSongs = monetizedListenedSongs;
    }

    public HashMap<String, Integer> getTimeFrameListenedSongs() {
        return timeFrameListenedSongs;
    }

    public void setTimeFrameListenedSongs(final HashMap<String, Integer> timeFrameListenedSongs) {
        this.timeFrameListenedSongs = timeFrameListenedSongs;
    }

    public HashMap<String, Integer> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(final HashMap<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    public HashMap<String, Integer> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(final HashMap<String, Integer> topGenres) {
        this.topGenres = topGenres;
    }

    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(final HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(final HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(final HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }
}
