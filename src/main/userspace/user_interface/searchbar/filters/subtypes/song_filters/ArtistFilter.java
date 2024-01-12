package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class ArtistFilter extends SongFilter {
    public ArtistFilter(final Filter filter) {
        this.setArtist(filter.getArtist());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        return this.getArtist().equals(((SongInput) song).getArtist());
    }
}
