package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.movieapp.entities.Season;

public class SeasonItemView extends LinearLayout {

    private TextView number;
    private Season season;

    public SeasonItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_season, this, true);
        number = findViewById(R.id.number);
    }

    public void bind(Season season) {
        this.season = season;
        number.setText(season.getNumber());
    }

    public Season getSerie() {
        return season;
    }
}