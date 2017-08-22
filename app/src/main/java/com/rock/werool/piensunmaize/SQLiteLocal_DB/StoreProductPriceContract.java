package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.graphics.PorterDuff;

/**
 * Created by user on 2017.08.20.
 */

public class StoreProductPriceContract {
    private StoreProductPriceContract(){}

    public static final String TABLE_NAME = "storeProductPrice";
    private static final String COLUMN_PK = "_primary_key";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_UPDATE = "lastUpdate";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_STORE_ID = "storeId";

    public static final String CREATE_TABLE_STOREPRODUCTPRICE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_UPDATE + " TEXT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_STORE_ID + " INTEGER)";
//                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + ProductContract.TABLE_NAME + "(" + ProductContract.COLUMN_PRODUCT_ID + ") " +
//                    "FOREIGN KEY (" + COLUMN_STORE_ID + ") REFERENCES " + StoreContract.TABLE_NAME + "(" + StoreContract.COLUMN_STORE_ID + ") )";

    public static final String DELETE_TABLE_STOREPRODUCTPRICE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
