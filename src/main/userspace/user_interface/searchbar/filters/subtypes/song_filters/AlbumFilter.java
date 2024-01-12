package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class AlbumFilter extends SongFilter {
    public AlbumFilter(final Filter filter) {
        this.setAlbum(filter.getAlbum());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        return this.getAlbum().equals(((SongInput) song).getAlbum());
    }
}
