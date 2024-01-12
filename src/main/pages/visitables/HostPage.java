package main.pages.visitables;

import fileio.input.PodcastInput;
import main.entities.Announcement;
import main.pages.visitors.PageVisitor;

import java.util.ArrayList;

public final class HostPage implements Page {
    private ArrayList<PodcastInput> podcasts = new ArrayList<>();
    private ArrayList<Announcement> announcements = new ArrayList<>();

    /**
     *
     * @param vis visitor of type builder/printer that makes use of the page
     * @param user the owner of the page
     */
    public void accept(final PageVisitor vis, final String user) {
        vis.visit(this, user);
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }
}
