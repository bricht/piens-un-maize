package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class SQLiteAddData extends IntentService {

    public static final String ADD_TYPE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.TYPE";
    public static final String ADD_BARCODE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE";
    public static final String ADD_PRODUCT = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT";
    public static final String ADD_STORE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE";
    public static final String ADD_STORE_PRODUCT_PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_PRICE";

    public static final String BARCODE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
    public static final String PRODUCT_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT_NAME_DATA";
    public static final String CATEGORY = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.CATEGORY_DATA";
    public static final String PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRICE_DATA";
    public static final String UPDATE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
    public static final String STORE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_NAME_DATA";
    public static final String STORE_ADDRESS = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_ADDRESS_DATA";

    private String date;


    private SQLiteHelper helper;
    private SQLiteDatabase database;


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
       // date = System.get
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
