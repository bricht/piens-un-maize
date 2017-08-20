package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.20.
 */

public class ProductContract {
    private ProductContract(){}

    public static final String TABLE_NAME = "products";
    private static final String COLUMN_PK = "_primary_key";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_AVG_PRICE = "price";
    public static final String COLUMN_CATEGORY = "category";

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT_ID + " INTEGER AUTOINCREMENT, " +
                    COLUMN_PRODUCT_NAME + " TEXT, " +
                    COLUMN_AVG_PRICE + " REAL, " +
                    COLUMN_CATEGORY + " TEXT)";

    public static final String DELETE_TABLE_PRODUCTS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
