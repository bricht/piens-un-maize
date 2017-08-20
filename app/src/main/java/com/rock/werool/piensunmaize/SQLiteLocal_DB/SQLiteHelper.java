package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017.08.20.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "milkNBread";

    public String getDatabaseName(){
        return DATABASE_NAME;
    }

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StoreContract.CREATE_TABLE_STORES);
        db.execSQL(ProductContract.CREATE_TABLE_PRODUCTS);
        db.execSQL(BarcodeContract.CREATE_TABLE_BARCODES);
        db.execSQL(StoreProductPriceContract.CREATE_TABLE_STOREPRODUCTPRICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StoreContract.DELETE_TABLE_STORES);
        db.execSQL(ProductContract.DELETE_TABLE_PRODUCTS);
        db.execSQL(BarcodeContract.DELETE_TABLE_BARCODES);
        db.execSQL(StoreProductPriceContract.DELETE_TABLE_STOREPRODUCTPRICE);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

}

