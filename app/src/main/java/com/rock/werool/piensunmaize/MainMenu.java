package com.rock.werool.piensunmaize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rock.werool.piensunmaize.barcode.BarcodeScanner;
import com.rock.werool.piensunmaize.search.SearchByProductActivity;
import com.rock.werool.piensunmaize.search.SearchByStoreActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        startActivity(new Intent(MainMenu.this, SearchByStoreActivity.class));
        //startActivity(new Intent(MainMenu.this, BarcodeScanner.class));
    }
}
