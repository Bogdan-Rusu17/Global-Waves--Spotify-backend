package main.entities;

import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.globals.LikeDB;

import java.util.ArrayList;

public final class Album {
    private String name;
    private String owner;
    private String description;
    private int releaseYear;
    private ArrayList<SongInput> songs = new ArrayList<>();

    public Album(final String name, final String owner, final String description,
                 final int releaseYear, final ArrayList<SongInput> songs) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.releaseYear = releaseYear;
        this.songs = songs;
    }

    public Artist getArtist() {
        return GlobalObjects.getInstance().existsArtist(owner);
    }

    /**
     *
     * @return sum of number of likes over all melodies in the album
     */
    public int computeTotalLikes() {
        int total = 0;
        for (SongInput song : songs) {
            total += LikeDB.getLikedSongs().get(song);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }
}
