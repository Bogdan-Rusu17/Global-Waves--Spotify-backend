package main.userspace.user_interface;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.pages.visitables.HomePage;
import main.pages.visitables.Page;
import main.userspace.user_interface.player.Player;
import main.userspace.user_interface.searchbar.SearchBar;
import main.wrapped.UserTop;

import java.util.ArrayList;
import java.util.HashMap;

public final class UserInterface {
    private boolean connectionStat;
    private SearchBar searchBar;
    private Player player;
    private ArrayList<Playlist> playlistList = new ArrayList<>();
    private ArrayList<SongInput> likedSongs = new ArrayList<>();
    private Page userPage = new HomePage();
    private HashMap<Playlist, Integer> isFollowingMap = new HashMap<>();
    private UserTop top = new UserTop();

    public UserInterface() {
        connectionStat = true;
    }

    /**
     * resets all selections, search results and loads
     */
    public void resetAllSelectLoad() {
        this.getSearchBar().setSongResults(null);
        this.getSearchBar().setPodcastResults(null);
        this.getSearchBar().setPlaylistResults(null);
        this.getSearchBar().setArtistResults(null);
        this.getSearchBar().setHostResults(null);
        this.getSearchBar().setAlbumResults(null);

        this.getSearchBar().setSelectedSong(null);
        this.getSearchBar().setSelectedPodcast(null);
        this.getSearchBar().setSelectedPlaylist(null);
        this.getSearchBar().setSelectedAlbum(null);

        this.getPlayer().setLoadedPlaylist(null);
        this.getPlayer().setLoadedSong(null);
        this.getPlayer().setLoadedPodcast(null);
        this.getPlayer().setLoadedAlbum(null);


    }

    public UserTop getTop() {
        return top;
    }

    public void setTop(UserTop top) {
        this.top = top;
    }

    public Page getUserPage() {
        return userPage;
    }

    public void setUserPage(final Page userPage) {
        this.userPage = userPage;
    }

    public boolean getConnectionStat() {
        return connectionStat;
    }

    public void setConnectionStat(final boolean connectionStat) {
        this.connectionStat = connectionStat;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(final SearchBar searchBar) {
        this.searchBar = searchBar;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public ArrayList<Playlist> getPlaylistList() {
        return playlistList;
    }

    public void setPlaylistList(final ArrayList<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    public ArrayList<SongInput> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(final ArrayList<SongInput> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public HashMap<Playlist, Integer> getIsFollowingMap() {
        return isFollowingMap;
    }

    public void setIsFollowingMap(final HashMap<Playlist, Integer> isFollowingMap) {
        this.isFollowingMap = isFollowingMap;
    }
}
