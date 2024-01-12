package main.pages.visitables;

import main.entities.Album;
import main.entities.Event;
import main.entities.Merch;
import main.pages.visitors.PageVisitor;

import java.util.ArrayList;

public final class ArtistPage implements Page {
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Merch> merchProducts = new ArrayList<>();

    /**
     *
     * @param vis visitor of type builder/printer that makes use of the page
     * @param user the owner of the page
     */
    public void accept(final PageVisitor vis, final String user) {
        vis.visit(this, user);
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(final ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Merch> getMerchProducts() {
        return merchProducts;
    }

    public void setMerchProducts(final ArrayList<Merch> merchProducts) {
        this.merchProducts = merchProducts;
    }
}
