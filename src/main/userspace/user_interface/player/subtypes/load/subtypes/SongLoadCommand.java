package main.userspace.user_interface.player.subtypes.load.subtypes;

import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.load.LoadCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.SongState;

public final class SongLoadCommand extends LoadCommand {
    public SongLoadCommand(final Command command) {
        super(command);
    }

    /**
     * sets the loadedSong parameter of the player to the selected song
     * and instances a new, default state for this song
     */
    @Override
    public void execCommand() {
        this.outputBase();
        SongInput selectedSong = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getSearchBar().getSelectedSong();
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
}
