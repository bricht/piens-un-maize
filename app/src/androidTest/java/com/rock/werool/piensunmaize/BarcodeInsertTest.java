package com.rock.werool.piensunmaize;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.BarcodeContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteHelper;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
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

public class BarcodeInsertTest {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;
    SQLiteAddData add;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        db = new SQLiteHelper(context);
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
    public void testBarcodeInsertion() {

        String s = null;

        Intent intent = new Intent(context, SQLiteAddData.class);
        intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_BARCODE);
        intent.putExtra(SQLiteAddData.BARCODE, "baltmaize");
        intent.putExtra(SQLiteAddData.PRODUCT_NAME, s);
        intent.putExtra(SQLiteAddData.PRODUCT_ID, (long)6 );

        ComponentName cn = context.startService(intent);
        String classname = cn.getShortClassName();
        assertEquals("Expected class name .SQLiteLocal_DB.SQLiteAddData, but was: " + classname, classname, new String(".SQLiteLocal_DB.SQLiteAddData"));

        database = db.getReadableDatabase();
        assertNotNull(database);

        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){

        }

        Cursor cursor = database.rawQuery("SELECT " + BarcodeContract.COLUMN_BARCODE + ", " + BarcodeContract.COLUMN_PRODUCT_ID +  " FROM " + BarcodeContract.TABLE_NAME, null);
        assertNotNull(cursor);

        int i = cursor.getCount();
        assertTrue("Number of rows expected to be 1, but was: " + i, i==1);

        String str = null;
        if(cursor.moveToFirst()){
            str = cursor.getString(cursor.getColumnIndex(BarcodeContract.COLUMN_BARCODE)) + ", " +
                    cursor.getString(cursor.getColumnIndex(BarcodeContract.COLUMN_PRODUCT_ID));
        } else{
            fail("No data!");
        }
        cursor.close();
        assertTrue("Expected 12345555, 6, but was: " + str, str.equals("12345555, 6"));

    }

}







