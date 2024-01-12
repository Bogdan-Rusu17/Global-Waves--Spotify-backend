package main.globals;

import fileio.input.SongInput;

import java.util.HashMap;

/**
 * utility class used for storing a hashmap to allow for fast checking
 * of the number of likes for a given song
 */
public final class LikeDB {
    private static HashMap<SongInput, Integer> likedSongs = new HashMap<>();
    private static HashMap<SongInput, Integer> priority = new HashMap<>();

    private LikeDB() {

    }

    /**
     *
     * @param song to add 1 like to said song
     */
    public static void addLike(final SongInput song) {
        if (likedSongs.containsKey(song)) {
            likedSongs.replace(song, likedSongs.get(song) + 1);
            return;
        }
        likedSongs.put(song, 1);
    }

    /**
     *
     * @param song to remove 1 like from said song
     */
    public static void removeLike(final SongInput song) {
        if (likedSongs.containsKey(song)) {
            likedSongs.replace(song, likedSongs.get(song) - 1);
        }
    }

    public static HashMap<SongInput, Integer> getLikedSongs() {
        return likedSongs;
    }

    public static void setLikedSongs(final HashMap<SongInput, Integer> likedSongs) {
        LikeDB.likedSongs = likedSongs;
    }

    public static HashMap<SongInput, Integer> getPriority() {
        return priority;
    }

    public static void setPriority(final HashMap<SongInput, Integer> priority) {
        LikeDB.priority = priority;
    }
}
