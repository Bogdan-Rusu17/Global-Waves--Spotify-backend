package main.userspace.user_interface.playlist_commands;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.PlaylistState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class ShuffleCommand extends Command {
    public ShuffleCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setSeed(command.getSeed());
    }

    @Override
    public void execCommand() {
        outputBase();
        String message;
        PlaylistState currentPlaylistState = UserSpaceDb.getDatabase()
                .get(this.getUsername()).getPlayer().getPlaylistState();
        if (!currentPlaylistState.isShuffle()) {
            Collections.shuffle(currentPlaylistState.getIdxOrder(), new Random(this.getSeed()));
            message = "Shuffle function activated successfully.";
        } else {
            ArrayList<Integer> idxOrder = new ArrayList<>();
            for (int i = 0; i < UserSpaceDb.getDatabase().get(this.getUsername()).getPlayer()
                    .getLoadedPlaylist().getSongList().size(); i++) {
                idxOrder.add(i);
            }
            currentPlaylistState.setIdxOrder(idxOrder);
            message = "Shuffle function deactivated successfully.";
        }
        currentPlaylistState.setShuffle(!currentPlaylistState.isShuffle());
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
