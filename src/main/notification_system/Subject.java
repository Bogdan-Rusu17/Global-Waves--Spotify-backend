package main.notification_system;

public interface Subject {
    void attach(Observer obs);
    void dettach(Observer obs);
    void notifyObservers(Notification notification);
}
