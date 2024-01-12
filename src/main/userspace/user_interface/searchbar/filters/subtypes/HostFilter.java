package main.userspace.user_interface.searchbar.filters.subtypes;

import main.userspace.user_interface.searchbar.filters.Filter;

public class HostFilter extends Filter {
    /**
     *
     * @param host audio entity to be verified against filters
     * @return placeholder
     */
    public boolean isMetFilter(final Object host) {
        return true;
    }
}
