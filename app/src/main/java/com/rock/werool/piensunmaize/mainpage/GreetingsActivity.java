package com.rock.werool.piensunmaize.mainpage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        spinner.setVisibility(View.VISIBLE);
        handler = new Handler();

        handler = new Handler();

        final ConnectionVerifyer verifyer = new ConnectionVerifyer("http://zesloka.tk/piens_un_maize_db/", this);
        verifyer.addListener(new IRemoteDBConnectionFerifyHandler() {

            @Override
            public void OnConnectionLost(String msg) {
                TextView txt = (TextView) findViewById(R.id.textView2);
                txt.setText("Sorry no connection, you are doomd!");
            }

            @Override
            public void OnConnection() {
                TextView txt = (TextView) findViewById(R.id.textView2);
                txt.setText("Welcome");
                verifyer.Stop();
                handler.postDelayed(myRunnable = new Runnable() {
                    public void run() {
                        finish();
                        startActivity(new Intent(GreetingsActivity.this, MainMenu.class));
                    }
                }, 2000);

            }
        });
        verifyer.setTimeout(1000);
        verifyer.Start();
    }

    //TODO Fix bug: After app closing and then geting back in - never ending loop with loading screen
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(myRunnable);
    }
}
