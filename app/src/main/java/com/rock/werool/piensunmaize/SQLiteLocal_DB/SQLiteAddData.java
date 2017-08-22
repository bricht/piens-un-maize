
package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.text.StringPrepParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class SQLiteAddData extends IntentService {

    // Japadod intent ar astoniem laukiem
    // Key SQLiteAddData.ADD_TYPE           Value SQLiteADD.ADD_BARCODE/ADD_PRODUCT/ADD_STORE/ADD_STORE_PRODUCT_PRICE
    // Key SQLiteAddData.BARCODE            Value String string or null
    // Key SQLiteAddData.PRODUCT_NAME       Value String string or null
    // Key SQLiteAddData.CATEGORY           Value String string or null
    // Key SQLiteAddData.PRICE              Value String string or null
    // Key SQLiteAddData.UPDATE             Value String string or null
    // Key SQLiteAddData.STORE_NAME         Value String string or null
    // Key SQLiteAddData.STORE_ADDRESS      Value String string or null

    public static final String ADD_TYPE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.TYPE";
    public static final int ADD_BARCODE = 1;
    public static final int ADD_PRODUCT = 2;
    public static final int ADD_STORE = 3;
    public static final int ADD_STORE_PRODUCT_PRICE = 4;

    public static final String BARCODE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
    public static final String PRODUCT_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT_NAME_DATA";
    public static final String CATEGORY = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.CATEGORY_DATA";
    public static final String PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRICE_DATA";
    public static final String STORE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_NAME_DATA";
    public static final String STORE_ADDRESS = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_ADDRESS_DATA";

    private static String date;
    private static String query = null;

    private SQLiteHelper helper;
    private static SQLiteDatabase database;


    public SQLiteAddData(){
        super(SQLiteAddData.class.getName());
    }
    public SQLiteAddData(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
        Date currentTime = Calendar.getInstance().getTime();
        date = currentTime.toString();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int i = intent.getIntExtra(ADD_TYPE, 0);
        Intent reply = new Intent("INSERT_RESULT");

        String barcode = intent.getStringExtra(BARCODE);
        String productName = intent.getStringExtra(PRODUCT_NAME);
        String category = intent.getStringExtra(CATEGORY);
        float price = intent.getFloatExtra(PRICE, 0);
        String storeName = intent.getStringExtra(STORE_NAME);
        String storeAddress = intent.getStringExtra(STORE_ADDRESS);

        switch (i) {
            case 1: i = 1;
                if(barcode == null || productName == null){
                    break;
                }
                insertBarcode(barcode, productName);
                break;

            case 2: i = 2;
                if(productName == null || category == null ){
                    break;
                }
                insertProduct(0, productName, category);
                break;

            case 3: i = 3;
                if(storeName == null || storeAddress == null){
                    break;
                }
                insertStore(0, storeName, storeAddress);
                break;

            case 4: i = 4;
                break;

            case 5: i = 0;
                if(productName == null || storeName == null || storeAddress == null || price == 0){
                    break;
                }
                insertPrice(productName, storeName, storeAddress, price);
                break;

        }

    }

   public static void insertBarcode(String code, String name){
       int productId;
       Cursor cursor;

       cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                       " FROM " + ProductContract.TABLE_NAME +
                       " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = " + name,
                        null);
       productId = cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));

       query = "INSERT INTO " + BarcodeContract.TABLE_NAME +
               " (" + BarcodeContract.COLUMN_BARCODE + ", " + BarcodeContract.COLUMN_PRODUCT_ID + ")" +
               " VALUES (" + code + ", " + productId + ")";

       database.rawQuery(query, null);
   }

   // Insert new product. If id is unknown or new product, int should be 0. If product name already exists, no insertion will be made - table requires unique product name.
   public static void insertProduct(int id, String name, String cat){
       int productId;
       if(id == 0){
           Cursor cursor = database.rawQuery("SELECT COALESCE(MAX(" + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID + "), 0) + 1 FROM " + ProductContract.TABLE_NAME, null);
           productId = cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));
           cursor.close();
       }else{
           productId = id;
       }

       query = "INSERT INTO " + ProductContract.TABLE_NAME +
               " (" + ProductContract.COLUMN_PRODUCT_ID + ", " + ProductContract.COLUMN_PRODUCT_NAME + ", " + ProductContract.COLUMN_CATEGORY + ")" +
               " VALUES (" + productId + ", " + name + ", " + cat + ")";

       database.rawQuery(query, null);
   }

    // Insert new store. If id is unknown or new store, int should be 0. If store name already exists, no insertion will be made - table requires unique store.
    public static void insertStore(int id, String name, String address){
        int storeId;
        if(id == 0){
            Cursor cursor = database.rawQuery("SELECT COALESCE(MAX(" + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID + "), 0) + 1 FROM " + StoreContract.TABLE_NAME, null);
            storeId = cursor.getInt(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));
            cursor.close();
        }else{
            storeId = id;
        }

        query = "INSERT INTO " + StoreContract.TABLE_NAME +
                " (" + StoreContract.COLUMN_STORE_ID + ", " + StoreContract.COLUMN_STORE_NAME + ", " + StoreContract.COLUMN_STORE_ADDRESS + ")" +
                " VALUES (" + storeId + ", " + name + ", " + address + ")";

        database.rawQuery(query, null);
   }

    public static void insertPrice(String pName, String sName, String address, double price){
        int productId;
        int storeId;
        Cursor cursor;

        cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                " FROM " + ProductContract.TABLE_NAME +
                " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = " + pName,
                null);
        productId = cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));

        cursor = database.rawQuery("SELECT " + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID +
                " FROM " + StoreContract.TABLE_NAME +
                " WHERE " + StoreContract.COLUMN_STORE_NAME + " = " + sName +
                " AND " + StoreContract.COLUMN_STORE_ADDRESS + " = " + address,
                null);
        storeId = cursor.getInt(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));

        query = "INSERT INTO " + StoreProductPriceContract.TABLE_NAME +
                " (" + StoreProductPriceContract.COLUMN_PRICE + ", " + StoreProductPriceContract.COLUMN_UPDATE + ", " + StoreProductPriceContract.COLUMN_PRODUCT_ID + ", " + StoreProductPriceContract.COLUMN_STORE_ID + ")" +
                " VALUES (" + price + ", " + date + ", " + productId + ", " + storeId + ")";

        database.rawQuery(query, null);
   }

   public static void updatePrice(String pName, String sName, String address, double price){
       int productId;
       int storeId;
       Cursor cursor;

       cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                       " FROM " + ProductContract.TABLE_NAME +
                       " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = " + pName,
               null);
       productId = cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));

       cursor = database.rawQuery("SELECT " + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID +
                       " FROM " + StoreContract.TABLE_NAME +
                       " WHERE " + StoreContract.COLUMN_STORE_NAME + " = " + sName +
                       " AND " + StoreContract.COLUMN_STORE_ADDRESS + " = " + address,
               null);
       storeId = cursor.getInt(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));

       query = "UPDATE " + StoreProductPriceContract.TABLE_NAME +
               " SET " + StoreProductPriceContract.COLUMN_PRICE + " = " + price + ", " + StoreProductPriceContract.COLUMN_UPDATE + " = " + date +
               " WHERE " + StoreProductPriceContract.COLUMN_PRODUCT_ID + " = " + productId + " AND " + StoreProductPriceContract.COLUMN_STORE_ID + " = " + storeId;

       database.rawQuery(query, null);
   }

}
