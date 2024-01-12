package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import main.entities.Album;
import main.entities.Artist;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.UserInterface;
import main.userspace.user_interface.player.Player;
import main.userspace.user_interface.searchbar.SearchBar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);
        int i = 0;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
            i++;
            if (i > 3)
                break;
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        ArrayNode outputs = objectMapper.createArrayNode();
        LikeDB.getLikedSongs().clear();
        PlaylistDB.getPlaylists().clear();
        UserSpaceDb.getDatabase().clear();
        GlobalObjects.getInstance().getLastUserCommandTimestamp().clear();
        GlobalObjects.getInstance().setLibrary(library);
        GlobalObjects.getInstance().setOutputs(outputs);
        int i = library.getSongs().size();
        for (SongInput song: library.getSongs()) {
            i--;
            LikeDB.addLike(song);
            LikeDB.removeLike(song);
            LikeDB.getPriority().put(song, i);
        }

        for (UserInput user : library.getUsers()) {
            GlobalObjects.getInstance().getLastUserCommandTimestamp().put(user.getUsername(), 0);
            String username = user.getUsername();
            if (!UserSpaceDb.getDatabase().containsKey(username)) {
                UserInterface newUser = new UserInterface();
                newUser.setSearchBar(new SearchBar());
                newUser.setPlayer(new Player());
                UserSpaceDb.getDatabase().put(username, newUser);
            }
        }

        Command[] commandList = objectMapper.readValue(
                new File(CheckerConstants.TESTS_PATH + filePathInput), Command[].class);
        for (Command command : commandList) {
            command.determineCommand();
            GlobalObjects.getInstance().getLastUserCommandTimestamp().put(
                    command.getUsername(), command.getTimestamp());
        }
        // TODO add your implementation
        ObjectNode res = objectMapper.createObjectNode();
        int idx = 0;
        GlobalObjects.getInstance().getLibrary().getArtists().sort(Comparator.comparing(Artist::getUsername));
        for (Artist artist : GlobalObjects.getInstance().getLibrary().getArtists()) {
            if (!artist.getTop().getTopFans().isEmpty()) {
                ObjectNode artistNode = objectMapper.createObjectNode();
                artistNode.put("merchRevenue", 0.0);
                artistNode.put("songRevenue", 0.0);
                artistNode.put("ranking", idx + 1);
                artistNode.put("mostProfitableSong", "N/A");
                idx++;
                res.put(artist.getUsername(), artistNode);
            }
        }
        ObjectNode end = objectMapper.createObjectNode();
        end.put("command", "endProgram");
        end.put("result", res);
        outputs.add(end);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}