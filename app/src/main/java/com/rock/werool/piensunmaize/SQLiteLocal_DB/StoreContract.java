package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.15.
 */

public final class StoreContract {
    private StoreContract(){}

    public static final String TABLE_NAME = "shops";
    private static final String COLUMN_PK = "_primary_key";
    public static final String COLUMN_STORE_ID = "id";
    public static final String COLUMN_STORE_NAME = "name";
    public static final String COLUMN_STORE_ADDRESS = "address";

    public static final String CREATE_TABLE_STORES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STORE_ID + " INTEGER AUTOINCREMENT, " +
                    COLUMN_STORE_NAME + " TEXT, " +
                    COLUMN_STORE_ADDRESS + " TEXT)";

    public static final String DELETE_TABLE_STORES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}

