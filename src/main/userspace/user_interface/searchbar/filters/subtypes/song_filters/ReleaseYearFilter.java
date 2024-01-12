package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class ReleaseYearFilter extends SongFilter {
    public ReleaseYearFilter(final Filter filter) {
        this.setReleaseYear(filter.getReleaseYear());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        if (this.getReleaseYear().charAt(0) == '<') {
            return ((SongInput) song).getReleaseYear() < Integer.parseInt(this
                    .getReleaseYear().substring(1));
        }
        return ((SongInput) song).getReleaseYear() > Integer.parseInt(this
                .getReleaseYear().substring(1));
    }
}
