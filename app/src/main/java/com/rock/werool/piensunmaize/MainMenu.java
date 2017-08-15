package com.rock.werool.piensunmaize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Intent intent = new Intent(this, BarcodeScanner.class);
        //startActivity(intent);
        startActivity(new Intent(MainMenu.this, BarcodeScanner.class));
    }
}
