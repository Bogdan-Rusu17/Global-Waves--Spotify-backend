package main.userspace.user_interface.searchbar.filters.subtypes.song_filters;

import fileio.input.SongInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.SongFilter;

public final class TagsFilter extends SongFilter {
    public TagsFilter(final Filter filter) {
        this.setTags(filter.getTags());
    }

    @Override
    public boolean isMetFilter(final Object song) {
        for (String tag : this.getTags()) {
            if (!((SongInput) song).getTags().contains(tag)) {
                return false;
            }
        }
        return true;
    }
}
