package main.entities;

import fileio.input.SongInput;
import main.globals.LikeDB;
import main.pages.visitables.ArtistPage;
import main.wrapped.ArtistTop;

public final class Artist {
    private String username;
    private int age;
    private String city;
    private ArtistPage page;
    private ArtistTop top = new ArtistTop();
    private static int priority = 0;
    private int prio;


    public Artist(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.page = new ArtistPage();
        prio = priority;
        priority++;
    }

    public int getPrio() {
        return prio;
    }

    /**
     *
     * @param name of the queried album
     * @return the album that has the name 'name' or null
     */
    public Album getAlbumByName(final String name) {
        for (Album album : page.getAlbums()) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     *
     * @param name to be checked
     * @return true if there already exists an album with the same name
     */
    public boolean hasSameAlbum(final String name) {
        for (Album album : page.getAlbums()) {
            if (album.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param name of the queried event
     * @return the event that has the name 'name' or null
     */
    public Event getEventByName(final String name) {
        for (Event event : page.getEvents()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    /**
     *
     * @return total likes of all the songs belonging to the artist
     */
    public int computeTotalLikes() {
        int total = 0;
        for (Album album : page.getAlbums()) {
            for (SongInput song : album.getSongs()) {
                total += LikeDB.getLikedSongs().get(song);
            }
        }
        return total;
    }

    public ArtistTop getTop() {
        return top;
    }

    public void setTop(ArtistTop top) {
        this.top = top;
    }

    public ArtistPage getPage() {
        return page;
    }

    public void setPage(final ArtistPage page) {
        this.page = page;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
}
