package com.rock.werool.piensunmaize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.BarcodeContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteHelper;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.StoreProductPriceContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by user on 2017.08.22.
 */

@RunWith(AndroidJUnit4.class)

public class DataTest {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        SQLiteHelper db = new SQLiteHelper(context);
    }

    @After
    public void tearDown() {
        context.deleteDatabase(db.getDatabaseName());
    }

    @Test
    public void createDbTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new SQLiteHelper(appContext);
        assertNotNull(db);
    }

    @Test
    public void testProductInsertion() {

        SQLiteAddData.insertProduct(0, "banana", "fruits");
        SQLiteAddData.insertStore(0, "Rimi", "Ulmana gatve");
        SQLiteAddData.insertBarcode("123456789", "banana", 0);
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);

        Context appContext = InstrumentationRegistry.getTargetContext();
        database = db.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT productName, productCategory FROM products", null);
        int i = cursor.getCount();
        assertTrue("Number of rows expected to be 1, but was: " + i, i==1);
        String str = null;
        if(cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_PRODUCT_NAME)) + ", " +
                    cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_CATEGORY));
        } else{
            fail("No data!");
        }
        cursor.close();
        assertTrue("Expected banana, fruits but was: " + str, str.equals("banana, fruits"));
    }

    @Test
    public void testStoreInsertion() {

        SQLiteAddData.insertProduct(0, "banana", "fruits");
        SQLiteAddData.insertStore(0, "Rimi", "Ulmana gatve");
        SQLiteAddData.insertBarcode("123456789", "banana", 0);
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);

        Context appContext = InstrumentationRegistry.getTargetContext();
        database = db.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT storeName, storeAddress FROM stores", null);
        int i = cursor.getCount();
        assertTrue("Number of rows expected to be 1, but was: " + i, i==1);
        String str = null;
        if(cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex(StoreContract.COLUMN_STORE_NAME)) + ", " +
                    cursor.getString(cursor.getColumnIndex(StoreContract.COLUMN_STORE_ADDRESS));
        } else{
            fail("No data!");
        }
        cursor.close();
        assertTrue("Expected Rimi, Ulmana gatve but was: " + str, str.equals("Rimi, Ulmana gatve"));
    }

    @Test
    public void testBarcodeInsertion() {

        SQLiteAddData.insertProduct(0, "banana", "fruits");
        SQLiteAddData.insertStore(0, "Rimi", "Ulmana gatve");
        SQLiteAddData.insertBarcode("123456789", "banana", 0);
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);

        Context appContext = InstrumentationRegistry.getTargetContext();
        database = db.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT barcode FROM barcodes", null);
        int i = cursor.getCount();
        assertTrue("Number of rows expected to be 1, but was: " + i, i==1);
        String str = null;
        if(cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex(BarcodeContract.COLUMN_BARCODE));
        } else{
            fail("No data!");
        }
        cursor.close();
        assertTrue("Expected123456789 but was: " + str, str.equals("123456789"));
    }

    @Test
    public void testPriceInsertion() {

        SQLiteAddData.insertProduct(0, "banana", "fruits");
        SQLiteAddData.insertStore(0, "Rimi", "Ulmana gatve");
        SQLiteAddData.insertBarcode("123456789", "banana", 0);
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);

        Context appContext = InstrumentationRegistry.getTargetContext();
        database = db.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT price FROM storeProductPrice", null);
        int i = cursor.getCount();
        assertTrue("Number of rows expected to be 1, but was: " + i, i==1);
        String str = null;
        if(cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex(StoreProductPriceContract.COLUMN_PRICE));
        } else{
            fail("No data!");
        }
        cursor.close();
        assertTrue("Expected 1.4 but was: " + str, str.equals("1.4"));
    }



}







