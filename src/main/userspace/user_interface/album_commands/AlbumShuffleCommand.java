package main.userspace.user_interface.album_commands;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.AlbumState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class AlbumShuffleCommand extends Command {
    public AlbumShuffleCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setSeed(command.getSeed());
    }

    @Override
    public void execCommand() {
        outputBase();
        String message;
        AlbumState currentAlbumState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getAlbumState();
        if (!currentAlbumState.isShuffle()) {
            Collections.shuffle(currentAlbumState.getIdxOrder(), new Random(this.getSeed()));
            message = "Shuffle function activated successfully.";
        } else {
            ArrayList<Integer> idxOrder = new ArrayList<>();
            for (int i = 0; i < UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                    .getLoadedAlbum().getSongs().size(); i++) {
                idxOrder.add(i);
            }
            currentAlbumState.setIdxOrder(idxOrder);
            message = "Shuffle function deactivated successfully.";
        }
        currentAlbumState.setShuffle(!currentAlbumState.isShuffle());
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
