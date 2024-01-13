package main.userspace.user_interface.player.subtypes.state.subtypes;

import main.globals.GlobalObjects;
import main.monetization.AdMonetization;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.Player;
import main.userspace.user_interface.player.subtypes.state.State;

public final class SongState extends State {
    private static final int AD_LENGTH = 10;
    public SongState(final String name, final int remainedTime) {
        super(name, remainedTime);
    }
    @Override
    public void changeState(final int timestamp, final String username) {
        Player currentPlayer = UserSpaceDb.getDatabase().get(username).getPlayer();
        if (this.isPaused()) {
            return;
        }
        if (this.getRepeat().equals("No Repeat")) {
            if (timestamp < this.getRemainedTime()) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                if (UserSpaceDb.getDatabase().get(username).isIncomingAd()) {
                    if (this.getRemainedTime() <= AD_LENGTH) {
                        AdMonetization.monetize(username, UserSpaceDb
                                .getDatabase().get(username).getAdPrice());
                        UserSpaceDb.getDatabase().get(username).setIncomingAd(false);
                    }
                }
                return;
            }
            if (UserSpaceDb.getDatabase().get(username).isIncomingAd()) {
                AdMonetization.monetize(username, UserSpaceDb
                        .getDatabase().get(username).getAdPrice());
                UserSpaceDb.getDatabase().get(username).setIncomingAd(false);
            }
            this.setOnFinished();
            UserSpaceDb.getDatabase().get(username).resetAllSelectLoad();
            return;
        }
        if (this.getRepeat().equals("Repeat Once")) {
            if (timestamp < this.getRemainedTime()) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                return;
            }
            if (timestamp < this.getRemainedTime() + currentPlayer.getLoadedSong().getDuration()) {
                UserSpaceDb.getDatabase().get(username).getTop()
                        .listenSong(currentPlayer.getLoadedSong(), 1);
                GlobalObjects.getInstance().existsArtist(currentPlayer.getLoadedSong().getArtist())
                        .getTop().getListenerSong(currentPlayer.getLoadedSong(), 1, username);
                this.setRemainedTime(this.getRemainedTime() + currentPlayer.getLoadedSong()
                        .getDuration() - timestamp);
                this.setRepeat("No Repeat");
                return;
            }
            this.setOnFinished();
            UserSpaceDb.getDatabase().get(username).resetAllSelectLoad();
            return;
        }
        if (this.getRemainedTime() > timestamp) {
            this.setRemainedTime(this.getRemainedTime() - timestamp);
            return;
        }
        this.setRemainedTime(currentPlayer.getLoadedSong().getDuration()
                - (timestamp - this.getRemainedTime()) % currentPlayer
                .getLoadedSong().getDuration());
        UserSpaceDb.getDatabase().get(username).getTop()
                .listenSong(currentPlayer.getLoadedSong(),
                        (timestamp - this.getRemainedTime()) / currentPlayer
                        .getLoadedSong().getDuration() + 1);
        GlobalObjects.getInstance().existsArtist(currentPlayer.getLoadedSong().getArtist())
                .getTop().getListenerSong(currentPlayer.getLoadedSong(),
                        (timestamp - this.getRemainedTime()) / currentPlayer
                        .getLoadedSong().getDuration() + 1, username);
    }
}
