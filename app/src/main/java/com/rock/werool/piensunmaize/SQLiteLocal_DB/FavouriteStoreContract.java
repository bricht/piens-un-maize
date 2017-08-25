package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Artūrs on 2017.08.22.
 */

/**
 * Defines table structure for favorite products table.
 */
public final class FavouriteStoreContract {
    /**
     * private constructor.
     */
    private FavouriteStoreContract(){}
    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "FavouriteStores";
    /**
     * Reference to primary key column name.
     */
    private static final String COLUMN_PK = "_primaryKey";
    /**
     * Reference to store ID column name.
     */
    public static final String COLUMN_STORE_ID = "storeId";
    /**
     * Reference to store name column name.
     */
    public static final String COLUMN_STORE_NAME = "storeName";
    /**
     * Reference to store address column name.
     */
    public static final String COLUMN_STORE_ADDRESS = "storeAddress";
    /**
     * Reference to SQL query that creates table structure defined in this class.
     */
    public static final String CREATE_TABLE_FAVOURITESTORES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_STORE_ID + " INTEGER, " +
                    COLUMN_STORE_NAME + " TEXT, " +
                    COLUMN_STORE_ADDRESS + " TEXT, " +
                    "UNIQUE(" + COLUMN_STORE_NAME + ", " + COLUMN_STORE_ADDRESS + "))";;
    /**
     * Reference to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_FAVOURITESTORES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


}

