package main.pages.navigation_history;

import main.userspace.user_interface.UserInterface;

import java.util.Stack;

public class NavigationHistoryCaretaker {
    private Stack<PageStateMemento> backHistory = new Stack<>();
    private Stack<PageStateMemento> forwardHistory = new Stack<>();

    public void saveStateMemento(UserInterface ui) {
        backHistory.push(ui.savePageStateMemento());
    }

    public boolean jumpNext(UserInterface ui) {
        if (!forwardHistory.isEmpty()) {
            backHistory.push(ui.savePageStateMemento());
            PageStateMemento memento = forwardHistory.pop();
            ui.restorePageStateMemento(memento);
            return true;
        }
        return false;
    }
    public boolean jumpPrev(UserInterface ui) {
        if (!backHistory.isEmpty()) {
            forwardHistory.push(ui.savePageStateMemento());
            PageStateMemento memento = backHistory.pop();
            ui.restorePageStateMemento(memento);
            return true;
        }
        return false;
    }
    public void clearForwardHistory() {
        forwardHistory.clear();
    }
}
