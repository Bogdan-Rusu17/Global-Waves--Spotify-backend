package main.entities;

import fileio.input.SongInput;
import main.globals.LikeDB;

public final class SongSortingHelper {
    private SongInput song;
    private int likes;

    public SongSortingHelper(final SongInput song) {
        this.song = song;
        this.likes = LikeDB.getLikedSongs().get(song);
    }

    public SongInput getSong() {
        return song;
    }

    public void setSong(final SongInput song) {
        this.song = song;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
