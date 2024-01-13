package main.wrapped;

import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.user_interface.UserInterface;

import java.util.HashMap;

public class UserTop {
    private HashMap<String, Integer> topArtists = new HashMap<>();
    private HashMap<String, Integer> topGenres = new HashMap<>();
    private HashMap<String, Integer> topSongs = new HashMap<>();
    private HashMap<String, Integer> topAlbums = new HashMap<>();
    private HashMap<String, Integer> topEpisodes = new HashMap<>();
    private HashMap<String, Integer> timeFrameListenedSongs = new HashMap<>();
    private HashMap<String, Integer> MonetizedListenedSongs = new HashMap<>();
    private UserInterface ui;

    public UserTop(UserInterface ui) {
        this.ui = ui;
    }
    public void listenSong(SongInput song, int times) {
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
            if (!MonetizedListenedSongs.containsKey(song.getName())) {
                MonetizedListenedSongs.put(song.getName(), times);
            } else {
                int timeFrameListened = MonetizedListenedSongs.get(song.getName()) + times;
                MonetizedListenedSongs.replace(song.getName(), timeFrameListened);
            }
        }
        listenArtist(GlobalObjects.getInstance().existsArtist(song.getArtist()), times);
        listenGenre(song.getGenre(), times);
        if (GlobalObjects.getInstance().getAlbumWithSong(song) != null)
            listenAlbum(GlobalObjects.getInstance().getAlbumWithSong(song), times);
    }
    public void listenGenre(String genre, int times) {
        if (!topGenres.containsKey(genre)) {
            topGenres.put(genre, times);
        } else {
            int noListens = topGenres.get(genre) + times;
            topGenres.replace(genre, noListens);
        }
    }
    public void listenArtist(Artist artist, int times) {
        if (!topArtists.containsKey(artist.getUsername())) {
            topArtists.put(artist.getUsername(), times);
        } else {
            int noListens = topArtists.get(artist.getUsername()) + times;
            topArtists.replace(artist.getUsername(), noListens);
        }
    }
    public void listenAlbum(Album album, int times) {
        if (!topAlbums.containsKey(album.getName())) {
            topAlbums.put(album.getName(), times);
        } else {
            int noListens = topAlbums.get(album.getName()) + times;
            topAlbums.replace(album.getName(), noListens);
        }
    }
    public void listenEpisode(EpisodeInput episode, int times) {
        if (!topEpisodes.containsKey(episode.getName())) {
            topEpisodes.put(episode.getName(), times);
        } else {
            int noListens = topEpisodes.get(episode.getName()) + times;
            topEpisodes.replace(episode.getName(), noListens);
        }
    }

    public HashMap<String, Integer> getMonetizedListenedSongs() {
        return MonetizedListenedSongs;
    }

    public void setMonetizedListenedSongs(HashMap<String, Integer> monetizedListenedSongs) {
        MonetizedListenedSongs = monetizedListenedSongs;
    }

    public HashMap<String, Integer> getTimeFrameListenedSongs() {
        return timeFrameListenedSongs;
    }

    public void setTimeFrameListenedSongs(HashMap<String, Integer> timeFrameListenedSongs) {
        this.timeFrameListenedSongs = timeFrameListenedSongs;
    }

    public HashMap<String, Integer> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(HashMap<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    public HashMap<String, Integer> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(HashMap<String, Integer> topGenres) {
        this.topGenres = topGenres;
    }

    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }
}
