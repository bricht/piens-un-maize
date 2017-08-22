package com.rock.werool.piensunmaize.mainpage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.remoteDatabase.ConnectionVerifyer;
import com.rock.werool.piensunmaize.remoteDatabase.IRemoteDBConnectionFerifyHandler;

public class GreetingsActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private Handler handler;
    private Runnable myRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);
        /*
        final ConnectionVerifyer verifier = new ConnectionVerifyer("http://zesloka.tk/piens_un_maize_db/", this);
        verifier.addListener(new IRemoteDBConnectionFerifyHandler() {
            @Override
            public void OnConnectionLost(String msg) {

            }

            @Override
            public void OnConnection() {
                startActivity(new Intent(GreetingsActivity.this, MainMenu.class));
                verifier.Stop();
            }
        });
        verifier.setTimeout(3000);
        verifier.Start();
        */
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

    //TODO Fix bug: After app closing and then geting back in - never ending loop with loading screen
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(myRunnable);
    }
}
