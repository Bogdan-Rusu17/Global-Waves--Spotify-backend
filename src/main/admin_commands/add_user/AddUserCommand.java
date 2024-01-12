package main.admin_commands.add_user;

import main.userspace.Command;

public class AddUserCommand extends Command {
    public AddUserCommand(final     Command command) {
        this.setUsername(command.getUsername());
        this.setAge(command.getAge());
        this.setCity(command.getCity());
        this.setTimestamp(command.getTimestamp());
        this.setCommand(command.getCommand());
    }
}
