package main.recommendations;

import com.fasterxml.jackson.databind.node.ArrayNode;
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

public class UpdateRecommendationsCommand extends Command {
    private int remTime;
    public UpdateRecommendationsCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setRecommendationType(command.getRecommendationType());
    }

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
    public HashMap<SongInput, Integer> buildTop5LikedSongsForUser(UserInput user) {
        HashMap<SongInput, Integer> allLikedSongsByUser = new HashMap<>();
        for (SongInput song : UserSpaceDb.getDatabase().get(user.getUsername()).getLikedSongs()) {
            allLikedSongsByUser.put(song, LikeDB.getLikedSongs().get(song));
        }
        return allLikedSongsByUser.entrySet().stream()
                .sorted(Map.Entry.<SongInput, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new));
    }
    public void createFansPlaylist() {
        Artist artist = getArtistOfCurrentLoadedSource();
        HashMap<String, Integer> topFans = artist.getTop().getTopFans().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(entry -> entry.getKey()))
                .limit(5)
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
            UserSpaceDb.getDatabase().get(this.getUsername()).getRecommendedPlaylists().add(newPlaylist);
            newPlaylist.attach(UserSpaceDb.getDatabase().get(this.getUsername()));
            this.outputErrorMessage("The recommendations for user " + getUsername() + " have been updated successfully.");
            return;
        }
        this.outputErrorMessage("No new recommendations were found");
    }

    public SongInput getCurrentlyPlayingSong() {
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong() != null) {
            remTime = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getSongState().getRemainedTime();
            return UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedSong();
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedPlaylist() != null) {
            Playlist loadedPlaylist = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedPlaylist();
            int index = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getPlaylistState().getSongIndex();
            remTime = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getPlaylistState().getRemainedTime();
            return loadedPlaylist.getSongList().get(index);
        }
        if (UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedAlbum() != null) {
            Album loadedAlbum = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getLoadedAlbum();
            int index = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getAlbumState().getSongIndex();
            remTime = UserSpaceDb.getDatabase().get(getUsername()).getPlayer().getAlbumState().getRemainedTime();
            return loadedAlbum.getSongs().get(index);
        }
        return null;
    }

    public void buildRandomSong() {
        SongInput playingSong = getCurrentlyPlayingSong();
        if (playingSong.getDuration() - remTime >= 30) {
            ArrayList<SongInput> genreSongs = new ArrayList<>();
            for (SongInput song : GlobalObjects.getInstance().getLibrary().getSongs()) {
                if (playingSong.getGenre().equals(song.getGenre())) {
                    genreSongs.add(song);
                }
            }
            int idx = (new Random(playingSong.getDuration() - remTime)).nextInt(genreSongs.size());
            if (!genreSongs.isEmpty()) {
                UserSpaceDb.getDatabase().get(getUsername()).getRecommendedSongs().add(genreSongs.get(idx));
                this.outputErrorMessage("The recommendations for user " + getUsername() + " have been updated successfully.");
                return;
            }
        }
        this.outputErrorMessage("No new recommendations were found");
    }

    public HashMap<SongInput, Integer> getTopGenreSongs(String genre, int maxResults) {
        HashMap<SongInput, Integer> likedGenreSongs = new HashMap<>();
        for (SongInput song : GlobalObjects.getInstance().getLibrary().getSongs()) {
            if (song.getGenre().equals(genre))
                likedGenreSongs.put(song, LikeDB.getLikedSongs().get(song));
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

    public void buildRandomPlaylist() {
        HashMap<String, Integer> userPreferredGenres = new HashMap<>();
        for (SongInput song : UserSpaceDb.getDatabase().get(getUsername()).getLikedSongs()) {
            if (!userPreferredGenres.containsKey(song.getGenre())) {
                userPreferredGenres.put(song.getGenre(), 1);
            } else {
                userPreferredGenres.replace(song.getGenre(), userPreferredGenres.get(song.getGenre()));
            }
        }
        for (Playlist playlist : UserSpaceDb.getDatabase().get(getUsername()).getPlaylistList()) {
            for (SongInput song : playlist.getSongList()) {
                if (!userPreferredGenres.containsKey(song.getGenre())) {
                    userPreferredGenres.put(song.getGenre(), 1);
                } else {
                    userPreferredGenres.replace(song.getGenre(), userPreferredGenres.get(song.getGenre()));
                }
            }
        }
        for (Map.Entry<Playlist, Integer> entry : UserSpaceDb.getDatabase().get(getUsername()).getIsFollowingMap().entrySet()) {
            if (entry.getValue() == 1) {
                for (SongInput song : entry.getKey().getSongList()) {
                    if (!userPreferredGenres.containsKey(song.getGenre())) {
                        userPreferredGenres.put(song.getGenre(), 1);
                    } else {
                        userPreferredGenres.replace(song.getGenre(), userPreferredGenres.get(song.getGenre()));
                    }
                }
            }
        }
        LinkedHashMap<String, Integer> sortedByValueDesc = userPreferredGenres.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        HashMap<String, Integer> maxSongsPerGenre = new HashMap<>();
        int idx = 0;
        for (Map.Entry<String, Integer> entry : sortedByValueDesc.entrySet()) {
            if (idx == 0) {
                maxSongsPerGenre.put(entry.getKey(), 5);
            } else if (idx == 1) {
                maxSongsPerGenre.put(entry.getKey(), 3);
            } else {
                maxSongsPerGenre.put(entry.getKey(), 2);
            }
            idx++;
        }
        HashMap<SongInput, Integer> combinedGenres = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sortedByValueDesc.entrySet()) {
            getTopGenreSongs(entry.getKey(), maxSongsPerGenre.get(entry.getKey())).forEach((key, value)->
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
            UserSpaceDb.getDatabase().get(this.getUsername()).getRecommendedPlaylists().add(newPlaylist);
            newPlaylist.attach(UserSpaceDb.getDatabase().get(this.getUsername()));
            this.outputErrorMessage("The recommendations for user " + getUsername() + " have been updated successfully.");
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
        }
    }
}
