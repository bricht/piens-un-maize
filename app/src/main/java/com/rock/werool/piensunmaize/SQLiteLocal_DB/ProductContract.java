package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.20.
 */

public class ProductContract {
    private ProductContract(){}

    public static final String TABLE_NAME = "products";
    private static final String COLUMN_PK = "_primaryKey";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_CATEGORY = "productCategory";

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    COLUMN_PRODUCT_NAME + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    "UNIQUE(" + COLUMN_PRODUCT_NAME + "))";

    public static final String DELETE_TABLE_PRODUCTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
