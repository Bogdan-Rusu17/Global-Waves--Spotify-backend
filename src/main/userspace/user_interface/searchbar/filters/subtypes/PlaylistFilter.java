package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;

/**
 * playlistFilter superclass
 */
public class PlaylistFilter extends Filter {
    /**
     *
     * @param playlist audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object playlist) {
        return true;
    }
}
