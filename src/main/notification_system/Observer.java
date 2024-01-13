package main.notification_system;

public interface Observer {
    /**
     *
     * @param notification to be processed by concrete observe
     */
    void update(Notification notification);
}
