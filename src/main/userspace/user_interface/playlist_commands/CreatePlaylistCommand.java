package main.userspace.user_interface.playlist_commands;

import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class CreatePlaylistCommand extends Command {
    public CreatePlaylistCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setPlaylistName(command.getPlaylistName());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        boolean alreadyExists = false;
        for (Playlist playlist : PlaylistDB.getPlaylists()) {
            if (playlist.getName().equals(this.getPlaylistName())
                    && playlist.getOwner().equals(this.getUsername())) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            this.getObjectNode().put("message", "A playlist with the same name already exists.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Playlist newPlaylist = new Playlist(this.getPlaylistName(), this.getUsername(),
                "public", 0);
        PlaylistDB.addPlaylist(newPlaylist);
        UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList().add(newPlaylist);
        newPlaylist.attach(UserSpaceDb.getDatabase().get(this.getUsername()));
        this.getObjectNode().put("message", "Playlist created successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
