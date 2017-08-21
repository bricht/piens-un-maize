package com.rock.werool.piensunmaize.mainpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.rock.werool.piensunmaize.R;

public class GreetingsActivity extends AppCompatActivity {
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        spinner.setVisibility(View.VISIBLE);
    }
}
