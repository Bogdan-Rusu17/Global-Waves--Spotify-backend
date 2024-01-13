package main.recommendations;

import fileio.input.SongInput;
import fileio.input.UserInput;
import main.entities.Album;
import main.entities.Artist;
import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.globals.LikeDB;
import main.globals.PlaylistDB;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

import java.util.*;
import java.util.stream.Collectors;

public final class UpdateRecommendationsCommand extends Command {
    private int remTime;
    private static final int FIVE = 5;
    private static final int THREE = 3;
    private static final int TWO = 2;
    private static final int THIRTY_SECONDS = 30;


    public UpdateRecommendationsCommand(final Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setRecommendationType(command.getRecommendationType());
    }

    /**
     *
     * @return the artist whose current audioFile user is playing
     */
    public Artist getArtistOfCurrentLoadedSource() {
        Artist artist = null;
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedAlbum() != null) {
            artist = GlobalObjects.getInstance().existsArtist(
                    UserSpaceDb.getDatabase().get(getUsername())
                            .getPlayer().getLoadedAlbum().getOwner());
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong() != null) {
            artist = GlobalObjects.getInstance().existsArtist(
                    UserSpaceDb.getDatabase().get(getUsername())
                            .getPlayer().getLoadedSong().getArtist());
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedPlaylist() != null) {
            Playlist loadedPlaylist = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getLoadedPlaylist();
            int songIdx = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getPlaylistState().getSongIndex();
            artist = GlobalObjects.getInstance().existsArtist(
                    loadedPlaylist.getSongList().get(songIdx).getArtist());
        }
        return artist;
    }

    /**
     *
     * @param user whose most liked songs need to be sorted
     * @return hashmap that stores the song object and the total number of likes it has
     * limited to 5 songs
     */
    public HashMap<SongInput, Integer> buildTop5LikedSongsForUser(final UserInput user) {
        HashMap<SongInput, Integer> allLikedSongsByUser = new HashMap<>();
        for (SongInput song : UserSpaceDb.getDatabase().get(user.getUsername()).getLikedSongs()) {
            allLikedSongsByUser.put(song, LikeDB.getLikedSongs().get(song));
        }
        return allLikedSongsByUser.entrySet().stream()
                .sorted(Map.Entry.<SongInput, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(FIVE)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new));
    }

    /**
     * method that creates the playlist based on the liked songs of the fans of the artist
     * whose song the user is currently playing
     * first off, the top fans are stored in a hashmap based on the artist's top fans
     * next, the hashmaps returned for each of the top fans are combined and again
     * sorted to be put in a playlist for the user's recommendations
     */
    public void createFansPlaylist() {
        Artist artist = getArtistOfCurrentLoadedSource();
        HashMap<String, Integer> topFans = artist.getTop().getTopFans().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(FIVE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        HashMap<SongInput, Integer> combinedMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : topFans.entrySet()) {
            UserInput user = GlobalObjects.getInstance().existsNormalUser(entry.getKey());
            buildTop5LikedSongsForUser(user).forEach((key, value) ->
                    combinedMap.merge(key, value, (existingValue, newValue) -> existingValue));
        }
        HashMap<SongInput, Integer> sortedMap = combinedMap.entrySet().stream()
                .sorted(Map.Entry.<SongInput, Integer>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        if (!sortedMap.isEmpty()) {
            Playlist newPlaylist = new Playlist(artist.getUsername() + " Fan Club recommendations",
                    getUsername(), "public", 0);
            for (Map.Entry<SongInput, Integer> entry : sortedMap.entrySet()) {
                newPlaylist.getSongList().add(entry.getKey());
            }
            PlaylistDB.addPlaylist(newPlaylist);
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList().add(newPlaylist);
            UserSpaceDb.getDatabase().get(this.getUsername())
                    .getRecommendedPlaylists().add(newPlaylist);
            newPlaylist.attach(UserSpaceDb.getDatabase().get(this.getUsername()));
            this.outputErrorMessage("The recommendations for user " + getUsername()
                    + " have been updated successfully.");
            return;
        }
        this.outputErrorMessage("No new recommendations were found");
    }

    /**
     *
     * @return currently played song
     */
    public SongInput getCurrentlyPlayingSong() {
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong() != null) {
            remTime = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getSongState().getRemainedTime();
            return UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong();
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedPlaylist() != null) {
            Playlist loadedPlaylist = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getLoadedPlaylist();
            int index = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getPlaylistState().getSongIndex();
            remTime = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getPlaylistState().getRemainedTime();
            return loadedPlaylist.getSongList().get(index);
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedAlbum() != null) {
            Album loadedAlbum = UserSpaceDb.getDatabase()
                    .get(getUsername()).getPlayer().getLoadedAlbum();
            int index = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getAlbumState().getSongIndex();
            remTime = UserSpaceDb.getDatabase().get(getUsername())
                    .getPlayer().getAlbumState().getRemainedTime();
            return loadedAlbum.getSongs().get(index);
        }
        return null;
    }

    /**
     * method that gets a random song to be put inside the song recommendations list for the user
     * we construct a list of songs that are of the same genre as the currently playing song
     * then we choose a random song from that list based on the passed time from the currently
     * playing song, and we add that random song to the list of recommended songs
     */
    public void buildRandomSong() {
        SongInput playingSong = getCurrentlyPlayingSong();
        if (playingSong.getDuration() - remTime >= THIRTY_SECONDS) {
            ArrayList<SongInput> genreSongs = new ArrayList<>();
            for (SongInput song : GlobalObjects.getInstance().getLibrary().getSongs()) {
                if (playingSong.getGenre().equals(song.getGenre())) {
                    genreSongs.add(song);
                }
            }
            int idx = (new Random(playingSong.getDuration() - remTime)).nextInt(genreSongs.size());
            if (!genreSongs.isEmpty()) {
                UserSpaceDb.getDatabase().get(getUsername())
                        .getRecommendedSongs().add(genreSongs.get(idx));
                this.outputErrorMessage("The recommendations for user "
                        + getUsername() + " have been updated successfully.");
                return;
            }
        }
        this.outputErrorMessage("No new recommendations were found");
    }

    /**
     *
     * @param genre of the songs we want to search for
     * @param maxResults number of maximum results we want to put in the hashmap to be returned
     * @return a hashmap contain at most maxResults songs in the given genre
     * the songs of that particular genre are put in a hashmap and then the hashmap is sorted
     * in terms of how many likes that song has overall
     */
    public HashMap<SongInput, Integer> getTopGenreSongs(final String genre, final int maxResults) {
        HashMap<SongInput, Integer> likedGenreSongs = new HashMap<>();
        for (SongInput song : GlobalObjects.getInstance().getLibrary().getSongs()) {
            if (song.getGenre().equals(genre)) {
                likedGenreSongs.put(song, LikeDB.getLikedSongs().get(song));
            }
        }
        return likedGenreSongs.entrySet().stream()
                .sorted(Map.Entry.<SongInput, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(maxResults)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    /**
     * method that constructs a new playlist based on the top 3 genres
     * the user prefers
     * we initialize a hashmap that holds the genre and the number of songs
     * the artist prefers from this genre from songs the user has liked
     * playlists created by the user and playlists the user follows
     * after that, the getTopGenreSongs() method is called to obtain the
     * hashmaps for each genre
     * the hashmaps are combined and sorted again in terms of the number
     * of likes for that given song and a new playlist is built for the
     * recommended playlists and added to it
     */
    public void buildRandomPlaylist() {
        HashMap<String, Integer> userPreferredGenres = new HashMap<>();
        for (SongInput song : UserSpaceDb.getDatabase().get(getUsername()).getLikedSongs()) {
            if (!userPreferredGenres.containsKey(song.getGenre())) {
                userPreferredGenres.put(song.getGenre(), 1);
            } else {
                userPreferredGenres.replace(song.getGenre(),
                        userPreferredGenres.get(song.getGenre()));
            }
        }
        for (Playlist playlist : UserSpaceDb.getDatabase().get(getUsername()).getPlaylistList()) {
            for (SongInput song : playlist.getSongList()) {
                if (!userPreferredGenres.containsKey(song.getGenre())) {
                    userPreferredGenres.put(song.getGenre(), 1);
                } else {
                    userPreferredGenres.replace(song.getGenre(),
                            userPreferredGenres.get(song.getGenre()));
                }
            }
        }
        for (Map.Entry<Playlist, Integer> entry : UserSpaceDb.getDatabase()
                .get(getUsername()).getIsFollowingMap().entrySet()) {
            if (entry.getValue() == 1) {
                for (SongInput song : entry.getKey().getSongList()) {
                    if (!userPreferredGenres.containsKey(song.getGenre())) {
                        userPreferredGenres.put(song.getGenre(), 1);
                    } else {
                        userPreferredGenres.replace(song.getGenre(),
                                userPreferredGenres.get(song.getGenre()));
                    }
                }
            }
        }
        LinkedHashMap<String, Integer> sortedByValueDesc = userPreferredGenres.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(THREE)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        HashMap<String, Integer> maxSongsPerGenre = new HashMap<>();
        int idx = 0;
        for (Map.Entry<String, Integer> entry : sortedByValueDesc.entrySet()) {
            if (idx == 0) {
                maxSongsPerGenre.put(entry.getKey(), FIVE);
            } else if (idx == 1) {
                maxSongsPerGenre.put(entry.getKey(), THREE);
            } else {
                maxSongsPerGenre.put(entry.getKey(), TWO);
            }
            idx++;
        }
        HashMap<SongInput, Integer> combinedGenres = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sortedByValueDesc.entrySet()) {
            getTopGenreSongs(entry.getKey(), maxSongsPerGenre
                    .get(entry.getKey())).forEach((key, value) ->
                    combinedGenres.merge(key, value, (existingValue, newValue) -> existingValue));
        }
        HashMap<SongInput, Integer> sortedSongs = combinedGenres.entrySet().stream()
                .sorted(Map.Entry.<SongInput, Integer>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        if (!sortedSongs.isEmpty()) {
            Playlist newPlaylist = new Playlist(getUsername() + "'s recommendations",
                    getUsername(), "public", 0);
            for (Map.Entry<SongInput, Integer> entry : sortedSongs.entrySet()) {
                newPlaylist.getSongList().add(entry.getKey());
            }
            PlaylistDB.addPlaylist(newPlaylist);
            UserSpaceDb.getDatabase().get(this.getUsername()).getPlaylistList().add(newPlaylist);
            UserSpaceDb.getDatabase().get(this.getUsername())
                    .getRecommendedPlaylists().add(newPlaylist);
            newPlaylist.attach(UserSpaceDb.getDatabase().get(this.getUsername()));
            this.outputErrorMessage("The recommendations for user "
                    + getUsername() + " have been updated successfully.");
            return;
        }
        this.outputErrorMessage("No new recommendations were found");

    }

    @Override
    public void execCommand() {
        this.outputBase();

        if (!GlobalObjects.getInstance().existsUsername(getUsername())) {
            this.outputErrorMessage("The username " + getUsername() + " doesn't exist.");
            return;
        }

        UserInput user = GlobalObjects.getInstance().existsNormalUser(getUsername());
        if (user == null) {
            this.outputErrorMessage(getUsername() + " is not a normal user");
            return;
        }

        switch (this.getRecommendationType()) {
            case "fans_playlist" -> {
                createFansPlaylist();
                UserSpaceDb.getDatabase().get(getUsername()).setLastRecommendation("playlist");
            }
            case "random_song" -> {
                buildRandomSong();
                UserSpaceDb.getDatabase().get(getUsername()).setLastRecommendation("song");
            }
            case "random_playlist" -> {
                buildRandomPlaylist();
                UserSpaceDb.getDatabase().get(getUsername()).setLastRecommendation("playlist");
            }
            default -> {

            }
        }
    }
}
