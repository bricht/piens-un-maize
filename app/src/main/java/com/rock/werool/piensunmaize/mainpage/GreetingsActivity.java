package com.rock.werool.piensunmaize.mainpage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.rock.werool.piensunmaize.R;

public class GreetingsActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private Handler handler;
    private Runnable myRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        spinner.setVisibility(View.VISIBLE);
        handler = new Handler();

        handler.postDelayed(myRunnable = new Runnable() {
            public void run() {
                finish();
                startActivity(new Intent(GreetingsActivity.this, MainMenu.class));

            }
        }, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(myRunnable);
    }
}
