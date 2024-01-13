package main.userspace.user_interface.searchbar.filters.subtypes.podcast_filters;

import fileio.input.PodcastInput;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.PodcastFilter;

public final class PodcastNameFilter extends PodcastFilter {
    public PodcastNameFilter(final Filter filter) {
        this.setName(filter.getName());
    }
    @Override
    public boolean isMetFilter(final Object podcast) {
        return ((PodcastInput) podcast).getName().toLowerCase()
                .startsWith(this.getName().toLowerCase());
    }
}
