package com.rock.werool.piensunmaize.SQLiteLocal_DB;

/**
 * Created by Roberts on 8/22/2017.
 */

public class ShoppingListContract {
    private ShoppingListContract(){}

    public static final String TABLE_NAME = "shopping_list";
    private static final String COLUMN_PK = "_primaryKey";
    public static final String COLUMN_SHOPPING_LIST_ID = "shoppingListId";
    public static final String COLUMN_SHOPPING_LIST_NAME = "shoppingListName";
    public static final String COLUMN_SHOPPING_LIST_PRICE = "shoppingListPrice";

    public static final String CREATE_TABLE_SHOPPING_LIST =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PK + " INTEGER PRIMARY KEY, " +
                    COLUMN_SHOPPING_LIST_ID + " INTEGER, " +
                    COLUMN_SHOPPING_LIST_NAME + " TEXT, " +
                    COLUMN_SHOPPING_LIST_PRICE + " FLOAT, " +
                    "UNIQUE(" + COLUMN_SHOPPING_LIST_NAME + "))";;

    public static final String DELETE_TABLE_SHOPPING_LIST =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
