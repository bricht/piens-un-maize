package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Jaco on 2017.08.22.
 */

public final class FavouriteStoreContract {
    private FavouriteStoreContract(){}

    public static final String TABLE_NAME = "FavouriteStores";
    private static final String COLUMN_PK = "_primaryKey";
    public static final String COLUMN_STORE_ID = "storeId";
    public static final String COLUMN_STORE_NAME = "storeName";
    public static final String COLUMN_STORE_ADDRESS = "storeAddress";

    public static final String CREATE_TABLE_FAVOURITESTORES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_STORE_ID + " INTEGER, " +
                    COLUMN_STORE_NAME + " TEXT, " +
                    COLUMN_STORE_ADDRESS + " TEXT, " +
                    "UNIQUE(" + COLUMN_STORE_NAME + ", " + COLUMN_STORE_ADDRESS + "))";;

    public static final String DELETE_TABLE_FAVOURITESTORES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}

