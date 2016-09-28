package com.example.alex.vuzixftw;

import android.app.Service;
import java.util.ArrayList;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import com.example.alex.vuzixftw.Song;

/**
 * Created by lauravsilva on 9/24/16.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
	private MediaPlayer player;
	private ArrayList<Song> songs;
	private int songPosn;

	private final IBinder musicBind = new MusicBinder();

	@Override
	public IBinder onBind(Intent arg) {
		return musicBind;
	}

	public boolean onUnbind(Intent intent) {
		player.stop();
		player.release();
		return false;
	}

	public void onCreate() {
		super.onCreate();
		songPosn = 0;
		player = new MediaPlayer();
		initMusicPlayer();
	}

	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}

	public void setSong(int songIndex) {
		songPosn = songIndex;
	}

	public void initMusicPlayer() {
		//Keeps music going after screen goes to sleep.
		player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);

		player.setOnPreparedListener(this);
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
	}

	public void setList(ArrayList<Song> theSongs) {
		songs = theSongs;
	}

	public void playSong() {
		player.reset();
		Song playSong = songs.get(songPosn);
		long currSong = playSong.getID();
		Uri trackUri = ContentUris.withAppendedId(
			android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSong);

		try {
			player.setDataSource(getApplicationContext(), trackUri);
		}
		catch (Exception e) {
			Log.e("Music Service", "Error Setting data source", e);
		}

		player.prepareAsync();
	}

	//TODO: ON Begin Playback STEP 3
	//https://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778
	public class MusicBinder extends Binder {
		MusicService getService() {
			return MusicService.this;
		}
	}


    public boolean onError(MediaPlayer mp, int what, int extra){
        return false;
    }

    public void onCompletion(MediaPlayer mp){

    }
}