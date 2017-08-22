package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.15.
 */

public final class StoreContract {
    private StoreContract(){}

    public static final String TABLE_NAME = "stores";
    private static final String COLUMN_PK = "_primaryKey";
    public static final String COLUMN_STORE_ID = "storeId";
    public static final String COLUMN_STORE_NAME = "storeName";
    public static final String COLUMN_STORE_ADDRESS = "storeAddress";

    public static final String CREATE_TABLE_STORES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_STORE_ID + " INTEGER, " +
                    COLUMN_STORE_NAME + " TEXT, " +
                    COLUMN_STORE_ADDRESS + " TEXT, " +
                    "UNIQUE(" + COLUMN_STORE_NAME + ", " + COLUMN_STORE_ADDRESS + "))";;

    public static final String DELETE_TABLE_STORES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}

