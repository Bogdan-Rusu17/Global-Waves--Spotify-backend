package main.userspace.user_interface.searchbar.filters.subtypes.host_filters;

import main.entities.Host;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.HostFilter;

public final class HostNameFilter extends HostFilter {
    public HostNameFilter(final Filter filter) {
        this.setName(filter.getName());
    }

    @Override
    public boolean isMetFilter(final Object host) {
        return ((Host) host).getUsername().toLowerCase().startsWith(this.getName().toLowerCase());
    }
}
