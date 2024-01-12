package main.userspace.user_interface.searchbar;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.entities.Host;
import main.entities.Playlist;
import main.userspace.user_interface.searchbar.subtypes.search.SearchCommand;
import main.userspace.user_interface.searchbar.subtypes.select.SelectCommand;

import java.util.ArrayList;

public class SearchBar {
    private SearchCommand search;
    private SelectCommand select;
    private ArrayList<SongInput> songResults;
    private ArrayList<PodcastInput> podcastResults;
    private ArrayList<Playlist> playlistResults;
    private ArrayList<Artist> artistResults;
    private ArrayList<Album> albumResults;
    private ArrayList<Host> hostResults;

    private SongInput selectedSong;
    private PodcastInput selectedPodcast;
    private Playlist selectedPlaylist;
    private Album selectedAlbum;

    public SearchBar() {

    }

    /**
     *
     */
    public SearchCommand getSearch() {
        return search;
    }
    /**
     *
     */
    public void setSearch(final SearchCommand search) {
        this.search = search;
    }
    /**
     *
     */
    public SelectCommand getSelect() {
        return select;
    }
    /**
     *
     */
    public void setSelect(final SelectCommand select) {
        this.select = select;
    }
    /**
     *
     */
    public ArrayList<SongInput> getSongResults() {
        return songResults;
    }
    /**
     *
     */
    public void setSongResults(final ArrayList<SongInput> songResults) {
        this.songResults = songResults;
    }
    /**
     *
     */
    public ArrayList<PodcastInput> getPodcastResults() {
        return podcastResults;
    }
    /**
     *
     */
    public void setPodcastResults(final ArrayList<PodcastInput> podcastResults) {
        this.podcastResults = podcastResults;
    }
    /**
     *
     */
    public ArrayList<Playlist> getPlaylistResults() {
        return playlistResults;
    }
    /**
     *
     */
    public void setPlaylistResults(final ArrayList<Playlist> playlistResults) {
        this.playlistResults = playlistResults;
    }
    /**
     *
     */
    public SongInput getSelectedSong() {
        return selectedSong;
    }
    /**
     *
     */
    public void setSelectedSong(final SongInput selectedSong) {
        this.selectedSong = selectedSong;
    }
    /**
     *
     */
    public PodcastInput getSelectedPodcast() {
        return selectedPodcast;
    }
    /**
     *
     */
    public void setSelectedPodcast(final PodcastInput selectedPodcast) {
        this.selectedPodcast = selectedPodcast;
    }
    /**
     *
     */
    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }
    /**
     *
     */
    public void setSelectedPlaylist(final Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }
    /**
     *
     */
    public ArrayList<Artist> getArtistResults() {
        return artistResults;
    }
    /**
     *
     */
    public void setArtistResults(final ArrayList<Artist> artistResults) {
        this.artistResults = artistResults;
    }
    /**
     *
     */
    public ArrayList<Album> getAlbumResults() {
        return albumResults;
    }
    /**
     *
     */
    public void setAlbumResults(final ArrayList<Album> albumResults) {
        this.albumResults = albumResults;
    }
    /**
     *
     */
    public Album getSelectedAlbum() {
        return selectedAlbum;
    }
    /**
     *
     */
    public void setSelectedAlbum(final Album selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }
    /**
     *
     */
    public ArrayList<Host> getHostResults() {
        return hostResults;
    }
    /**
     *
     */
    public void setHostResults(final ArrayList<Host> hostResults) {
        this.hostResults = hostResults;
    }
}
