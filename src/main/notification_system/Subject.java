package main.notification_system;

public interface Subject {
    /**
     *
     * @param obs added to the list of subscribers for subject
     */
    void attach(Observer obs);
    /**
     *
     * @param obs removed to the list of subscribers for subject
     */
    void detach(Observer obs);

    /**
     *
     * @param notification to be sent to the list of subscribers to subject
     */
    void notifyObservers(Notification notification);
}
