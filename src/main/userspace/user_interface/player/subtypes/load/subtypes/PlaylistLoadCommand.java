package main.userspace.user_interface.player.subtypes.load.subtypes;

import fileio.input.SongInput;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.load.LoadCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

import java.util.ArrayList;

public final class PlaylistLoadCommand extends LoadCommand {
    public PlaylistLoadCommand(final Command command) {
        super(command);
    }

    /**
     * sets the loadedPlaylist parameter of the player to the selected playlist
     * instances a new, default state for a playlist
     * and sets a default shuffle (ordered indexes)
     */
    @Override
    public void execCommand() {
        this.outputBase();
        Playlist selectedPlaylist = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getSelectedPlaylist();
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
}
