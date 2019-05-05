package com.trashfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button points = (Button) findViewById(R.id.points);
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(getApplicationContext(), PointsActivity.class);
                startActivity(intentMain);
                Log.i("Content "," Go to Points page. ");
            }
        });

        Button challenges = (Button) findViewById(R.id.challenge);
        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(getApplicationContext(), ChallengesActivity.class);
                startActivity(intentMain);
                Log.i("Content "," Go to Points page. ");
            }
        });

        Button redeem = (Button) findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPage = new Intent(getApplicationContext(), ConfirmPoints.class);
                startActivity(intentPage);
            }
        });

        /*
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You currently have 50 points!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        Button btn = (Button) findViewById(R.id.challenge);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(HomeActivity.this, v);
                popup.setOnMenuItemClickListener(HomeActivity.this);
                popup.inflate(R.menu.menu_challenges);
                popup.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.enrolled:
                // do your code
                Intent intentE = new Intent(getApplicationContext(), EnrolledActivity.class);
                startActivity(intentE);
                return true;
            case R.id.avail:
                // do your code
                Intent intentA = new Intent(getApplicationContext(), AvailableActivity.class);
                startActivity(intentA);
                return true;
            default:
                return false;
        }
    }
}
