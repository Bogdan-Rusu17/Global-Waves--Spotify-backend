package main.recommendations;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.load.LoadCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;
import main.userspace.user_interface.player.subtypes.state.subtypes.SongState;

import java.util.ArrayList;

public final class LoadRecommendationsCommand extends LoadCommand {
    public LoadRecommendationsCommand(final Command command) {
        super(command);
    }

    /**
     * load the last song from the song recommendations list for the user
     */
    public void loadSong() {
        this.outputBase();
        ArrayList<SongInput> recom = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getRecommendedSongs();
        int idx = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getRecommendedSongs().size() - 1;
        SongInput selectedSong = recom.get(idx);
        this.getObjectNode().put("message", "Playback loaded successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setLoadedSong(selectedSong);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadTimestamp(this.getTimestamp());
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setSongState(new SongState(selectedSong.getName(), selectedSong.getDuration()));
        UserSpaceDb.getDatabase().get(this.getUsername()).getTop()
                .listenSong(selectedSong, 1);
        GlobalObjects.getInstance().existsArtist(selectedSong.getArtist())
                .getTop().getListenerSong(selectedSong, 1, getUsername());
        this.deleteSelection();
    }
    /**
     * load the last playlist from the playlist recommendations list for the user
     */
    public void loadPlaylist() {
        this.outputBase();
        ArrayList<Playlist> recom = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getRecommendedPlaylists();
        int idx = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getRecommendedPlaylists().size() - 1;
        Playlist selectedPlaylist = recom.get(idx - 1);
        if (selectedPlaylist.getSongList().isEmpty()) {
            outputErrorMessage("You can't load an empty audio collection!");
            return;
        }
        this.getObjectNode().put("message", "Playback loaded successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadedPlaylist(selectedPlaylist);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadTimestamp(this.getTimestamp());
        ArrayList<Integer> idxOrder = new ArrayList<>();
        for (int i = 0; i < selectedPlaylist.getSongList().size(); i++) {
            idxOrder.add(i);
        }
        SongInput firstSong = selectedPlaylist.getSongList().get(0);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setPlaylistState(
                new PlaylistState(firstSong.getName(), firstSong.getDuration(),
                        selectedPlaylist, 0, idxOrder));
        UserSpaceDb.getDatabase().get(this.getUsername()).getTop()
                .listenSong(firstSong, 1);
        GlobalObjects.getInstance().existsArtist(firstSong.getArtist())
                .getTop().getListenerSong(firstSong, 1, getUsername());
        this.deleteSelection();
    }

    @Override
    public void execCommand() {
        if (UserSpaceDb.getDatabase().get(getUsername()).getLastRecommendation().equals("song")) {
            loadSong();
        } else {
            loadPlaylist();
        }
    }
}
