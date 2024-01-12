package main.admin_commands.album_commands;

import fileio.input.SongInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.notification_system.Notification;
import main.userspace.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class AddAlbumCommand extends Command {
    public AddAlbumCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setName(command.getName());
        this.setTimestamp(command.getTimestamp());
        this.setReleaseYear(command.getReleaseYear());
        this.setUsername(command.getUsername());
        this.setDescription(command.getDescription());
        this.setSongs(command.getSongs());
    }


    /**
     *
     * @param songs in an album to be added
     * @return true if there are 2 identical songs
     */
    public boolean hasIdenticalSongs(final ArrayList<SongInput> songs) {
        for (SongInput song1 : songs) {
            for (SongInput song2 : songs) {
                if (song1.getName().equals(song2.getName()) && song1 != song2) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void execCommand() {
        this.outputBase();
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Artist artist = GlobalObjects.getInstance().existsArtist(this.getUsername());
        if (artist == null) {
            this.getObjectNode().put("message", this.getUsername()
                    + " is not an artist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (artist.hasSameAlbum(this.getName())) {
            this.getObjectNode().put("message", this.getUsername()
                    + " has another album with the same name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }

        if (hasIdenticalSongs(this.getSongs())) {
            this.getObjectNode().put("message", this.getUsername()
                    + " has the same song at least twice in this album.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        for (SongInput song : this.getSongs()) {
            GlobalObjects.getInstance().getLibrary().getSongs().add(song);
            LikeDB.addLike(song);
            LikeDB.removeLike(song);
            LikeDB.getPriority().put(song, GlobalObjects.getInstance().getPrio());
            GlobalObjects.getInstance().setPrio(GlobalObjects.getInstance().getPrio() - 1);
        }
        Album newAlbum = new Album(this.getName(), this.getUsername(),
                this.getDescription(), this.getReleaseYear(), this.getSongs());
        artist.getPage().getAlbums().add(newAlbum);
        artist.notifyObservers(new Notification("New Album", "New Album from " + artist.getUsername() + "."));
        GlobalObjects.getInstance().getLibrary().getAlbums().add(newAlbum);
        this.getObjectNode().put("message", this.getUsername()
                + " has added new album successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        GlobalObjects.getInstance().getLibrary().getAlbums().sort(Comparator.comparingInt(a -> a.getArtist().getPrio()));
    }
}
