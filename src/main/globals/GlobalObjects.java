package main.globals;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import main.entities.Album;
import main.entities.Artist;
import main.entities.Host;

import java.util.HashMap;

public final class GlobalObjects {
    private int prio = -1;
    private LibraryInput library;
    private ArrayNode outputs;
    private HashMap<String, Integer> lastUserCommandTimestamp = new HashMap<>();
    private String lastUsername;
    private static GlobalObjects instance = null;
    private GlobalObjects() {

    }

    /**
     *
     * @param songName to find whose song it is
     * @return the artist if a song with given name is found for them
     */
    public Artist getArtistBySongName(final String songName) {
        for (Artist artist : library.getArtists()) {
            if (artist.getTop().getRevenuePerSongs().containsKey(songName)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * method for getting the singleton instance of the Global Objects
     * @return the instance of the class
     */
    public static GlobalObjects getInstance() {
        if (instance == null) {
            instance = new GlobalObjects();
        }
        return instance;
    }

    /**
     *
     * @param song to find in which album it belongs
     * @return the album who has given song name in it
     */
    public Album getAlbumWithSong(final SongInput song) {
        for (Album album : library.getAlbums()) {
            if (album.getSongs().contains(song)) {
                return album;
            }
        }
        return null;
    }

    /**
     *
     * @param username to be queried
     * @return true if there exists a user with given username
     */
    public boolean existsUsername(final String username) {
        boolean exists = false;
        for (UserInput user : library.getUsers()) {
            if (user.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        for (Artist artist : library.getArtists()) {
            if (artist.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        for (Host host : library.getHosts()) {
            if (host.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    /**
     *
     * @param username to be queried
     * @return the artist if there exists an artist with given username
     */
    public Artist existsArtist(final String username) {
        Artist exists = null;
        for (Artist artist : library.getArtists()) {
            if (artist.getUsername().equals(username)) {
                exists = artist;
                break;
            }
        }
        return exists;
    }
    /**
     *
     * @param username to be queried
     * @return the host if there exists a host with given username
     */
    public Host existsHost(final String username) {
        Host exists = null;
        for (Host host : library.getHosts()) {
            if (host.getUsername().equals(username)) {
                exists = host;
                break;
            }
        }
        return exists;
    }
    /**
     *
     * @param username to be queried
     * @return the normal user if there exists a normal user with given username
     */
    public UserInput existsNormalUser(final String username) {
        UserInput exists = null;
        for (UserInput user : library.getUsers()) {
            if (user.getUsername().equals(username)) {
                exists = user;
                break;
            }
        }
        return exists;
    }
    /**
     *
     * @param username to be queried
     * @return true if there exists a normal user with given username
     */
    public boolean containsNormalUser(final String username) {
        for (UserInput user : library.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public LibraryInput getLibrary() {
        return library;
    }

    public void setLibrary(final LibraryInput library) {
        GlobalObjects.getInstance().library = library;
    }

    public ArrayNode getOutputs() {
        return outputs;
    }

    public void setOutputs(final ArrayNode outputs) {
        GlobalObjects.getInstance().outputs = outputs;
    }

    public HashMap<String, Integer> getLastUserCommandTimestamp() {
        return lastUserCommandTimestamp;
    }

    public void setLastUserCommandTimestamp(final HashMap<String,
            Integer> lastUserCommandTimestamp) {
        GlobalObjects.getInstance().lastUserCommandTimestamp = lastUserCommandTimestamp;
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public void setLastUsername(final String lastUsername) {
        GlobalObjects.getInstance().lastUsername = lastUsername;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(final int prio) {
        GlobalObjects.getInstance().prio = prio;
    }
}
