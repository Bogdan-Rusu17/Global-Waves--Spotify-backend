package main.userspace.user_interface.searchbar.filters.subtypes.artist_filters;

import main.entities.Artist;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.ArtistFilter;

public final class ArtistNameFilter extends ArtistFilter {
    public ArtistNameFilter(final Filter filter) {
        this.setName(filter.getName());
    }

    @Override
    public boolean isMetFilter(final Object artist) {
        return ((Artist) artist).getUsername().toLowerCase()
                .startsWith(this.getName().toLowerCase());
    }
}
