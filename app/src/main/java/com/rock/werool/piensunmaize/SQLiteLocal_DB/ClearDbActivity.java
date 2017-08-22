package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rock.werool.piensunmaize.R;

public class ClearDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_db);
    }

    public void onClick(View view){
        Intent intent = new Intent(ClearDbActivity.this, DropTheBassService.class);
        ClearDbActivity.this.startService(intent);
    }
}
