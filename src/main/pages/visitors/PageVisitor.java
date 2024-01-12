package main.pages.visitors;

import main.pages.visitables.ArtistPage;
import main.pages.visitables.HomePage;
import main.pages.visitables.HostPage;
import main.pages.visitables.LikedContentPage;

public interface PageVisitor {
    /**
     *
     * @param page artist page to be worked on
     * @param user owner of the page
     */
    void visit(ArtistPage page, String user);
    /**
     *
     * @param page user homepage page to be worked on
     * @param user owner of the page
     */
    void visit(HomePage page, String user);
    /**
     *
     * @param page host page to be worked on
     * @param user owner of the page
     */
    void visit(HostPage page, String user);
    /**
     *
     * @param page user liked content page to be worked on
     * @param user owner of the page
     */
    void visit(LikedContentPage page, String user);
}
