package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;

public class AlbumFilter extends Filter {
    /**
     *
     * @param album audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object album) {
        return true;
    }
}
