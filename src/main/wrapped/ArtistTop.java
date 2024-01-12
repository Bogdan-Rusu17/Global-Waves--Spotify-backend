package main.wrapped;

import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;

import java.util.HashMap;

public class ArtistTop {
    private HashMap<String, Integer> topFans = new HashMap<>();
    private HashMap<String, Integer> topSongs = new HashMap<>();
    private HashMap<String, Integer> topAlbums = new HashMap<>();

    public ArtistTop() {

    }

    public void getListenerSong(SongInput song, int times, String listener) {
        if (!topSongs.containsKey(song.getName())) {
            topSongs.put(song.getName(), times);
        } else {
            int noListens = topSongs.get(song.getName()) + times;
            topSongs.replace(song.getName(), noListens);
        }
        getListenerAlbum(GlobalObjects.getInstance().getAlbumWithSong(song), times);
        getListenerFan(times, listener);
    }
    public void getListenerAlbum(Album album, int times) {
        if (!topAlbums.containsKey(album.getName())) {
            topAlbums.put(album.getName(), times);
        } else {
            int noListens = topAlbums.get(album.getName()) + times;
            topAlbums.replace(album.getName(), noListens);
        }
    }
    public void getListenerFan(int times, String listener) {
        if (!topFans.containsKey(listener)) {
            topFans.put(listener, times);
        } else {
            int noListens = topFans.get(listener) + times;
            topFans.replace(listener, noListens);
        }
    }

    public HashMap<String, Integer> getTopFans() {
        return topFans;
    }

    public void setTopFans(HashMap<String, Integer> topFans) {
        this.topFans = topFans;
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
}
