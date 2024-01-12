package main.userspace.user_interface.player;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Playlist;
import main.userspace.user_interface.player.subtypes.state.subtypes.*;

public class Player {
    private SongInput loadedSong;
    private Playlist loadedPlaylist;
    private PodcastInput loadedPodcast;
    private Album loadedAlbum;
    private SongState songState;
    private PodcastState podcastState;
    private PlaylistState playlistState;
    private AlbumState albumState;
    private int loadTimestamp;
    private PodcastStateResumer resumer = new PodcastStateResumer();

    /**
     *
     *
     */
    public AlbumState getAlbumState() {
        return albumState;
    }

    /**
     *
     *
     */
    public void setAlbumState(final AlbumState albumState) {
        this.albumState = albumState;
    }
    /**
     *
     *
     */
    public Album getLoadedAlbum() {
        return loadedAlbum;
    }
    /**
     *
     *
     */
    public void setLoadedAlbum(final Album loadedAlbum) {
        this.loadedAlbum = loadedAlbum;
    }

    /**
     *
     */
    public SongInput getLoadedSong() {
        return loadedSong;
    }
    /**
     *
     */
    public void setLoadedSong(final SongInput loadedSong) {
        this.loadedSong = loadedSong;
    }
    /**
     *
     */
    public Playlist getLoadedPlaylist() {
        return loadedPlaylist;
    }
    /**
     *
     */
    public void setLoadedPlaylist(final Playlist loadedPlaylist) {
        this.loadedPlaylist = loadedPlaylist;
    }
    /**
     *
     */
    public PodcastInput getLoadedPodcast() {
        return loadedPodcast;
    }
    /**
     *
     */
    public void setLoadedPodcast(final PodcastInput loadedPodcast) {
        this.loadedPodcast = loadedPodcast;
    }
    /**
     *
     */
    public int getLoadTimestamp() {
        return loadTimestamp;
    }
    /**
     *
     */
    public void setLoadTimestamp(final int loadTimestamp) {
        this.loadTimestamp = loadTimestamp;
    }
    /**
     *
     */
    public SongState getSongState() {
        return songState;
    }
    /**
     *
     */
    public void setSongState(final SongState songState) {
        this.songState = songState;
    }
    /**
     *
     */
    public PodcastState getPodcastState() {
        return podcastState;
    }
    /**
     *
     */
    public void setPodcastState(final PodcastState podcastState) {
        this.podcastState = podcastState;
    }
    /**
     *
     */
    public PodcastStateResumer getResumer() {
        return resumer;
    }
    /**
     *
     */
    public void setResumer(final PodcastStateResumer resumer) {
        this.resumer = resumer;
    }
    /**
     *
     */
    public PlaylistState getPlaylistState() {
        return playlistState;
    }
    /**
     *
     */
    public void setPlaylistState(final PlaylistState playlistState) {
        this.playlistState = playlistState;
    }
}
