package main.userspace.user_interface.playlist_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.SongInput;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class ShowPlaylistsCommand extends Command {
    public ShowPlaylistsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    @Override
    public void execCommand() {
        outputBase();
        ArrayNode resultPlaylists = Command.getObjectMapper().createArrayNode();


        for (Playlist playlist : UserSpaceDb.getDatabase().get(this.getUsername())
                .getPlaylistList()) {
            ObjectNode playlistDescription = Command.getObjectMapper().createObjectNode();
            playlistDescription.put("name", playlist.getName());
            ArrayNode songs = Command.getObjectMapper().createArrayNode();
            for (SongInput song : playlist.getSongList()) {
                songs.add(song.getName());
            }
            playlistDescription.put("songs", songs);
            playlistDescription.put("visibility", playlist.getVisibility());
            playlistDescription.put("followers", playlist.getFollowers());
            resultPlaylists.add(playlistDescription);
        }

        this.getObjectNode().put("result", resultPlaylists);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
