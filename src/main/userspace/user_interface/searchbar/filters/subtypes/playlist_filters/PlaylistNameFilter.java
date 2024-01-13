package main.userspace.user_interface.searchbar.filters.subtypes.playlist_filters;

import main.entities.Playlist;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.filters.subtypes.PlaylistFilter;

public final class PlaylistNameFilter extends PlaylistFilter {
    public PlaylistNameFilter(final Filter filter) {
        this.setName(filter.getName());
    }

    @Override
    public boolean isMetFilter(final Object playlist) {
        return ((Playlist) playlist).getName().toLowerCase()
                .startsWith(this.getName().toLowerCase());
    }
}
