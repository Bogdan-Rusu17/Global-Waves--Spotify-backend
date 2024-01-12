package main.userspace.user_interface.searchbar.filters.subtypes.album_filters;

import main.entities.Album;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.AlbumFilter;

public final class AlbumNameFilter extends AlbumFilter {
    public AlbumNameFilter(final Filter filter) {
        this.setName(filter.getName());
    }

    @Override
    public boolean isMetFilter(final Object album) {
        return ((Album) album).getName().toLowerCase().startsWith(this.getName().toLowerCase());
    }
}
