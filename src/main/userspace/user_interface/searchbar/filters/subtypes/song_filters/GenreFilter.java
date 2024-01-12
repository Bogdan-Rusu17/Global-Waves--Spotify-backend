package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class GenreFilter extends SongFilter {
    public GenreFilter(final Filter filter) {
        this.setGenre(filter.getGenre());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        return this.getGenre().equalsIgnoreCase(((SongInput) song).getGenre());
    }
}
