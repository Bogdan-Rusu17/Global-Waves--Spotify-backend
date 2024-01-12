package main.userspace.user_interface.searchbar.filters.subtypes.podcast_filters;

import fileio.input.PodcastInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.PodcastFilter;

public final class PodcastOwnerFilter extends PodcastFilter {
    public PodcastOwnerFilter(final Filter filter) {
        this.setOwner(filter.getOwner());
    }

    @Override
    public boolean isMetFilter(final Object podcast) {
        return this.getOwner().equals(((PodcastInput) podcast).getOwner());
    }
}
