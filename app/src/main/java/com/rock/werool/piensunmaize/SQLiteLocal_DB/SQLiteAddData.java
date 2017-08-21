package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class SQLiteAddData extends IntentService {







    private SQLiteHelper helper;
    private SQLiteDatabase database;

    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
