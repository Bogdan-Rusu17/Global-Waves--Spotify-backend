package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class LyricsFilter extends SongFilter {
    public LyricsFilter(final Filter filter) {
        this.setLyrics(filter.getLyrics());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        return ((SongInput) song).getLyrics().toLowerCase().contains(this
                .getLyrics().toLowerCase());
    }
}
