package main.pages.navigation_history;

import main.userspace.user_interface.UserInterface;

import java.util.Stack;

public final class NavigationHistoryCaretaker {
    private Stack<PageStateMemento> backHistory = new Stack<>();
    private Stack<PageStateMemento> forwardHistory = new Stack<>();

    /**
     *
     * @param ui the userInterface whose current page snapshot needs to be saved
     */
    public void saveStateMemento(final UserInterface ui) {
        backHistory.push(ui.savePageStateMemento());
    }

    /**
     *
     * @param ui the userInterface whose next page snapshot needs to be restored
     * @return true if there is anything in the forward history
     */
    public boolean jumpNext(final UserInterface ui) {
        if (!forwardHistory.isEmpty()) {
            backHistory.push(ui.savePageStateMemento());
            PageStateMemento memento = forwardHistory.pop();
            ui.restorePageStateMemento(memento);
            return true;
        }
        return false;
    }
    /**
     *
     * @param ui the userInterface whose back page snapshot needs to be restored
     * @return true if there is anything in the back history
     */
    public boolean jumpPrev(final UserInterface ui) {
        if (!backHistory.isEmpty()) {
            forwardHistory.push(ui.savePageStateMemento());
            PageStateMemento memento = backHistory.pop();
            ui.restorePageStateMemento(memento);
            return true;
        }
        return false;
    }

    /**
     * clears forward history
     */
    public void clearForwardHistory() {
        forwardHistory.clear();
    }
}
