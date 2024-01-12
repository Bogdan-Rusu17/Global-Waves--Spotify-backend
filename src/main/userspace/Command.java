package main.userspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import main.admin_commands.album_commands.*;
import main.admin_commands.announcement_commands.AddAnnouncementCommand;
import main.admin_commands.announcement_commands.RemoveAnnouncementCommand;
import main.admin_commands.podcast_commands.RemovePodcastCommand;
import main.admin_commands.podcast_commands.ShowPodcastsCommand;
import main.admin_commands.add_merch.AddMerchCommand;
import main.admin_commands.podcast_commands.AddPodcastCommand;
import main.admin_commands.add_user.subtypes.AddArtistUserCommand;
import main.admin_commands.add_user.subtypes.AddHostUserCommand;
import main.admin_commands.add_user.subtypes.AddNormalUserCommand;
import main.admin_commands.all_users.GetAllUsersCommand;
import main.admin_commands.delete_user.DeleteUserCommand;
import main.admin_commands.event_commands.AddEventCommand;
import main.admin_commands.event_commands.RemoveEventCommand;
import main.globals.GlobalObjects;
import main.pages.visitables.commands.ChangePageCommand;
import main.pages.visitables.commands.PrintPageCommandWrapper;
import main.userspace.user_interface.album_commands.AlbumShuffleCommand;
import main.userspace.user_interface.jump_commands.backward.BackwardCommand;
import main.userspace.user_interface.jump_commands.forward.ForwardCommand;
import main.userspace.user_interface.jump_commands.next.subtypes.AlbumNextCommand;
import main.userspace.user_interface.jump_commands.next.subtypes.PlaylistNextCommand;
import main.userspace.user_interface.jump_commands.next.subtypes.PodcastNextCommand;
import main.userspace.user_interface.jump_commands.prev.subtypes.AlbumPrevCommand;
import main.userspace.user_interface.jump_commands.prev.subtypes.PlaylistPrevCommand;
import main.userspace.user_interface.jump_commands.prev.subtypes.PodcastPrevCommand;
import main.userspace.user_interface.like_commands.*;
import main.userspace.user_interface.modify_state_cmds.repeat.subtypes.AlbumRepeatCommand;
import main.userspace.user_interface.modify_state_cmds.repeat.subtypes.PlaylistRepeatCommand;
import main.userspace.user_interface.modify_state_cmds.repeat.subtypes.PodcastRepeatCommand;
import main.userspace.user_interface.modify_state_cmds.repeat.subtypes.SongRepeatCommand;
import main.userspace.user_interface.player.subtypes.load.subtypes.AlbumLoadCommand;
import main.userspace.user_interface.player.subtypes.load.subtypes.PlaylistLoadCommand;
import main.userspace.user_interface.player.subtypes.load.subtypes.PodcastLoadCommand;
import main.userspace.user_interface.player.subtypes.load.subtypes.SongLoadCommand;
import main.userspace.user_interface.player.subtypes.playpause.subtypes.AlbumPlayPauseCommand;
import main.userspace.user_interface.player.subtypes.playpause.subtypes.PlaylistPlayPauseCommand;
import main.userspace.user_interface.player.subtypes.playpause.subtypes.PodcastPlayPauseCommand;
import main.userspace.user_interface.player.subtypes.playpause.subtypes.SongPlayPauseCommand;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;
import main.userspace.user_interface.player.subtypes.status.StatusCommand;
import main.userspace.user_interface.player.subtypes.status.subtypes.AlbumStatusCommand;
import main.userspace.user_interface.player.subtypes.status.subtypes.PlaylistStatusCommand;
import main.userspace.user_interface.player.subtypes.status.subtypes.PodcastStatusCommand;
import main.userspace.user_interface.player.subtypes.status.subtypes.SongStatusCommand;
import main.userspace.user_interface.playlist_commands.*;
import main.userspace.user_interface.searchbar.filters.Filter;
import main.userspace.user_interface.searchbar.subtypes.search.subtypes.*;
import main.userspace.user_interface.searchbar.subtypes.select.subtypes.*;
import main.userspace.user_interface.statistics_commands.GetOnlineUsersCommand;
import main.wrapped.subtypes.ArtistWrappedCommand;
import main.wrapped.subtypes.NormalUserWrappedCommand;

