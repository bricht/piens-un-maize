package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Ernests on 2017.08.20.
 */

/**
 * Defines table structure for product table.
 */

public final class ProductContract {
    /**
     * private constructor.
     */
    private ProductContract(){}
    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "products";
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

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_PRODUCT_NAME + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT)";
                   // "UNIQUE(" + COLUMN_PRODUCT_NAME + "))";
    /**
     * Referenc to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_PRODUCTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
