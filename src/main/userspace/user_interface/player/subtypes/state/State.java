package main.userspace.user_interface.player.subtypes.state;

import com.fasterxml.jackson.databind.node.ObjectNode;
import main.globals.GlobalObjects;
import main.userspace.Command;

public class State {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle;
    private boolean paused;
    private Command command;

    public State(final String name, final int remainedTime) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.repeat = "No Repeat";
        this.paused = false;
        this.shuffle = false;
    }
    /**
     * change current state depending on the type of audio file we have loaded
     */
    public void changeState(final int timestamp, final String username) {

    }
    /**
     * output the current state
     */
    public void outputState(final Command commandInput) {
        commandInput.outputBase();
        ObjectNode stateNode = Command.getObjectMapper().createObjectNode();
        stateNode.put("name", name);
        stateNode.put("remainedTime", remainedTime);
        stateNode.put("repeat", repeat);
        stateNode.put("shuffle", shuffle);
        stateNode.put("paused", paused);
        commandInput.getObjectNode().put("stats", stateNode);
        GlobalObjects.getInstance().getOutputs().add(commandInput.getObjectNode());
    }

    /**
     * set the current state on finished
     */
    public void setOnFinished() {
        this.shuffle = false;
        this.paused = true;
        this.remainedTime = 0;
        this.name = "";
        this.repeat = "No Repeat";
    }

    /**
     *
     */
    public String getName() {
        return name;
    }
    /**
     *
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     *
     */
    public int getRemainedTime() {
        return remainedTime;
    }
    /**
     *
     */
    public void setRemainedTime(final int remainedTime) {
        this.remainedTime = remainedTime;
    }
    /**
     *
     */
    public String getRepeat() {
        return repeat;
    }
    /**
     *
     */
    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }
    /**
     *
     */
    public boolean isShuffle() {
        return shuffle;
    }
    /**
     *
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }
    /**
     *
     */
    public boolean isPaused() {
        return paused;
    }
    /**
     *
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }
    /**
     *
     */
    public Command getCommand() {
        return command;
    }
    /**
     *
     */
    public void setCommand(final Command command) {
        this.command = command;
    }
}
