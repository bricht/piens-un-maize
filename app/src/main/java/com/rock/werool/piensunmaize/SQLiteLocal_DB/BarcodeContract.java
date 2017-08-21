package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by user on 2017.08.20.
 */

public class BarcodeContract {
    private BarcodeContract(){}

    public static final String TABLE_NAME = "barcodes";
    private static final String COLUMN_PK = "_primaryKey";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMN_PRODUCT_ID = "prod_id";

    public static final String CREATE_TABLE_BARCODES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_BARCODE + " TEXT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + ProductContract.TABLE_NAME + "(" + ProductContract.COLUMN_PRODUCT_ID + ") )";

    public static final String DELETE_TABLE_BARCODES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
