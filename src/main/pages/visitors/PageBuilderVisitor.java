package main.pages.visitors;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.entities.SongSortingHelper;
import main.pages.visitables.ArtistPage;
import main.pages.visitables.HomePage;
import main.pages.visitables.HostPage;
import main.pages.visitables.LikedContentPage;
import main.userspace.UserSpaceDb;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class PageBuilderVisitor implements PageVisitor {
    /**
     *
     * @param page the artist page
     * @param user the name of the owner
     *             doesn't need to be built as it is static
     */
    public void visit(final ArtistPage page, final String user) {

    }

    /**
     *
     * @param page user's homepage
     * @param user owner of the page
     *             needs to be built as it contains information sorted in a manner that can be
     *             altered by other users
     */
    public void visit(final HomePage page, final String user) {
        page.getLikedSongs().clear();
        page.getFollowedPlaylists().clear();
        for (SongInput song : UserSpaceDb.getDatabase().get(user).getLikedSongs()) {
            SongSortingHelper songHelper = new SongSortingHelper(song);
            page.getLikedSongs().add(songHelper);
        }
        Collections.sort(page.getLikedSongs(), Comparator
                .comparingInt(SongSortingHelper::getLikes).reversed());
        for (Map.Entry<Playlist, Integer> entry : UserSpaceDb.getDatabase()
                .get(user).getIsFollowingMap().entrySet()) {
            entry.getKey().setTotalLikes(entry.getKey().computeTotalLikes());
            page.getFollowedPlaylists().add(entry.getKey());
        }
        Collections.sort(page.getFollowedPlaylists(), Comparator
                .comparingInt(Playlist::getFollowers).reversed());
    }
    /**
     *
     * @param page the host page
     * @param user the name of the owner
     * doesn't need to be built as it is static
     */
    public void visit(final HostPage page, final String user) {

    }
    /**
     *
     * @param page user's liked content page
     * @param user owner of the page
     *             needs to be built as it contains information sorted in a manner that can be
     *             altered by other users
     */
    public void visit(final LikedContentPage page, final String user) {
        page.getLikedSongs().clear();
        page.getFollowedPlaylists().clear();
        page.getLikedSongs().addAll(UserSpaceDb.getDatabase().get(user).getLikedSongs());
        for (Map.Entry<Playlist, Integer> entry : UserSpaceDb.getDatabase()
                .get(user).getIsFollowingMap().entrySet()) {
            if (entry.getValue() == 1) {
                page.getFollowedPlaylists().add(entry.getKey());
            }
        }
    }
}
