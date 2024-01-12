package main.pages.visitables;

import main.pages.visitors.PageVisitor;

public interface Page {
    /**
     *
     * @param vis visitor of type builder/printer that makes use of the page
     * @param user the owner of the page
     */
     void accept(PageVisitor vis, String user);
}
