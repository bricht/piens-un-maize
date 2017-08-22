package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rock.werool.piensunmaize.R;

public class ClearDbActivity extends AppCompatActivity {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_db);

        context = getApplicationContext();
        db = new SQLiteHelper(context);
        database = db.getReadableDatabase();

        final MediaPlayer cat = MediaPlayer.create(this, R.raw.cat);
        Button play = (Button)this.findViewById(R.id.button3);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat.start();
            }
        });

        TextView textView = (TextView)this.findViewById(R.id.textView5);
        textView.setText(database.getPath());

    }

    public void onClick(View view){
        String message;
        boolean bool = context.deleteDatabase(db.getDatabaseName());
        if(bool == true){
            message = "Success";
        }else{
            message = "Fail";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }
}
