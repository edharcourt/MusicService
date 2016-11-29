package com.example.ehar.musicserviceexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final int REQUEST_CODE = 999;
    private static final String LOG_TAG = MainActivity.class.getName();
    Button play = null;  // play musics button
    Button stop = null;  // stop music button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The mp3 file is on external storage
        // so we need to handle permissions
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }
        else {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE)
            if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            }
            else {
                Log.e(LOG_TAG, "permisssion not granted to read file");
            }

    }

    private void init() {

        // set up the views
        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);

        // set the click listener to start the
        // musci service
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.getInstance() == null)
                    startService(
                            new Intent(MainActivity.this,
                                    MusicService.class)
                    );
                else {
                    MusicService.getInstance().restart();
                }
            }
        });

        // set the click listener to stop
        // the music service
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(
                        new Intent(MainActivity.this,
                                MusicService.class)
                );
            }
        });
    }
}
