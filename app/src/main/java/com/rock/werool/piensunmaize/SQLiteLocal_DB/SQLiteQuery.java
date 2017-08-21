package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.gms.games.request.Requests;

/**
 * Created by user on 2017.08.20.
 */

public class SQLiteQuery extends IntentService implements Parcelable{

    // Japadod intent ar cetriem laukiem
    // Key SQLiteQuery.SRC.TYPE             Value SQLiteQuery.SRC_PRODUCT_AVG_PRICE/SRC_PRICE/SRC_NAME_PRICE_ALL
    // Key SQLiteQuery.SRC_NAME             Value String string or null
    // Key SQLiteQuery.SRC_STORE            Value String string or null
    // Key SQLiteQuery.SRC_ADDRESS          Value String string or null

    public static final String SRC_TYPE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.TYPE";   // search type
    public static final int SRC_PRODUCT_AVG_PRICE = 1;                                          // expected result - average price in all stores
    public static final int SRC_PRICE = 2;                                                      // expected result - price one store different search criteria
    public static final int SRC_NAME_PRICE_ALL = 3;                                             // expected result - price for all products in single store

    public static final String SRC_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.NAME";                   // search parameter - product name
    public static final String SRC_STORE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.STORE";                 // search parameter - store name
    public static final String SRC_ADDRESS = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ADDRESS";             // search parameter - store address

    public static final String QUERY_RESULT = "queryResult";                                                    // obtained values

    private Cursor result;
    private SQLiteHelper helper;
    private SQLiteDatabase database;

    public SQLiteQuery(){
        super(SQLiteQuery.class.getName());
    }

    public SQLiteQuery(String name) {
        super(name);
    }

    //Override Parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    // Override IntentService methods

    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int i = intent.getIntExtra(SRC_TYPE, 0);
        String product = intent.getStringExtra(SRC_NAME);
        String store = intent.getStringExtra(SRC_STORE);
        String address = intent.getStringExtra(SRC_ADDRESS);

        if(product == null && store == null && address == null){
            i = 0;
        }

        switch (i){
            case 1: i = 1;
                String query = "SELECT";
                break;
            case  2: i = 2;

                break;
            case 3: i = 3;

                break;
            case 4 : i = 0;

                break;
        }

        publishResults(intent);

    }

    private void publishResults(Intent intent){
        sendBroadcast(intent);
    }


}