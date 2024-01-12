package main.userspace.user_interface.searchbar.filters.subtypes.playlist_filters;

import main.entities.Playlist;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.PlaylistFilter;

public final class PlaylistOwnerFilter extends PlaylistFilter {
    public PlaylistOwnerFilter(final Filter filter) {
        this.setOwner(filter.getOwner());
    }

    @Override
    public boolean isMetFilter(final Object playlist) {
        return ((Playlist) playlist).getOwner().equals(this.getOwner());
    }
}
