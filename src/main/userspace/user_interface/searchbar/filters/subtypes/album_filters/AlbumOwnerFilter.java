package main.userspace.user_interface.searchbar.filters.subtypes.album_filters;

import main.entities.Album;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.AlbumFilter;

public final class AlbumOwnerFilter extends AlbumFilter {
    public AlbumOwnerFilter(final Filter filter) {
        this.setOwner(filter.getOwner());
    }

    @Override
    public boolean isMetFilter(final Object album) {
        return ((Album) album).getOwner().equals(getOwner());
    }
}
