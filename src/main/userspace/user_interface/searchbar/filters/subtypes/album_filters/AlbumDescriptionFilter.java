package main.userspace.user_interface.searchbar.filters.subtypes.album_filters;

import main.entities.Album;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.AlbumFilter;

public final class AlbumDescriptionFilter extends AlbumFilter {
    public AlbumDescriptionFilter(final Filter filter) {
        this.setDescription(filter.getDescription());
    }

    @Override
    public boolean isMetFilter(final Object album) {
        return ((Album) album).getDescription().startsWith(this.getDescription());
    }
}
