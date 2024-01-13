package main.entities;

import fileio.input.PodcastInput;
import fileio.input.UserInput;
import main.globals.GlobalObjects;
import main.notification_system.Notification;
import main.notification_system.Observer;
import main.notification_system.Subject;
import main.pages.visitables.HostPage;
import main.userspace.UserSpaceDb;
import main.wrapped.HostTop;

import java.util.ArrayList;
import java.util.List;

public final class Host implements Subject {
    private String username;
    private int age;
    private String city;
    private HostPage page;
    private List<Observer> observers = new ArrayList<>();
    private HostTop top = new HostTop();

    public Host(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.page = new HostPage();
    }
    @Override
    public void attach(final Observer obs) {
        observers.add(obs);
    }

    @Override
    public void detach(final Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(final Notification notification) {
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }

    public HostTop getTop() {
        return top;
    }

    public void setTop(final HostTop top) {
        this.top = top;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(final List<Observer> observers) {
        this.observers = observers;
    }

    /**
     *
     * @param name to be queried
     * @return true if a podcast with the same name already exists for that host
     */
    public boolean hasSamePodcast(final String name) {
        for (PodcastInput podcast : page.getPodcasts()) {
            if (podcast.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param name to be queried
     * @return the announcement with the given name
     */
    public Announcement hasSameAnnouncement(final String name) {
        for (Announcement announce : page.getAnnouncements()) {
            if (announce.getName().equals(name)) {
                return announce;
            }
        }
        return null;
    }

    /**
     *
     * @param podcast to be verified not to have conflicts with other users' actions
     * @return true if there are no conflicts
     */
    public boolean cantRemovePodcast(final PodcastInput podcast) {
        for (UserInput user : GlobalObjects.getInstance().getLibrary().getUsers()) {
            if (UserSpaceDb.getDatabase().get(user.getUsername())
                    .getPlayer().getLoadedPodcast() == podcast) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param name to be queried
     * @return the podcast with the given name
     */
    public PodcastInput getPodcastByName(final String name) {
        for (PodcastInput podcast : page.getPodcasts()) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
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

    public HostPage getPage() {
        return page;
    }

    public void setPage(final HostPage page) {
        this.page = page;
    }
}
