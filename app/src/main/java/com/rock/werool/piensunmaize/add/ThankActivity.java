package com.rock.werool.piensunmaize.add;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.mainpage.GreetingsActivity;
import com.rock.werool.piensunmaize.mainpage.MainMenu;

public class ThankActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);



        handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                startActivity(new Intent(ThankActivity.this, MainMenu.class));

            }
        }, 2500);

    }
}
