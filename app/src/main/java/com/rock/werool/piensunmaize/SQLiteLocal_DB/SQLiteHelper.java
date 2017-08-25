package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ernests on 2017.08.20.
 */

/**
 * When created, this class object can use inherited methods getReadableDatabase and getWritableDatabase to initialize SQLiteDatabase object with read or write rights.
 * Class onCreate, onUpgrade and onDowngrade methods provide way of creating, upgrading and downgrading database. onCreate method is called automatically when database is
 * first initialized and normally should not bet used.
 */

public final class SQLiteHelper extends SQLiteOpenHelper{
    /**
     * Contains integer value representing database version.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Contains database name.
     */
    private static final String DATABASE_NAME = "milkNBread";
    /**
     * Private application context object.
     */
    private Context context;
    /**
     * eturns database name.
     * @return
     */
    public String getDatabaseName(){
        return DATABASE_NAME;
    }
    /**
     * Public constructor.
     * @param context
     */
    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Executes code in it's body that creates database structure and puts initial data in it.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StoreContract.CREATE_TABLE_STORES);
        db.execSQL(ProductContract.CREATE_TABLE_PRODUCTS);
        db.execSQL(BarcodeContract.CREATE_TABLE_BARCODES);
        db.execSQL(StoreProductPriceContract.CREATE_TABLE_STOREPRODUCTPRICE);
        db.execSQL(ShoppingListContract.CREATE_TABLE_SHOPPING_LIST);
        db.execSQL(FavouriteProductContract.CREATE_TABLE_FAVOURITEPRODUCTS);
		db.execSQL(FavouriteStoreContract.CREATE_TABLE_FAVOURITESTORES);
        Intent intent = new Intent(context, DbInitialize.class);
        context.startService(intent);
    }

    /**
     * Executes code in it's body that updates database.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProductContract.DELETE_TABLE_PRODUCTS);
        db.execSQL(StoreContract.DELETE_TABLE_STORES);
        db.execSQL(BarcodeContract.DELETE_TABLE_BARCODES);
        db.execSQL(StoreProductPriceContract.DELETE_TABLE_STOREPRODUCTPRICE);
        db.execSQL(ShoppingListContract.DELETE_TABLE_SHOPPING_LIST);
        db.execSQL(FavouriteProductContract.DELETE_TABLE_FAVOURITEPRODUCTS);
		db.execSQL(FavouriteStoreContract.DELETE_TABLE_FAVOURITESTORES);

        onCreate(db);
    }

    /**
     * Executes code in it's body that downgrades database.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

}

