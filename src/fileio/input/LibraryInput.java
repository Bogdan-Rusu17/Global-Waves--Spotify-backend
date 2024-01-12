package fileio.input;

import main.entities.Album;
import main.entities.Artist;
import main.entities.Host;

import java.util.ArrayList;

public final class LibraryInput {
    private ArrayList<SongInput> songs;
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<UserInput> users;
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Host> hosts = new ArrayList<>();

    public LibraryInput() {
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(final ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Host> getHosts() {
        return hosts;
    }

    public void setHosts(final ArrayList<Host> hosts) {
        this.hosts = hosts;
    }
}
