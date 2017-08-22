package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DropTheBassService extends IntentService {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;

    public DropTheBassService(){
        super(SQLiteAddData.class.getName());
    }
    public DropTheBassService(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
            context = getApplicationContext();
            db = new SQLiteHelper(context);
            database = db.getWritableDatabase();
    }

    @Override
    public void onHandleIntent(Intent intent){
        context.deleteDatabase(db.getDatabaseName());
    }

}
