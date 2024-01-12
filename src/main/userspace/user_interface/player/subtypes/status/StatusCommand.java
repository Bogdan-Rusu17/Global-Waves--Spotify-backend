package main.userspace.user_interface.player.subtypes.status;

import main.userspace.Command;
import main.userspace.user_interface.player.subtypes.state.State;

public class StatusCommand extends Command {
    public StatusCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }

    /**
     * used for error whenever we don't have a loaded source
     */
    @Override
    public void execCommand() {
        State errorState = new State("", 0);
        errorState.setOnFinished();
        errorState.outputState(this);
    }
}
