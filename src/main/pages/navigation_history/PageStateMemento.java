package main.pages.navigation_history;

import main.pages.visitables.Page;

public final class PageStateMemento {
    private final Page page;

    /**
     *
     * @param page to save in the snapshot/memento
     */
    public PageStateMemento(final Page page) {
        this.page = page;
    }

    /**
     *
     * @return snapshot/memento page
     */
    public Page getPage() {
        return page;
    }
}
