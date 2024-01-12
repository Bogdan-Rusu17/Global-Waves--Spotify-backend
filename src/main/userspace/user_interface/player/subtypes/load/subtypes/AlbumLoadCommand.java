package main.userspace.user_interface.player.subtypes.load.subtypes;

import fileio.input.SongInput;
import main.entities.Album;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.load.LoadCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.AlbumState;

import java.util.ArrayList;

public final class AlbumLoadCommand extends LoadCommand {
    public AlbumLoadCommand(final Command command) {
        super(command);
    }

    @Override
    public void execCommand() {
        this.outputBase();
        Album selectedAlbum = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getSelectedAlbum();
        if (selectedAlbum.getSongs().isEmpty()) {
            outputErrorMessage("You can't load an empty audio collection!");
            return;
        }
        this.getObjectNode().put("message", "Playback loaded successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadedAlbum(selectedAlbum);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                .setLoadTimestamp(this.getTimestamp());
        ArrayList<Integer> idxOrder = new ArrayList<>();
        for (int i = 0; i < selectedAlbum.getSongs().size(); i++) {
            idxOrder.add(i);
        }
        SongInput firstSong = selectedAlbum.getSongs().get(0);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer().setAlbumState(
                new AlbumState(firstSong.getName(), firstSong.getDuration(),
                        selectedAlbum, 0, idxOrder));
        UserSpaceDb.getDatabase().get(this.getUsername()).getTop()
                .listenSong(firstSong, 1);
        GlobalObjects.getInstance().existsArtist(firstSong.getArtist())
                .getTop().getListenerSong(firstSong, 1, getUsername());
        this.deleteSelection();
    }
}
