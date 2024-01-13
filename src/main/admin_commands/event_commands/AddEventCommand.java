package main.admin_commands.event_commands;

import main.entities.Artist;
import main.entities.Event;
import main.globals.GlobalObjects;
import main.notification_system.Notification;
import main.userspace.Command;

public final class AddEventCommand extends Command {
    public static final int VALID_LEN = 10;
    public static final int FIRST_DASH = 2;
    public static final int SECOND_DASH = 5;
    public static final int FEBRUARY = 2;
    public static final int JANUARY = 1;
    public static final int LAST_FEB_DAY = 28;
    public static final int LAST_DAY_OF_MONTH = 31;
    public static final int DECEMBER = 12;
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2023;
    public AddEventCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setName(command.getName());
        this.setTimestamp(command.getTimestamp());
        this.setDescription(command.getDescription());
        this.setDate(command.getDate());
        this.setUsername(command.getUsername());
    }

    /**
     *
     * @param artist whose list of events are verified
     * @param name of the event to be added
     * @return false if the artist already has an event with the same name
     */
    public boolean hasSameEvent(final Artist artist, final String name) {
        for (Event event : artist.getPage().getEvents()) {
            if (event.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param dateString to be checked
     * @return true if the dateString is a valid date
     */
    public boolean isValidDate(final String dateString) {
        if (dateString.length() != VALID_LEN) {
            return false;
        }
        if (dateString.charAt(FIRST_DASH) != '-' || dateString.charAt(SECOND_DASH) != '-') {
            return false;
        }
        for (int i = 0; i < VALID_LEN; i++) {
            if (i != FIRST_DASH && i != SECOND_DASH) {
                if (!(dateString.charAt(i) <= '9' && dateString.charAt(i) >= '0')) {
                    return false;
                }
            }
        }
        int day, month, year;
        day = Integer.parseInt(dateString.substring(0, FIRST_DASH));
        month = Integer.parseInt(dateString.substring(FIRST_DASH + 1, SECOND_DASH));
        year = Integer.parseInt(dateString.substring(SECOND_DASH + 1, VALID_LEN));
        if (month == FEBRUARY && day > LAST_FEB_DAY) {
            return false;
        }
        if (month != FEBRUARY && day > LAST_DAY_OF_MONTH) {
            return false;
        }
        if (month < JANUARY || month > DECEMBER) {
            return false;
        }
        if (year > MAX_YEAR || year < MIN_YEAR) {
            return false;
        }
        return true;
    }
    @Override
    public void execCommand() {
        if (!GlobalObjects.getInstance().existsUsername(this.getUsername())) {
            this.outputBase();
            this.getObjectNode().put("message", "The username "
                    + this.getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Artist artist = GlobalObjects.getInstance().existsArtist(this.getUsername());
        if (artist == null) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " is not an artist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (hasSameEvent(artist, getName())) {
            this.outputBase();
            this.getObjectNode().put("message", this.getUsername()
                    + " has another event with the same name.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (!isValidDate(getDate())) {
            this.outputBase();
            this.getObjectNode().put("message", "Event for "
                    + this.getUsername() + " does not have a valid date.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        Event newEvent = new Event(getUsername(), getName(), getDescription(), getDate());
        artist.getPage().getEvents().add(newEvent);
        artist.notifyObservers(new Notification("New Event",
                "New Event from " + artist.getUsername() + "."));
        this.outputBase();
        this.getObjectNode().put("message", this.getUsername()
                + " has added new event successfully.");
        GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
    }
}
