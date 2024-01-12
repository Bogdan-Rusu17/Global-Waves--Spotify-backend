package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class NameFilter extends SongFilter {
    public NameFilter(final Filter filter) {
        this.setName(filter.getName());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        return ((SongInput) song).getName().toLowerCase().startsWith(this.getName().toLowerCase());
    }
}
