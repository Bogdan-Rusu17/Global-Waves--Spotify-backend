package main.entities;

public final class ArtistSortingHelper {
    private Artist artist;
    private int likes;

    public ArtistSortingHelper(final Artist artist, final int likes) {
        this.artist = artist;
        this.likes = likes;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(final Artist artist) {
        this.artist = artist;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
