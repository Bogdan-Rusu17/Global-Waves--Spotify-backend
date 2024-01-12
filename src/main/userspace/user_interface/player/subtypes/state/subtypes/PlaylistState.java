package main.userspace.user_interface.player.subtypes.state.subtypes;

import main.entities.Playlist;
import main.globals.GlobalObjects;
import main.monetization.AdMonetization;
import main.userspace.UserSpaceDb;
import main.userspace.user_interface.player.subtypes.state.State;

import java.util.ArrayList;

public final class PlaylistState extends State {
    private Playlist playlist;
    private int songIndex;
    private ArrayList<Integer> idxOrder;
    private boolean finished;

    public PlaylistState(final String name, final int remainedTime, final Playlist playlist,
                         final int songIndex, final ArrayList<Integer> idxOrder) {
        super(name, remainedTime);
        this.playlist = playlist;
        this.songIndex = songIndex;
        this.idxOrder = idxOrder;
    }

    /**
     *
     * @param index for the current song
     * @return index of the next song taking shuffle into account
     */
    public int getNextSong(final int index) {
        for (int i = 0; i < idxOrder.size(); i++) {
            if (idxOrder.get(i) == index) {
                if (i == idxOrder.size() - 1) {
                    return -1;
                }
                return idxOrder.get(i + 1);
            }
        }
        return -1;
    }
    /**
     *
     * @param index for the current song
     * @return index of the previous song taking shuffle into account
     */
    public int getPrevSong(final int index) {
        for (int i = 0; i < idxOrder.size(); i++) {
            if (idxOrder.get(i) == index) {
                if (i == 0) {
                    return -1;
                }
                return idxOrder.get(i - 1);
            }
        }
        return -1;
    }

    @Override
    public void changeState(final int time, final String username) {
        int timestamp = time;
        if (this.isPaused()) {
            return;
        }
        if (this.getRepeat().equals("No Repeat")) {
            if (timestamp < this.getRemainedTime()) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                if (UserSpaceDb.getDatabase().get(username).isIncomingAd()) {
                    if (this.getRemainedTime() <= 10) {
                        AdMonetization.monetize(username, UserSpaceDb.getDatabase().get(username).getAdPrice());
                        UserSpaceDb.getDatabase().get(username).setIncomingAd(false);
                    }
                }
                return;
            }
            if (UserSpaceDb.getDatabase().get(username).isIncomingAd()) {
                AdMonetization.monetize(username, UserSpaceDb.getDatabase().get(username).getAdPrice());
                UserSpaceDb.getDatabase().get(username).setIncomingAd(false);
            }
            int nextSongIdx = getNextSong(songIndex);
            if (nextSongIdx == -1) {
                this.setOnFinished();
                finished = true;
                UserSpaceDb.getDatabase().get(username)
                        .resetAllSelectLoad();
                return;
            }
            timestamp -= this.getRemainedTime();
            while (nextSongIdx != -1 && timestamp >= playlist.getSongList()
                    .get(nextSongIdx).getDuration()) {
                timestamp -= playlist.getSongList().get(nextSongIdx).getDuration();
                UserSpaceDb.getDatabase().get(username).getTop()
                        .listenSong(playlist.getSongList().get(nextSongIdx), 1);
                GlobalObjects.getInstance().existsArtist(playlist.getSongList()
                                .get(nextSongIdx).getArtist())
                        .getTop().getListenerSong(playlist.getSongList().get(nextSongIdx),
                                1, username);
                nextSongIdx = getNextSong(nextSongIdx);
            }
            if (nextSongIdx == -1) {
                this.setOnFinished();
                finished = true;
                UserSpaceDb.getDatabase().get(username)
                        .resetAllSelectLoad();
                return;
            }
            songIndex = nextSongIdx;
            this.setName(playlist.getSongList().get(nextSongIdx).getName());
            this.setRemainedTime(playlist.getSongList().get(nextSongIdx)
                    .getDuration() - timestamp);
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenSong(playlist.getSongList().get(nextSongIdx), 1);
            GlobalObjects.getInstance().existsArtist(playlist.getSongList()
                            .get(nextSongIdx).getArtist())
                    .getTop().getListenerSong(playlist.getSongList().get(nextSongIdx),
                            1, username);
            return;
        } else if (this.getRepeat().equals("Repeat Current Song")) {
            if (this.getRemainedTime() > timestamp) {
                this.setRemainedTime(this.getRemainedTime() - timestamp);
                return;
            }
            this.setRemainedTime(playlist.getSongList()
                    .get(songIndex).getDuration() - (timestamp - this.getRemainedTime())
                    % playlist.getSongList()
                    .get(songIndex).getDuration());
            int times = (timestamp - this.getRemainedTime())
                    % playlist.getSongList()
                    .get(songIndex).getDuration() + 1;
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenSong(playlist.getSongList().get(songIndex), times);
            GlobalObjects.getInstance().existsArtist(playlist.getSongList()
                            .get(songIndex).getArtist())
                    .getTop().getListenerSong(playlist.getSongList().get(songIndex),
                            times, username);
            return;
        }
        if (timestamp < this.getRemainedTime()) {
            this.setRemainedTime(this.getRemainedTime() - timestamp);
            return;
        }
        songIndex = getNextSong(songIndex);
        if (songIndex == -1) {
            songIndex = idxOrder.get(0);
        }
        timestamp -= this.getRemainedTime();

        while (timestamp >= playlist.getSongList().get(songIndex).getDuration()) {
            timestamp -= playlist.getSongList().get(songIndex).getDuration();
            UserSpaceDb.getDatabase().get(username).getTop()
                    .listenSong(playlist.getSongList().get(songIndex), 1);
            GlobalObjects.getInstance().existsArtist(playlist.getSongList()
                            .get(songIndex).getArtist())
                    .getTop().getListenerSong(playlist.getSongList().get(songIndex),
                            1, username);
            songIndex = getNextSong(songIndex);
            if (songIndex == -1) {
                songIndex = idxOrder.get(0);
            }
        }
        UserSpaceDb.getDatabase().get(username).getTop()
                .listenSong(playlist.getSongList().get(songIndex), 1);
        GlobalObjects.getInstance().existsArtist(playlist.getSongList()
                        .get(songIndex).getArtist())
                .getTop().getListenerSong(playlist.getSongList().get(songIndex),
                        1, username);
        this.setName(playlist.getSongList().get(songIndex).getName());
        this.setRemainedTime(playlist.getSongList().get(songIndex).getDuration() - timestamp);
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }

    public int getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(final int songIndex) {
        this.songIndex = songIndex;
    }

    public ArrayList<Integer> getIdxOrder() {
        return idxOrder;
    }

    public void setIdxOrder(final ArrayList<Integer> idxOrder) {
        this.idxOrder = idxOrder;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }
}
