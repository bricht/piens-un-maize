package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.20.
 */

public class ProductContract {
    private ProductContract(){}

    public static final String TABLE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.products";
    private static final String COLUMN_PK = "_com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.primary_key";
    public static final String COLUMN_PRODUCT_ID = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.id";
    public static final String COLUMN_PRODUCT_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.name";
    public static final String COLUMN_AVG_PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.price";
    public static final String COLUMN_CATEGORY = "com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract.category";

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
