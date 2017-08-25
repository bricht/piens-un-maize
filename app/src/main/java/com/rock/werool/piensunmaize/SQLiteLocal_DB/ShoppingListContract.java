package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Artūrs on 2017.08.22.
 */

/**
 * Defines table structure for favorite products table.
 */

public final class ShoppingListContract {
    /**
     * private constructor.
     */
    private ShoppingListContract(){}
    /**
     * Reference to table name.
     */
    public static final String TABLE_NAME = "shopping_list";
    /**
     * Reference to primary key column name.
     */
    public static final String COLUMN_PK = "_primaryKey";
    /**
     * Reference to shoping list name column name.
     */
    public static final String COLUMN_SHOPPING_LIST_NAME = "shoppingListName";
    /**
     * Reference to shoping list product price column name.
     */
    public static final String COLUMN_SHOPPING_LIST_PRICE = "shoppingListPrice";
    /**
     * Reference to SQL query that creates table structure defined in this class.
     */
    public static final String CREATE_TABLE_SHOPPING_LIST =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_SHOPPING_LIST_NAME + " TEXT, " +
                    COLUMN_SHOPPING_LIST_PRICE + " REAL)" ;
    /**
     * Reference to SQL query that deletes table who's structure is defined in this class.
     */
    public static final String DELETE_TABLE_SHOPPING_LIST =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
