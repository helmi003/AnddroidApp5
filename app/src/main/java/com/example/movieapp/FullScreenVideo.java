package com.example.movieapp;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;


public class FullScreenVideo extends AppCompatActivity {
    private ImageView backArrow;
    private String videoUrl;
    String video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        videoUrl = getIntent().getStringExtra("videoUrl");
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(view -> finish());
        WebView webView = findViewById(R.id.webView);
        if (videoUrl == null || videoUrl.isEmpty()) {
            video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dQw4w9WgXcQ?si=K2LiuWxYwApJxXG8\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        }else{
            video = videoUrl;
        }
        webView.loadData(video, "text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }
}