import java.util.ArrayList;

public class Command {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Filter filters;
    private int itemNumber;
    private String playlistName;
    private int playlistId;
    private int seed;
    private int age;
    private String city;
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<SongInput> songs;
    private String date;
    private int price;
    private ArrayList<EpisodeInput> episodes;
    private String nextPage;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode objectNode = objectMapper.createObjectNode();
    private static int lastTimestamp = 0;
    private static Command lastCommand;

    public Command() {

    }

    public static int getLastTimestamp() {
        return lastTimestamp;
    }

    /**
     * method that determines the type of the command read from input,
     * creates a new instance of the specific command class
     * and executes it
     */
    public void determineCommand() {

        GlobalObjects.getInstance().setLastUsername(username);
        Command commandToExec = null;
        if (timestamp - lastTimestamp > 0) {
            for (UserInput normalUser : GlobalObjects.getInstance().getLibrary().getUsers()) {
                String normalUserName = normalUser.getUsername();
                if (UserSpaceDb.getDatabase().containsKey(normalUserName)
                        && UserSpaceDb.getDatabase().get(normalUserName).getConnectionStat()) {
                    if (UserSpaceDb.getDatabase().get(normalUserName)
                            .getPlayer().getLoadedSong() != null) {
                        UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                                .getSongState().changeState(
                                timestamp
                                        - lastTimestamp, normalUserName);
                    } else if (UserSpaceDb.getDatabase().get(normalUserName)
                            .getPlayer().getLoadedPodcast() != null) {
                        UserSpaceDb.getDatabase().get(normalUserName).getPlayer().
                                getPodcastState().changeState(
                                timestamp
                                        - lastTimestamp, normalUserName);
                        PodcastState newState =
                                UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                                        .getPodcastState();
                        UserSpaceDb.getDatabase().get(normalUserName).getPlayer().getResumer()
                                .getResumeMap().replace(
                                UserSpaceDb.getDatabase().get(normalUserName)
                                        .getPlayer().getLoadedPodcast(), newState);
                    } else if (UserSpaceDb.getDatabase().get(normalUserName)
                            .getPlayer().getLoadedPlaylist() != null) {
                        UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                                .getPlaylistState().changeState(
                                timestamp - lastTimestamp, normalUserName);
                    } else if (UserSpaceDb.getDatabase().get(normalUserName)
                            .getPlayer().getLoadedAlbum() != null) {
                        UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                                .getAlbumState().changeState(
                                timestamp - lastTimestamp, normalUserName);
                    }
                }
            }
        }

        lastTimestamp = timestamp;

        if (this.command.equals("switchConnectionStatus")) {
            outputBase();
            if (!GlobalObjects.getInstance().existsUsername(username)) {
                outputErrorMessage("The username " + username + " doesn't exist.");
                return;
            }
            if (!GlobalObjects.getInstance().containsNormalUser(username)) {
                outputErrorMessage(username + " is not a normal user.");
                return;
            }
            outputErrorMessage(username + " has changed status successfully.");
            UserSpaceDb.getDatabase().get(username)
                    .setConnectionStat(!UserSpaceDb.getDatabase()
                            .get(username).getConnectionStat());
            return;
        }


        switch (this.command) {
            case "wrapped" -> {
                if (GlobalObjects.getInstance().containsNormalUser(this.getUsername())) {
                    commandToExec = new NormalUserWrappedCommand(this);
                } else {
                    commandToExec = new ArtistWrappedCommand(this);
                }
            }
            case "getTop5Artists" -> {
                commandToExec = new GetTop5ArtistsCommand(this);
            }
            case "getTop5Albums" -> {
                commandToExec = new GetTop5AlbumsCommand(this);
            }
            case "removeEvent" -> {
                commandToExec = new RemoveEventCommand(this);
            }
            case "removePodcast" -> {
                commandToExec = new RemovePodcastCommand(this);
            }
            case "changePage" -> {
                commandToExec = new ChangePageCommand(this);
            }
            case "removeAlbum" -> {
                commandToExec = new RemoveAlbumCommand(this);
            }
            case "showPodcasts" -> {
                commandToExec = new ShowPodcastsCommand(this);
            }
            case "removeAnnouncement" -> {
                commandToExec = new RemoveAnnouncementCommand(this);
            }
            case "addAnnouncement" -> {
                commandToExec = new AddAnnouncementCommand(this);
            }
            case "addPodcast" -> {
                commandToExec = new AddPodcastCommand(this);
            }
            case "deleteUser" -> {
                commandToExec = new DeleteUserCommand(this);
            }
            case "getAllUsers" -> {
                commandToExec = new GetAllUsersCommand(this);
            }
            case "addMerch" -> {
                commandToExec = new AddMerchCommand(this);
            }
            case "addEvent" -> {
                commandToExec = new AddEventCommand(this);
            }
            case "showAlbums" -> {
                commandToExec = new ShowAlbumsCommand(this);
            }
            case "printCurrentPage" -> {
                if (!UserSpaceDb.getDatabase().get(username).getConnectionStat()) {
                    outputBase();
                    objectNode.put("message", username + " is offline.");
                    GlobalObjects.getInstance().getOutputs().add(objectNode);
                    return;
                }
                commandToExec = new PrintPageCommandWrapper(this);
            }
            case "addAlbum" -> {
                commandToExec = new AddAlbumCommand(this);
            }
            case "addUser" -> {
                if (type.equals("artist")) {
                    commandToExec = new AddArtistUserCommand(this);
                } else if (type.equals("user")) {
                    commandToExec = new AddNormalUserCommand(this);
                } else if (type.equals("host")) {
                    commandToExec = new AddHostUserCommand(this);
                }
            }
            case "getOnlineUsers" -> {
                commandToExec = new GetOnlineUsersCommand(this);
            }
            case "search" -> {
                if (!UserSpaceDb.getDatabase().get(username).getConnectionStat()) {
                    outputBase();
                    ArrayNode result = objectMapper.createArrayNode();
                    objectNode.put("message", username + " is offline.");
                    objectNode.put("results", result);
                    GlobalObjects.getInstance().getOutputs().add(objectNode);
                    return;
                }
                commandToExec = switch (this.type) {
                    case "song" -> new SongSearchCommand(this);
                    case "podcast" -> new PodcastSearchCommand(this);
                    case "playlist" -> new PlaylistSearchCommand(this);
                    case "artist" -> new ArtistSearchCommand(this);
                    case "album" -> new AlbumSearchCommand(this);
                    case "host" -> new HostSearchCommand(this);
                    default -> commandToExec;
                };
            }
            case "select" -> {
                if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSongResults() != null) {
                    commandToExec = new SongSelectCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getPodcastResults() != null) {
                    commandToExec = new PodcastSelectCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getPlaylistResults() != null) {
                    commandToExec = new PlaylistSelectCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getArtistResults() != null) {
                    commandToExec = new ArtistSelectCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getAlbumResults() != null) {
                    commandToExec = new AlbumSelectCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getHostResults() != null) {
                    commandToExec = new HostSelectCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please conduct a search before making a selection.");
                    return;
                }
            }
            case "load" -> {
                if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedSong() != null) {
                    commandToExec = new SongLoadCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedPodcast() != null) {
                    commandToExec = new PodcastLoadCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedPlaylist() != null) {
                    commandToExec = new PlaylistLoadCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedAlbum() != null) {
                    commandToExec = new AlbumLoadCommand(this);
                }  else {
                    outputBase();
                    outputErrorMessage("Please select a source before attempting to load.");
                    return;
                }
            }
            case "status" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    commandToExec = new SongStatusCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new PodcastStatusCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new PlaylistStatusCommand(this);
                }  else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumStatusCommand(this);
                } else {
                    commandToExec = new StatusCommand(this);
                }
            }
            case "playPause" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    commandToExec = new SongPlayPauseCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new PodcastPlayPauseCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new PlaylistPlayPauseCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumPlayPauseCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before attempting to "
                            + "pause or resume playback.");
                    return;
                }
            }
            case "createPlaylist" -> {
                commandToExec = new CreatePlaylistCommand(this);
            }
            case "addRemoveInPlaylist" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    commandToExec = new AddRemoveInPlaylistCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a song.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a song.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumAddRemoveInPlaylist(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before adding to or "
                            + "removing from the playlist.");
                    return;
                }
            }
            case "like" -> {
                if (!UserSpaceDb.getDatabase().get(username).getConnectionStat()) {
                    outputBase();
                    objectNode.put("message", username + " is offline.");
                    GlobalObjects.getInstance().getOutputs().add(objectNode);
                    return;
                }
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    commandToExec = new LikeCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    outputBase();
                    outputErrorMessage("Loaded source is not a song.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new LikeFromPlaylistCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new LikeFromAlbumCommand(this);
                }  else {
                    outputBase();
                    outputErrorMessage("Please load a source before liking or unliking.");
                    return;
                }
            }
            case "showPlaylists" -> {
                commandToExec = new ShowPlaylistsCommand(this);
            }
            case "showPreferredSongs" -> {
                commandToExec = new ShowPreferredSongsCommand(this);
            }
            case "repeat" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    commandToExec = new SongRepeatCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new PodcastRepeatCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new PlaylistRepeatCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumRepeatCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before setting "
                            + "the repeat status.");
                    return;
                }
            }
            case "shuffle" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a playlist or an album.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a playlist or an album.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new ShuffleCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumShuffleCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before using the shuffle function.");
                    return;
                }
            }
            case "prev" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new PlaylistPrevCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new PodcastPrevCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumPrevCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before returning"
                            + " to the previous track.");
                    return;
                }
            }
            case "next" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    commandToExec = new PlaylistNextCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new PodcastNextCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    commandToExec = new AlbumNextCommand(this);
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before skipping to the next track.");
                    return;
                }
            }
            case "backward" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new BackwardCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else {
                    outputBase();
                    outputErrorMessage("Please select a source before rewinding.");
                    return;
                }
            }
            case "forward" -> {
                if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPodcast() != null) {
                    commandToExec = new ForwardCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedPlaylist() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedSong() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getPlayer()
                        .getLoadedAlbum() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a podcast.");
                    return;
                } else {
                    outputBase();
                    outputErrorMessage("Please load a source before attempting to forward.");
                    return;
                }
            }
            case "switchVisibility" -> {
                commandToExec = new SwitchVisibilityCommand(this);
            }
            case "follow" -> {
                if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedPlaylist() != null) {
                    commandToExec = new FollowCommand(this);
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedPodcast() != null) {
                    outputBase();
                    outputErrorMessage("The selected source is not a playlist.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedSong() != null) {
                    outputBase();
                    outputErrorMessage("The selected source is not a playlist.");
                    return;
                } else if (UserSpaceDb.getDatabase().get(username).getSearchBar()
                        .getSelectedAlbum() != null) {
                    outputBase();
                    outputErrorMessage("The loaded source is not a playlist.");
                    return;
                } else {
                    outputBase();
                    outputErrorMessage("Please select a source before following "
                            + "or unfollowing.");
                    return;
                }
            }
            case "getTop5Songs" -> {
                commandToExec = new GetTop5SongsCommand(this);
            }
            case "getTop5Playlists" -> {
                commandToExec = new GetTop5PlaylistsCommand(this);
            }
            case "switchConnectionStatus" -> {

            }
            default -> {
                commandToExec = null;
            }
        }
        lastCommand = commandToExec;
        if (commandToExec != null) {
            commandToExec.initCommand();
            commandToExec.execCommand();
        }

    }

    /**
     * perform initializing instructions depending on the type of command
     */
    public void initCommand() {

    }

    /**
     * execute the current type of command
     */
    public void execCommand() {

    }


    /**
     * output basic header for each output command
     */
    public void outputBase() {
        objectNode.put("command", command);
        objectNode.put("user", this.getUsername());
        objectNode.put("timestamp", this.getTimestamp());
    }

    /**
     * output error message
     */
    public void outputErrorMessage(final String message) {
        objectNode.put("message", message);
        GlobalObjects.getInstance().getOutputs().add(objectNode);
    }
    /**
     *
     */
    public String getCity() {
        return city;
    }
    /**
     *
     */
    public void setCity(final String city) {
        this.city = city;
    }
    /**
     *
     */
    public int getAge() {
        return age;
    }
    /**
     *
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * get command name
     */
    public String getCommand() {
        return command;
    }
    /**
     * set command name
     */
    public void setCommand(final String command) {
        this.command = command;
    }
    /**
     *
     */
    public String getUsername() {
        return username;
    }
    /**
     *
     */
    public void setUsername(final String username) {
        this.username = username;
    }
    /**
     *
     */
    public int getTimestamp() {
        return timestamp;
    }
    /**
     *
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
    /**
     *
     */
    public String getType() {
        return type;
    }
    /**
     *
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     *
     */
    public Filter getFilters() {
        return filters;
    }
    /**
     *
     */
    public void setFilters(final Filter filters) {
        this.filters = filters;
    }
    /**
     *
     */
    public int getItemNumber() {
        return itemNumber;
    }
    /**
     *
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }
    /**
     *
     */
    public String getPlaylistName() {
        return playlistName;
    }
    /**
     *
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }
    /**
     *
     */
    public int getPlaylistId() {
        return playlistId;
    }
    /**
     *
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }
    /**
     *
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    /**
     *
     */
    public static void setObjectMapper(final ObjectMapper objectMapper) {
        Command.objectMapper = objectMapper;
    }
    /**
     *
     */
    public ObjectNode getObjectNode() {
        return objectNode;
    }
    /**
     *
     */
    public void setObjectNode(final ObjectNode objectNode) {
        this.objectNode = objectNode;
    }
    /**
     *
     */
    public int getSeed() {
        return seed;
    }
    /**
     *
     */
    public void setSeed(final int seed) {
        this.seed = seed;
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
    public int getReleaseYear() {
        return releaseYear;
    }
    /**
     *
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }
    /**
     *
     */
    public String getDescription() {
        return description;
    }
    /**
     *
     */
    public void setDescription(final String description) {
        this.description = description;
    }
    /**
     *
     */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }
    /**
     *
     */
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }
    /**
     *
     */
    public String getDate() {
        return date;
    }
    /**
     *
     */
    public void setDate(final String date) {
        this.date = date;
    }
    /**
     *
     */
    public int getPrice() {
        return price;
    }
    /**
     *
     */
    public void setPrice(final int price) {
        this.price = price;
    }
    /**
     *
     */
    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }
    /**
     *
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }
    /**
     *
     */
    public String getNextPage() {
        return nextPage;
    }
    /**
     *
     */
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }
    /**
     *
     */
    public static void setLastTimestamp(final int lastTimestamp) {
        Command.lastTimestamp = lastTimestamp;
    }
    /**
     *
     */
    public static Command getLastCommand() {
        return lastCommand;
    }
    /**
     *
     */
    public static void setLastCommand(final Command lastCommand) {
        Command.lastCommand = lastCommand;
    }
}
