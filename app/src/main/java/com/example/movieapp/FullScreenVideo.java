package com.example.movieapp;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;


public class FullScreenVideo extends AppCompatActivity {

    private PlayerView playerView;
    private ImageButton closeButton;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

        playerView = findViewById(R.id.playerView);
        closeButton = findViewById(R.id.closeButton);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        closeButton.setOnClickListener(v -> {
            player.release();
            finish();
        });

        String videoUrl = getIntent().getStringExtra("videoUrl");
        if (videoUrl != null) {
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d("PlayerState", "Playback state changed: " + playbackState);
                if (playbackState == Player.STATE_READY) {
                    player.play();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release(); // Ensure you release the player
    }
}
