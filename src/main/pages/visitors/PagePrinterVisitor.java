package main.pages.visitors;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import main.entities.*;
import main.globals.GlobalObjects;
import main.pages.visitables.ArtistPage;
import main.pages.visitables.HomePage;
import main.pages.visitables.HostPage;
import main.pages.visitables.LikedContentPage;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public class PagePrinterVisitor implements PageVisitor {
    private static final int MAX_RESULTS = 4;
    /**
     *
     * @param page artist page to be displayed in string form
     * @param user owner of the page
     */
    public void visit(final ArtistPage page, final String user) {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("Albums:\n\t[");
        int cnt = 0;
        for (Album album : page.getAlbums()) {
            cnt++;
            toPrint.append(album.getName()).append(", ");
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        cnt = 0;
        toPrint.append("]\n\nMerch:\n\t[");
        for (Merch merch : page.getMerchProducts()) {
            cnt++;
            toPrint.append(merch.getName()).append(" - ").append(merch.getPrice())
                    .append(":\n\t").append(merch.getDescription()).append(", ");
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        cnt = 0;
        toPrint.append("]\n\nEvents:\n\t[");
        for (Event event : page.getEvents()) {
            cnt++;
            toPrint.append(event.getName()).append(" - ").append(event.getDate())
                    .append(":\n\t").append(event.getDescription()).append(", ");
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]");
        Command.getLastCommand().outputBase();
        Command.getLastCommand().getObjectNode().put("message", toPrint.toString());
        GlobalObjects.getInstance().getOutputs().add(Command.getLastCommand().getObjectNode());
    }
    /**
     *
     * @param page user home page to be displayed in string form
     * @param user owner of the page
     */
    public void visit(final HomePage page, final String user) {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("Liked songs:\n\t[");
        int cnt = 0;
        for (SongSortingHelper song : page.getLikedSongs()) {
            cnt++;
            toPrint.append(song.getSong().getName()).append(", ");
            if (page.getLikedSongs().indexOf(song) >= MAX_RESULTS) {
                break;
            }
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        cnt = 0;
        toPrint.append("]\n\nFollowed playlists:\n\t[");
        for (Playlist playlist : page.getFollowedPlaylists()) {
            cnt++;
            toPrint.append(playlist.getName()).append(", ");
            if (page.getFollowedPlaylists().indexOf(playlist) >= MAX_RESULTS) {
                break;
            }
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]\n\nSong recommendations:\n\t[");
        cnt = 0;
        for (SongInput song : UserSpaceDb.getDatabase().get(user).getRecommendedSongs()) {
            cnt++;
            toPrint.append(song.getName()).append(", ");
            if (UserSpaceDb.getDatabase().get(user)
                    .getRecommendedSongs().indexOf(song) >= MAX_RESULTS) {
                break;
            }
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        cnt = 0;
        toPrint.append("]\n\nPlaylists recommendations:\n\t[");
        for (Playlist playlist : UserSpaceDb.getDatabase().get(user).getRecommendedPlaylists()) {
            cnt++;
            toPrint.append(playlist.getName()).append(", ");
            if (UserSpaceDb.getDatabase().get(user)
                    .getRecommendedPlaylists().indexOf(playlist) >= MAX_RESULTS) {
                break;
            }
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]");
        Command.getLastCommand().outputBase();
        Command.getLastCommand().getObjectNode().put("message", toPrint.toString());
        GlobalObjects.getInstance().getOutputs().add(Command.getLastCommand().getObjectNode());
    }
    /**
     *
     * @param page host page to be displayed in string form
     * @param user owner of the page
     */
    public void visit(final HostPage page, final String user) {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("Podcasts:\n\t[");
        int cntPod = 0;
        for (PodcastInput podcast : page.getPodcasts()) {
            cntPod++;
            toPrint.append(podcast.getName()).append(":\n\t[");
            int cntEp = 0;
            for (EpisodeInput episode : podcast.getEpisodes()) {
                cntEp++;
                toPrint.append(episode.getName()).append(" - ")
                        .append(episode.getDescription()).append(", ");
            }
            if (cntEp > 0) {
                toPrint.deleteCharAt(toPrint.length() - 1);
                toPrint.deleteCharAt(toPrint.length() - 1);
            }
            toPrint.append("]\n, ");
        }
        if (cntPod > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]\n\nAnnouncements:\n\t[");
        int cntAn = 0;
        for (Announcement announce : page.getAnnouncements()) {
            cntAn++;
            toPrint.append(announce.getName()).append(":\n\t")
                    .append(announce.getDescription()).append("\n\t");
        }
        if (cntAn > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]");
        Command.getLastCommand().outputBase();
        Command.getLastCommand().getObjectNode().put("message", toPrint.toString());
        GlobalObjects.getInstance().getOutputs().add(Command.getLastCommand().getObjectNode());
    }
    /**
     *
     * @param page user liked content page to be displayed in string form
     * @param user owner of the page
     */
    public void visit(final LikedContentPage page, final String user) {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("Liked songs:\n\t[");
        int cnt = 0;
        for (SongInput song : page.getLikedSongs()) {
            cnt++;
            toPrint.append(song.getName()).append(" - ")
                    .append(song.getArtist()).append(", ");

        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        cnt = 0;
        toPrint.append("]\n\nFollowed playlists:\n\t[");
        for (Playlist playlist : page.getFollowedPlaylists()) {
            cnt++;
            toPrint.append(playlist.getName()).append(" - ")
                    .append(playlist.getOwner()).append(", ");
        }
        if (cnt > 0) {
            toPrint.deleteCharAt(toPrint.length() - 1);
            toPrint.deleteCharAt(toPrint.length() - 1);
        }
        toPrint.append("]");
        Command.getLastCommand().outputBase();
        Command.getLastCommand().getObjectNode().put("message", toPrint.toString());
        GlobalObjects.getInstance().getOutputs().add(Command.getLastCommand().getObjectNode());
    }
}
