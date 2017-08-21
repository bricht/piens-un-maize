package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.graphics.PorterDuff;

/**
 * Created by user on 2017.08.20.
 */

public class StoreProductPriceContract {
    private StoreProductPriceContract(){}

    public static final String TABLE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.storeProductPrice";
    private static final String COLUMN_PK = "_com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.primary_key";
    public static final String COLUMN_PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.price";
    public static final String COLUMN_UPDATE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.update";
    public static final String COLUMN_PRODUCT_ID = "com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.product_id";
    public static final String COLUMN_STORE_ID = "com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract.store_id";

    public static final String CREATE_TABLE_STOREPRODUCTPRICE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_UPDATE + " TEXT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_STORE_ID + " INTEGER), " +
                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + ProductContract.TABLE_NAME + "(" + ProductContract.COLUMN_PRODUCT_ID + ") " +
                    "FOREIGN KEY (" + COLUMN_STORE_ID + ") REFERENCES " + StoreContract.TABLE_NAME + "(" + StoreContract.COLUMN_STORE_ID + ")";

    public static final String DELETE_TABLE_STOREPRODUCTPRICE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
