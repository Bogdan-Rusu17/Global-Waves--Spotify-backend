package main.userspace.user_interface.like_commands;

import fileio.input.SongInput;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class LikeCommand extends Command {
    public LikeCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * adds or removes the like for the loaded song in the database of likes
     * and into each user's own list of liked songs
     */
    @Override
    public void execCommand() {
        this.outputBase();
        String message;
        SongInput loadedSong = UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlayer().getLoadedSong();
        if (UserSpaceDb.getDatabase().get(this.getUsername()).getLikedSongs()
                .contains(loadedSong)) {
            LikeDB.removeLike(loadedSong);
            UserSpaceDb.getDatabase().get(this.getUsername()).getLikedSongs()
                    .remove(loadedSong);
            message = "Unlike registered successfully.";
        } else {
            LikeDB.addLike(loadedSong);
            UserSpaceDb.getDatabase().get(this.getUsername()).getLikedSongs()
                    .add(loadedSong);
            message = "Like registered successfully.";
        }
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
