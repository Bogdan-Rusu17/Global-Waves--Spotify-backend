package main.monetization;

import main.globals.GlobalObjects;
import main.userspace.Command;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.subtypes.PodcastState;

public class CreateAdBreakCommand extends Command {
    public CreateAdBreakCommand(Command command) {
        this.setCommand(command.getCommand());
        this.setUsername(command.getUsername());
        this.setTimestamp(command.getTimestamp());
        this.setPrice(command.getPrice());
    }

    @Override
    public void execCommand() {
        this.outputBase();
        if (!GlobalObjects.getInstance().containsNormalUser(getUsername())) {
            this.outputErrorMessage("The username " + getUsername() + " doesn't exist.");
            return;
        }

        UserSpaceDb.getDatabase().get(getUsername()).setAdPrice(this.getPrice());
        UserSpaceDb.getDatabase().get(getUsername()).setIncomingAd(true);
        String normalUserName = getUsername();
        boolean isAnyAudioLoaded = false;
        if (UserSpaceDb.getDatabase().get(normalUserName)
                .getPlayer().getLoadedSong() != null) {
            isAnyAudioLoaded = true;
            int remTime = UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getSongState().getRemainedTime() + 10;
            UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getSongState().setRemainedTime(remTime);
        } else if (UserSpaceDb.getDatabase().get(normalUserName)
                .getPlayer().getLoadedPodcast() != null) {
            isAnyAudioLoaded = true;
            int remTime = UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getPodcastState().getRemainedTime() + 10;
            UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getPodcastState().setRemainedTime(remTime);
            PodcastState newState =
                    UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                            .getPodcastState();
            UserSpaceDb.getDatabase().get(normalUserName).getPlayer().getResumer()
                    .getResumeMap().replace(
                            UserSpaceDb.getDatabase().get(normalUserName)
                                    .getPlayer().getLoadedPodcast(), newState);
        } else if (UserSpaceDb.getDatabase().get(normalUserName)
                .getPlayer().getLoadedPlaylist() != null) {
            isAnyAudioLoaded = true;
            int remTime = UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getPlaylistState().getRemainedTime() + 10;
            UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getPlaylistState().setRemainedTime(remTime);
        } else if (UserSpaceDb.getDatabase().get(normalUserName)
                .getPlayer().getLoadedAlbum() != null) {
            isAnyAudioLoaded = true;
            int remTime = UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getAlbumState().getRemainedTime() + 10;
            UserSpaceDb.getDatabase().get(normalUserName).getPlayer()
                    .getAlbumState().setRemainedTime(remTime);
        }
        if (!isAnyAudioLoaded) {
            this.outputErrorMessage(getUsername() + " is not playing any music.");
        } else {
            this.outputErrorMessage("Ad inserted successfully.");
        }
    }
}
