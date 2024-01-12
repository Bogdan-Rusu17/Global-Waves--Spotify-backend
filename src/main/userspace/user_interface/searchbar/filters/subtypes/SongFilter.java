package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;
/**
 * songFilter superclass
 */
public class SongFilter extends Filter {
    /**
     *
     * @param song audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object song) {
        return true;
    }
}
