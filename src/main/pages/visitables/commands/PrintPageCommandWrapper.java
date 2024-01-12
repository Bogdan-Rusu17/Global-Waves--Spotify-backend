package main.pages.visitables.commands;

import main.pages.visitors.PageBuilderVisitor;
import main.pages.visitors.PagePrinterVisitor;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

public final class PrintPageCommandWrapper extends Command {
    private static final PageBuilderVisitor BUILDER_VISITOR = new PageBuilderVisitor();
    private static final PagePrinterVisitor PRINTER_VISITOR = new PagePrinterVisitor();
    public PrintPageCommandWrapper(final Command command) {
        this.setCommand(command.getCommand());
        this.setTimestamp(command.getTimestamp());
        this.setUsername(command.getUsername());
    }

    @Override
    public void execCommand() {
        UserSpaceDb.getDatabase().get(getUsername()).getUserPage()
                .accept(BUILDER_VISITOR, getUsername());
        UserSpaceDb.getDatabase().get(getUsername()).getUserPage()
                .accept(PRINTER_VISITOR, getUsername());
    }
}
