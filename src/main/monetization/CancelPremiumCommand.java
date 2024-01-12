package main.monetization;

import main.entities.Artist;
import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;

import java.util.HashMap;
import java.util.Map;

public class CancelPremiumCommand extends Command {
    public CancelPremiumCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
    }
    @Override
    public void execCommand() {
        this.outputBase();
        if (!GlobalObjects.getInstance().containsNormalUser(getUsername())) {
            this.getObjectNode().put("message", "The username " + getUsername() + " doesn't exist.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (!UserSpaceDb.getDatabase().get(getUsername()).isPremiumUser()) {
            this.getObjectNode().put("message", getUsername() + " is not a premium user.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
            return;
        }
        if (getTimestamp() != -1) {
            UserSpaceDb.getDatabase().get(getUsername()).setPremiumUser(false);
            this.getObjectNode().put("message", getUsername() + " cancelled the subscription successfully.");
            GlobalObjects.getInstance().getOutputs().add(this.getObjectNode());
        }
        int totalListensInTimeframe = UserSpaceDb.getDatabase().get(getUsername()).getTop().getTimeFrameListenedSongs().size();
        HashMap<String, Integer> songsPerArtist = new HashMap<>();

        int total = 0;
        for (Map.Entry<String, Integer> listenedSong : UserSpaceDb.getDatabase().get(getUsername()).getTop().getTimeFrameListenedSongs().entrySet()) {
            total += listenedSong.getValue();
        }

        for (Map.Entry<String, Integer> listenedSong : UserSpaceDb.getDatabase().get(getUsername()).getTop().getTimeFrameListenedSongs().entrySet()) {
            Artist artist = GlobalObjects.getInstance().getArtistBySongName(listenedSong.getKey());
            if (artist != null) {
                if (!songsPerArtist.containsKey(artist.getUsername())) {
                    songsPerArtist.put(artist.getUsername(), listenedSong.getValue());
                } else {
                    songsPerArtist.replace(artist.getUsername(), songsPerArtist.get(artist.getUsername()) + listenedSong.getValue());
                }
                double moneyPerSong = (double) 1000000 * listenedSong.getValue() / total;
                artist.getTop().getRevenuePerSongs().replace(listenedSong.getKey(), artist.getTop().getRevenuePerSongs().get(listenedSong.getKey()) + moneyPerSong);
            }
        }
        for (Map.Entry<String, Integer> listens : songsPerArtist.entrySet()) {
            int timesListenedArtist = listens.getValue();
            double moneyPerSong = (double) 1000000 * timesListenedArtist / total;
            Artist artist = GlobalObjects.getInstance().existsArtist(listens.getKey());
            artist.getTop().setSongRevenue(artist.getTop().getSongRevenue() + moneyPerSong);
//            System.out.println(getUsername() + " " + timesListenedArtist + " " + artist.getUsername() + " " + totalListensInTimeframe);
            //artist.getTop().getRevenuePerSongs().replace(listenedSong.getKey(), artist.getTop().getRevenuePerSongs().get(listenedSong.getKey()) + moneyPerSong);
        }
//        System.out.println(GlobalObjects.getInstance().existsArtist("James Brown").getTop().getSongRevenue());
        UserSpaceDb.getDatabase().get(getUsername()).getTop().getTimeFrameListenedSongs().clear();
    }
}
