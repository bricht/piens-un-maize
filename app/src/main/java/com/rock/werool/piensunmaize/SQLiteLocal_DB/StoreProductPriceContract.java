package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.graphics.PorterDuff;

/**
 * Created by Ernests on 2017.08.20.
 */

/**
 * Defines table structure for store product price table.
 */

public final class StoreProductPriceContract {
    /**
     * private constructor.
     */
    private StoreProductPriceContract(){}
    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "storeProductPrice";
    /**
     * Reference to primary key column name.
     */
    private static final String COLUMN_PK = "_primary_key";
    /**
     * Reference to price column name.
     */
    public static final String COLUMN_PRICE = "price";
    /**
     * Reference to last time price was updated column name.
     */
    public static final String COLUMN_UPDATE = "lastUpdate";
    /**
     * Reference to product ID this price is referencing column name
     */
    public static final String COLUMN_PRODUCT_ID = "productId";
    /**
     * Reference to product ID this price is referencing column name
     */
    public static final String COLUMN_STORE_ID = "storeId";
    /**
     * Reference to SQL query that creates table structure defined in this class.
     */
    public static final String CREATE_TABLE_STOREPRODUCTPRICE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_UPDATE + " TEXT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_STORE_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + ProductContract.TABLE_NAME + "(" + ProductContract.COLUMN_PRODUCT_ID + ") " +
                    "FOREIGN KEY (" + COLUMN_STORE_ID + ") REFERENCES " + StoreContract.TABLE_NAME + "(" + StoreContract.COLUMN_STORE_ID + ") )";
    /**
     * Reference to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_STOREPRODUCTPRICE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
