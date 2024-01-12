package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;

public class ArtistFilter extends Filter {
    /**
     *
     * @param artist audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object artist) {
        return true;
    }
}
