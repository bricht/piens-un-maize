//package com.rock.werool.piensunmaize.SQLiteLocal_DB;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.icu.text.SimpleDateFormat;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//
//public class SQLiteAddData extends IntentService {
//
//    // Japadod intent ar astoniem laukiem
//    // Key SQLiteAddData.ADD_TYPE           Value SQLiteADD.ADD_BARCODE/ADD_PRODUCT/ADD_STORE/ADD_STORE_PRODUCT_PRICE
//    // Key SQLiteAddData.BARCODE            Value String string or null
//    // Key SQLiteAddData.PRODUCT_NAME       Value String string or null
//    // Key SQLiteAddData.CATEGORY           Value String string or null
//    // Key SQLiteAddData.PRICE              Value String string or null
//    // Key SQLiteAddData.UPDATE             Value String string or null
//    // Key SQLiteAddData.STORE_NAME         Value String string or null
//    // Key SQLiteAddData.STORE_ADDRESS      Value String string or null
//
//    public static final String ADD_TYPE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.TYPE";
//    public static final int ADD_BARCODE = 1;
//    public static final int ADD_PRODUCT = 2;
//    public static final int ADD_STORE = 3;
//    public static final int ADD_STORE_PRODUCT_PRICE = 4;
//
//    public static final String BARCODE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
//    public static final String PRODUCT_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRODUCT_NAME_DATA";
//    public static final String CATEGORY = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.CATEGORY_DATA";
//    public static final String PRICE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.PRICE_DATA";
//    public static final String UPDATE = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.BARCODE_DATA";
//    public static final String STORE_NAME = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_NAME_DATA";
//    public static final String STORE_ADDRESS = "com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData.STORE_ADDRESS_DATA";
//
//    private String date;
//
//    private SQLiteHelper helper;
//    private SQLiteDatabase database;
//
//
//    public SQLiteAddData(){
//        super(SQLiteAddData.class.getName());
//    }
//    public SQLiteAddData(String name) {
//        super(name);
//    }
//
//    @Override
//    public void onCreate(){
//        super.onCreate();
//        helper = new SQLiteHelper(getApplicationContext());
//        database = helper.getWritableDatabase();
//        Date currentTime = Calendar.getInstance().getTime();
//        date = currentTime.toString();
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        int i = intent.getIntExtra(ADD_TYPE, 0);
//        Intent reply = new Intent("INSERT_RESULT");
//
//        String barcode = intent.getStringExtra(BARCODE);
//        String productName = intent.getStringExtra(PRODUCT_NAME);
//        String address = intent.getStringExtra(SRC_ADDRESS);
//        String query = null;
//
//        if(product == null && store == null && address == null){
//            i = 0;
//        }
//
//        switch (i){
//            case 1: i = 1; // pec produkta nosaukuma atrod videjo produkta cenu
//
//    }
//
//    private void publishResults(Intent intent){
//        intent.setAction("INSERT_RESULT");
//        sendBroadcast(intent);
//    }
//
//}
