package main.monetization;

import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.UserSpaceDb;

import java.util.HashMap;
import java.util.Map;

public final class AdMonetization {
    private AdMonetization() {

    }

    /**
     *
     * @param username whose listened songs need to be monetized
     * @param price for paid for the add
     */
    public static void monetize(final String username, final int price) {
        HashMap<String, Integer> songsPerArtist = new HashMap<>();
        int total = 0;
        for (Map.Entry<String, Integer> listenedSong : UserSpaceDb.getDatabase()
                .get(username).getTop().getMonetizedListenedSongs().entrySet()) {
            total += listenedSong.getValue();
        }

        for (Map.Entry<String, Integer> listenedSong : UserSpaceDb.getDatabase().get(username)
                .getTop().getMonetizedListenedSongs().entrySet()) {
            Artist artist = GlobalObjects.getInstance().getArtistBySongName(listenedSong.getKey());
            if (artist != null) {
                if (!songsPerArtist.containsKey(artist.getUsername())) {
                    songsPerArtist.put(artist.getUsername(), listenedSong.getValue());
                } else {
                    songsPerArtist.replace(artist.getUsername(), songsPerArtist
                            .get(artist.getUsername()) + listenedSong.getValue());
                }
                double moneyPerSong = (double) price * listenedSong.getValue() / total;
                artist.getTop().getRevenuePerSongs().replace(listenedSong.getKey(),
                        artist.getTop().getRevenuePerSongs()
                                .get(listenedSong.getKey()) + moneyPerSong);
            }
        }
        for (Map.Entry<String, Integer> listens : songsPerArtist.entrySet()) {
            int timesListenedArtist = listens.getValue();
            double moneyPerSong = (double) price * timesListenedArtist / total;
            Artist artist = GlobalObjects.getInstance().existsArtist(listens.getKey());
            artist.getTop().setSongRevenue(artist.getTop().getSongRevenue() + moneyPerSong);
        }
        UserSpaceDb.getDatabase().get(username).getTop().getMonetizedListenedSongs().clear();
    }
}
