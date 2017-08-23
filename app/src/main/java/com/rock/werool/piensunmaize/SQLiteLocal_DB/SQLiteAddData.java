package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.text.StringPrepParseException;

import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.Store;

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
    public static final int UPDATE_STORE_PRODUCT_PRICE = 5;

    public static final String BARCODE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
    public static final String PRODUCT_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT_NAME_DATA";
    public static final String PRODUCT_ID = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT_ID_DATA";
    public static final String CATEGORY = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.CATEGORY_DATA";
    public static final String PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRICE_DATA";
    public static final String STORE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_NAME_DATA";
    public static final String STORE_ADDRESS = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_ADDRESS_DATA";
    public static final String STORE_ID = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_ID_DATA";
    public static final String NEW = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.NEW_DATA";;

    private String date;
    private String query = null;

    private SQLiteHelper helper;
    private SQLiteDatabase database;

    private RemoteDatabase remoteDatabase;


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
        remoteDatabase = new RemoteDatabase("http://zsloka.tk/piens_un_maize_db/", getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int i = intent.getIntExtra(ADD_TYPE, 0);

        String barcode = intent.getStringExtra(BARCODE);
        String productName = intent.getStringExtra(PRODUCT_NAME);
        String category = intent.getStringExtra(CATEGORY);
        double price = intent.getDoubleExtra(PRICE, 0.0);
        String storeName = intent.getStringExtra(STORE_NAME);
        String storeAddress = intent.getStringExtra(STORE_ADDRESS);
        long pId = intent.getLongExtra(PRODUCT_ID, 0);
        long sId = intent.getLongExtra(STORE_ID, 0);
        boolean bool = intent.getBooleanExtra(NEW, false);

        switch (i) {
            case 1: i = 1;
                if(barcode == null){
                    break;
                }
                insertBarcode(barcode, productName, pId, bool);
                break;

            case 2: i = 2;
                if(productName == null || category == null ){
                    break;
                }
                insertProduct(pId, productName, category, bool);
                break;

            case 3: i = 3;
                if(storeName == null || storeAddress == null){
                    break;
                }
                insertStore(sId, storeName, storeAddress, bool);
                break;

            case 4: i = 4;
                if(productName == null || storeName == null || storeAddress == null || price == 0){
                    break;
                }
                insertPrice(productName, storeName, storeAddress, price, bool);
                break;

            case 5: i = 5;
                if(productName == null || storeName == null || storeAddress == null || price == 0){
                    break;
                }
                updatePrice(productName, storeName, storeAddress, price);
                break;

            case 6: i = 0;

                break;

        }

    }

    // Insert new barcode. name or id must be supplied, if both are supplied, only name will be used and id will be searched un db.
   private boolean insertBarcode(String code, String name, long id, boolean bool){
       long productId;
       Cursor cursor;

       if(name == null){
           productId = id;
       }else {
           cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                           " FROM " + ProductContract.TABLE_NAME +
                           " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = '" + name + "'",
                   null);
           cursor.moveToFirst();
           productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));
       }

       ContentValues values = new ContentValues();
       values.put(BarcodeContract.COLUMN_BARCODE, code);
       values.put(BarcodeContract.COLUMN_PRODUCT_ID, productId);
       long newRowId = database.insert(BarcodeContract.TABLE_NAME, null, values);

       if(bool == true){
           //ievietot onlaina ari
       }

       if(newRowId > 0){
           return true;
       }else{
           return false;
       }
   }

   // Insert new product. If id is unknown or new product, int should be 0. If product name already exists, no insertion will be made - table requires unique product name.
   private boolean insertProduct(long id, String name, String cat, boolean bool){
       long productId;
       if(id == 0){
           Cursor cursor = database.rawQuery("SELECT COALESCE(MAX(" + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID + "), 0) + 1 FROM " + ProductContract.TABLE_NAME, null);
           productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));
           cursor.moveToFirst();
           cursor.close();
       }else{
           productId = id;
       }

       ContentValues values = new ContentValues();
       values.put(ProductContract.COLUMN_PRODUCT_ID, productId);
       values.put(ProductContract.COLUMN_PRODUCT_NAME, name);
       values.put(ProductContract.COLUMN_CATEGORY, cat);
       long newRowId = database.insert(ProductContract.TABLE_NAME, null, values);

       if(bool == true){
           //ievietot onlaina ari
       }

       if(newRowId > 0){
           return true;
       }else{
           return false;
       }

   }

    // Insert new store. If id is unknown or new store, int should be 0. If store name already exists, no insertion will be made - table requires unique store.
    private boolean insertStore(long id, String name, String address, boolean bool){
        long storeId;
//        if(id == 0){
//            Cursor cursor = database.rawQuery("SELECT COALESCE(MAX(" + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID + "), 0) + 1 FROM " + StoreContract.TABLE_NAME, null);
//            storeId = cursor.getLong(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));
//            cursor.moveToFirst();
//            cursor.close();
//        }else{
            storeId = id;
//        }

        ContentValues values = new ContentValues();
        values.put(StoreContract.COLUMN_STORE_ID, storeId);
        values.put(StoreContract.COLUMN_STORE_NAME, name);
        values.put(StoreContract.COLUMN_STORE_ADDRESS, address);
        long newRowId = database.insert(StoreContract.TABLE_NAME, null, values);

        if(bool == true){
            //ievietot onlaina ari
        }

        if(newRowId > 0){
            return true;
        }else{
            return false;
        }

   }

    private boolean insertPrice(String pName, String sName, String address, double price, boolean bool){
        long productId;
        long storeId;
        Cursor cursor;

        cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                " FROM " + ProductContract.TABLE_NAME +
                " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = '" + pName + "'",
                null);
        cursor.moveToFirst();
        productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));
        cursor.close();

        cursor = database.rawQuery("SELECT " + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID +
                " FROM " + StoreContract.TABLE_NAME +
                " WHERE " + StoreContract.COLUMN_STORE_NAME + " = '" + sName + "'" +
                " AND " + StoreContract.COLUMN_STORE_ADDRESS + " = '" + address + "'",
                null);
        cursor.moveToFirst();
        storeId = cursor.getLong(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(StoreProductPriceContract.COLUMN_PRICE, price);
        values.put(StoreProductPriceContract.COLUMN_UPDATE, date);
        values.put(StoreProductPriceContract.COLUMN_PRODUCT_ID, productId);
        values.put(StoreProductPriceContract.COLUMN_STORE_ID, storeId);
        long newRowId = database.insert(StoreProductPriceContract.TABLE_NAME, null, values);

        if(bool == true){
            //ievietot onlaina ari
        }

        if(newRowId > 0){
            return true;
        }else{
            return false;
        }
   }

   private boolean updatePrice(String pName, String sName, String address, double price){
       long productId;
       long storeId;
       Cursor cursor;

       cursor = database.rawQuery("SELECT " + ProductContract.TABLE_NAME + "." + ProductContract.COLUMN_PRODUCT_ID +
                       " FROM " + ProductContract.TABLE_NAME +
                       " WHERE " + ProductContract.COLUMN_PRODUCT_NAME + " = " + pName,
               null);
       productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_ID));

       cursor = database.rawQuery("SELECT " + StoreContract.TABLE_NAME + "." + StoreContract.COLUMN_STORE_ID +
                       " FROM " + StoreContract.TABLE_NAME +
                       " WHERE " + StoreContract.COLUMN_STORE_NAME + " = " + sName +
                       " AND " + StoreContract.COLUMN_STORE_ADDRESS + " = " + address,
               null);
       storeId = cursor.getLong(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ID));

       ContentValues values = new ContentValues();
       values.put(StoreProductPriceContract.COLUMN_PRICE, price);
       values.put(StoreProductPriceContract.COLUMN_UPDATE, date);
       String where = "WHERE " + StoreProductPriceContract.COLUMN_PRODUCT_ID + " = " + productId + " AND " + StoreProductPriceContract.COLUMN_STORE_ID + " = " + storeId;
       int newRowId = database.update(ProductContract.TABLE_NAME, values, where, null);

       if(newRowId > 0){
           return true;
       }else{
           return false;
       }
   }

}


