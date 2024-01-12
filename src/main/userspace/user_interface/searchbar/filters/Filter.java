package main.userspace.user_interface.searchbar.filters;

import main.userspace.user_interface.searchbar.filters.subtypes.album_filters.AlbumDescriptionFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.album_filters.AlbumNameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.album_filters.AlbumOwnerFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.artist_filters.ArtistNameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.host_filters.HostNameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.playlist_filters.PlaylistNameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.playlist_filters.PlaylistOwnerFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.podcast_filters.PodcastNameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.podcast_filters.PodcastOwnerFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.ReleaseYearFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.TagsFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.LyricsFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.NameFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.AlbumFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.GenreFilter;
import main.userspace.user_interface.searchbar.filters.subtypes.song_filters.ArtistFilter;

import java.util.ArrayList;

/**
 * class used for reading the input
 * if the fields are not null, we make instances of the corresponding filters
 */
public class Filter {
    private String album;
    private String artist;
    private String genre;
    private String lyrics;
    private String name;
    private String releaseYear;
    private String description;
    private ArrayList<String> tags;
    private String owner;
    private ArrayList<Filter> subFilters = new ArrayList<>();

    public Filter() {

    }

    /**
     * add to the list of song filters the filters that are present
     */
    public void initSubFiltersSongs() {
        if (album != null) {
            subFilters.add(new AlbumFilter(this));
        }
        if (artist != null) {
            subFilters.add(new ArtistFilter(this));
        }
        if (genre != null) {
            subFilters.add(new GenreFilter(this));
        }
        if (name != null) {
            subFilters.add(new NameFilter(this));
        }
        if (lyrics != null) {
            subFilters.add(new LyricsFilter(this));
        }
        if (tags != null) {
            subFilters.add(new TagsFilter(this));
        }
        if (releaseYear != null) {
            subFilters.add(new ReleaseYearFilter(this));
        }
    }
    /**
     * add to the list of podcast filters the filters that are present
     */
    public void initSubFiltersPodcast() {
        if (name != null) {
            subFilters.add(new PodcastNameFilter(this));
        }
        if (owner != null) {
            subFilters.add(new PodcastOwnerFilter(this));
        }
    }

    /**
     * add to the list of playlist filters the filters that are present
     */
    public void initSubFiltersPlaylist() {
        if (name != null) {
            subFilters.add(new PlaylistNameFilter(this));
        }
        if (owner != null) {
            subFilters.add(new PlaylistOwnerFilter(this));
        }
    }
    /**
     * add to the list of artist filters the filters that are present
     */
    public void initSubFiltersArtist() {
        if (name != null) {
            subFilters.add(new ArtistNameFilter(this));
        }
    }
    /**
     * add to the list of album filters the filters that are present
     */
    public void initSubFiltersAlbum() {
        if (name != null) {
            subFilters.add(new AlbumNameFilter(this));
        }
        if (owner != null) {
            subFilters.add(new AlbumOwnerFilter(this));
        }
        if (description != null) {
            subFilters.add(new AlbumDescriptionFilter(this));
        }
    }
    /**
     * add to the list of host filters the filters that are present
     */
    public void initSubFiltersHost() {
        if (name != null) {
            subFilters.add(new HostNameFilter(this));
        }
    }
    /**
     *
     * @param obj audio entity to be verified against filters
     * @return is placeholder
     */
    public boolean isMetFilter(final Object obj) {
        return false;
    }
    /**
     *
     */
    public String getDescription() {
        return description;
    }
    /**
     *
     */
    public void setDescription(final String description) {
        this.description = description;
    }
    /**
     *
     */
    public ArrayList<Filter> getSubFilters() {
        return subFilters;
    }
    /**
     *
     */
    public void setSubFilters(final ArrayList<Filter> subFilters) {
        this.subFilters = subFilters;
    }
    /**
     *
     */
    public String getAlbum() {
        return album;
    }
    /**
     *
     */
    public void setAlbum(final String album) {
        this.album = album;
    }
    /**
     *
     */
    public String getArtist() {
        return artist;
    }
    /**
     *
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }
    /**
     *
     */
    public String getGenre() {
        return genre;
    }
    /**
     *
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }
    /**
     *
     */
    public String getLyrics() {
        return lyrics;
    }
    /**
     *
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }
    /**
     *
     */
    public String getName() {
        return name;
    }
    /**
     *
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     *
     */
    public String getReleaseYear() {
        return releaseYear;
    }
    /**
     *
     */
    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }
    /**
     *
     */
    public ArrayList<String> getTags() {
        return tags;
    }
    /**
     *
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }
    /**
     *
     */
    public String getOwner() {
        return owner;
    }
    /**
     *
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
