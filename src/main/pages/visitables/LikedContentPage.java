package main.pages.visitables;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.pages.visitors.PageVisitor;

import java.util.ArrayList;

public final class LikedContentPage implements Page {
    private ArrayList<SongInput> likedSongs = new ArrayList<>();
    private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
    /**
     *
     * @param vis visitor of type builder/printer that makes use of the page
     * @param user the owner of the page
     */
    public void accept(final PageVisitor vis, final String user) {
        vis.visit(this, user);
    }

    public ArrayList<SongInput> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(final ArrayList<SongInput> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }
}
