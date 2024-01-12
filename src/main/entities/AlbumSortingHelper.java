package main.entities;

import java.util.Comparator;

public final class AlbumSortingHelper {
    private Album album;
    private int likes;

    public AlbumSortingHelper(final Album album, final int likes) {
        this.album = album;
        this.likes = likes;
    }

    public static final class AlbumComparator implements Comparator<AlbumSortingHelper> {
        @Override
        public int compare(final AlbumSortingHelper o1, final AlbumSortingHelper o2) {
            int compLikes = Integer.compare(o2.getLikes(), o1.getLikes());
            if (compLikes == 0) {
                return o1.getAlbum().getName().compareTo(o2.getAlbum().getName());
            }
            return compLikes;
        }
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(final Album album) {
        this.album = album;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
