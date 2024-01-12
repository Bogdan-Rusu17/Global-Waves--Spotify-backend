package main.admin_commands.album_commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;

public final class ShowAlbumsCommand extends Command {
    public ShowAlbumsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    /**
     *
     * @param username to be queried
     * @return the artist if there is an artist with given username
     */
    public Artist existsArtist(final String username) {
        Artist exists = null;
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            if (artist.getUsername().equals(username)) {
                exists = artist;
                break;
            }
        }
        return exists;
    }
    @Override
    public void execCommand() {
        this.outputBase();
        ArrayNode resultNode = Command.getObjectMapper().createArrayNode();
        Artist artist = existsArtist(this.getUsername());
        for (Album album : artist.getPage().getAlbums()) {
            ObjectNode albumNode = Command.getObjectMapper().createObjectNode();
            albumNode.put("name", album.getName());
            ArrayNode songsInAlbumNode = Command.getObjectMapper().createArrayNode();
            for (SongInput song : album.getSongs()) {
                songsInAlbumNode.add(song.getName());
            }
            albumNode.put("songs", songsInAlbumNode);
            resultNode.add(albumNode);
        }
        this.getObjectNode().put("result", resultNode);
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
