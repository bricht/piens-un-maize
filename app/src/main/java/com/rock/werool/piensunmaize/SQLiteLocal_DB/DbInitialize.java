package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DbInitialize extends IntentService {
    private SQLiteHelper helper;
    private SQLiteDatabase database;

    public DbInitialize(){
        super(SQLiteQuery.class.getName());
    }

    public DbInitialize(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        SQLiteAddData.insertProduct(0, "banana", "fruits");
        SQLiteAddData.insertStore(0, "Rimi", "Ulmana gatve");
        SQLiteAddData.insertBarcode("123456789", "banana");
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);



    }
}