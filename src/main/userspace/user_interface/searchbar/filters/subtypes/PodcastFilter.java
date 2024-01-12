package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;
/**
 * podcastFilter superclass
 */
public class PodcastFilter extends Filter {
    /**
     *
     * @param podcast audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object podcast) {
        return true;
    }
}
