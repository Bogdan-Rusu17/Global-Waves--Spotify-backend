package main.userspace.user_interface;

import fileio.input.SongInput;
import main.entities.Merch;
import main.entities.Playlist;
import main.notification_system.Notification;
import main.notification_system.Observer;
import main.pages.navigation_history.NavigationHistoryCaretaker;
import main.pages.navigation_history.PageStateMemento;
import main.pages.visitables.HomePage;
import main.pages.visitables.Page;
import main.userspace.user_interface.player.Player;
import main.userspace.user_interface.searchbar.SearchBar;
import main.wrapped.UserTop;

import java.util.ArrayList;
import java.util.HashMap;

public final class UserInterface implements Observer {
    private boolean connectionStat;
    private SearchBar searchBar;
    private Player player;
    private ArrayList<Playlist> playlistList = new ArrayList<>();
    private ArrayList<SongInput> likedSongs = new ArrayList<>();
    private Page userPage = new HomePage();
    private HashMap<Playlist, Integer> isFollowingMap = new HashMap<>();
    private UserTop top = new UserTop(this);
    private boolean premiumUser = false;
    private boolean incomingAd = false;
    private int adPrice;
    private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<Merch> boughtMerch = new ArrayList<>();
    private NavigationHistoryCaretaker history = new NavigationHistoryCaretaker();
    private ArrayList<SongInput> recommendedSongs = new ArrayList<>();
    private ArrayList<Playlist> recommendedPlaylists = new ArrayList<>();
    private String lastRecommendation = "";

    public UserInterface() {
        connectionStat = true;
    }

    @Override
    public void update(final Notification notification) {
        notifications.add(notification);
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

    public String getLastRecommendation() {
        return lastRecommendation;
    }

    public void setLastRecommendation(final String lastRecommendation) {
        this.lastRecommendation = lastRecommendation;
    }

    public ArrayList<SongInput> getRecommendedSongs() {
        return recommendedSongs;
    }

    public void setRecommendedSongs(final ArrayList<SongInput> recommendedSongs) {
        this.recommendedSongs = recommendedSongs;
    }

    public ArrayList<Playlist> getRecommendedPlaylists() {
        return recommendedPlaylists;
    }

    public void setRecommendedPlaylists(final ArrayList<Playlist> recommendedPlaylists) {
        this.recommendedPlaylists = recommendedPlaylists;
    }

    public NavigationHistoryCaretaker getHistory() {
        return history;
    }

    public void setHistory(final NavigationHistoryCaretaker history) {
        this.history = history;
    }

    /**
     *
     * @return page memento/snapshot of the current page the user is on
     */
    public PageStateMemento savePageStateMemento() {
        return new PageStateMemento(userPage);
    }

    /**
     *
     * @param pageStateMemento snapshot used to restore the user page
     */
    public void restorePageStateMemento(final PageStateMemento pageStateMemento) {
        userPage = pageStateMemento.getPage();
    }

    public ArrayList<Merch> getBoughtMerch() {
        return boughtMerch;
    }

    public void setBoughtMerch(final ArrayList<Merch> boughtMerch) {
        this.boughtMerch = boughtMerch;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public boolean isIncomingAd() {
        return incomingAd;
    }

    public void setIncomingAd(final boolean incomingAd) {
        this.incomingAd = incomingAd;
    }

    public int getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(final int adPrice) {
        this.adPrice = adPrice;
    }

    public boolean isPremiumUser() {
        return premiumUser;
    }

    public void setPremiumUser(final boolean premiumUser) {
        this.premiumUser = premiumUser;
    }

    public UserTop getTop() {
        return top;
    }

    public void setTop(final UserTop top) {
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
