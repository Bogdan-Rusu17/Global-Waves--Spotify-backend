package main.pages.navigation_history;

import main.pages.visitables.Page;

public class PageStateMemento {
    private final Page page;
    public PageStateMemento(Page page) {
        this.page = page;
    }
    public Page getPage() {
        return page;
    }
}
