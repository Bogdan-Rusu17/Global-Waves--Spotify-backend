package main.userspace;

import main.userspace.user_interface.UserInterface;

import java.util.HashMap;

public final class UserSpaceDb {
    private static HashMap<String, UserInterface> database = new HashMap<>();

    private UserSpaceDb() {

    }

    public static HashMap<String, UserInterface> getDatabase() {
        return database;
    }

    public static void setDatabase(final HashMap<String, UserInterface> database) {
        UserSpaceDb.database = database;
    }
}
