package main.userspace.user_interface.playlist_commands;

import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class FollowCommand extends Command {
    public FollowCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        outputBase();
        String message;
        Playlist selectedPlaylist = UserSpaceDb.getDatabase().get(this.getUsername())
                .getSearchBar().getSelectedPlaylist();
        if (UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList()
                .contains(selectedPlaylist)) {
            this.getObjectNode().put("message",
                    "You cannot follow or unfollow your own playlist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        if (UserSpaceDb.getDatabase().get(this.getUsername()).getIsFollowingMap()
                .containsKey(selectedPlaylist)) {
            if (UserSpaceDb.getDatabase().get(this.getUsername()).getIsFollowingMap()
                    .get(selectedPlaylist) == 0) {
                UserSpaceDb.getDatabase().get(this.getUsername()).getIsFollowingMap()
                        .put(selectedPlaylist, 1);
                selectedPlaylist.setFollowers(selectedPlaylist.getFollowers() + 1);
                message = "Playlist followed successfully.";
            } else {
                UserSpaceDb.getDatabase().get(this.getUsername()).getIsFollowingMap()
                        .replace(selectedPlaylist, 0);
                selectedPlaylist.setFollowers(selectedPlaylist.getFollowers() - 1);
                message = "Playlist unfollowed successfully.";
            }
        } else {
            UserSpaceDb.getDatabase().get(this.getUsername()).getIsFollowingMap()
                    .put(selectedPlaylist, 1);
            selectedPlaylist.setFollowers(selectedPlaylist.getFollowers() + 1);
            message = "Playlist followed successfully.";
        }
        this.getObjectNode().put("message", message);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());

    }
}
