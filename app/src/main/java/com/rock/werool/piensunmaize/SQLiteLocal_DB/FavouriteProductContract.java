package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Artūrs  on 2017.08.22.
 */

/**
 * Defines table structure for favorite products table.
 */

public final class FavouriteProductContract {
    /**
     * private constructor.
     */
    private FavouriteProductContract(){}
    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "favouriteProducts";
    /**
     * Reference to primary key column name.
     */
    private static final String COLUMN_PK = "_primaryKey";
    /**
     * Reference to product ID column name.
     */
    public static final String COLUMN_PRODUCT_ID = "productId";
    /**
     * Reference to product name column name.
     */
    public static final String COLUMN_PRODUCT_NAME = "productName";
    /**
     * Referenc to product category column name.
     */
    public static final String COLUMN_CATEGORY = "productCategory";
    /**
     * Referenc to SQL query that creates table structure defined in this class.
     */
    public static final String CREATE_TABLE_FAVOURITEPRODUCTS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_PRODUCT_NAME + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    "UNIQUE(" + COLUMN_PRODUCT_NAME + "))";
    /**
     * Referenc to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_FAVOURITEPRODUCTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
