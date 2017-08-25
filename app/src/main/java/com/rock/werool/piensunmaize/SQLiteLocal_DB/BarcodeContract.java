package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Erensts on 2017.08.20.
 */

/**
 * Defines table structure for barcode table.
 */
public final class BarcodeContract {
    /**
     * Private constructor.
     */
    private BarcodeContract(){}

    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "barcodes";
    /**
     * Reference to primary key column name
     */
    private static final String COLUMN_PK = "_primaryKey";
    /**
     * Reference to barcode value column name.
     */
    public static final String COLUMN_BARCODE = "barcode";
    /**
     * Reference to product ID column name.
     */
    public static final String COLUMN_PRODUCT_ID = "prodId";
    /**
     * Reference to SQL query that creates table structure defined in this class.
     */
    public static final String CREATE_TABLE_BARCODES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_BARCODE + " TEXT, " +
                    COLUMN_PRODUCT_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + ProductContract.TABLE_NAME + "(" + ProductContract.COLUMN_PRODUCT_ID + ") )";
    /**
     * Reference to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_BARCODES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
