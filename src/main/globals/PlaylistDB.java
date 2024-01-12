package main.globals;

import main.entities.Playlist;

import java.util.ArrayList;

/**
 * utility class used for storing created playlists
 */
public final class PlaylistDB {
    private static ArrayList<Playlist> playlists = new ArrayList<>();

    private PlaylistDB() {

    }

    /**
     *
     * @param playlist to be added to the list of playlists in the database
     */
    public static void addPlaylist(final Playlist playlist) {
        playlists.add(playlist);
    }

    public static ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public static void setPlaylists(final ArrayList<Playlist> playlists) {
        PlaylistDB.playlists = playlists;
    }
}
