package main.entities;

public final class Event {
    private String username;
    private String name;
    private String description;
    private String date;

    public Event(final String username, final String name,
                 final String description, final String date) {
        this.username = username;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
