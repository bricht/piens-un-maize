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

public class QueryTest {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;

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
    public void testQueryName() {


        // Pievieno produktu
        Intent in1 = new Intent(context, SQLiteAddData.class);
        in1.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_PRODUCT);
        in1.putExtra(SQLiteAddData.PRODUCT_NAME, "banana");
        in1.putExtra(SQLiteAddData.CATEGORY, "fruits");
        in1.putExtra(SQLiteAddData.PRODUCT_ID, (long)123);

        ComponentName cn = context.startService(in1);
        String classname = cn.getShortClassName();
        assertEquals("Expected class name .SQLiteLocal_DB.SQLiteAddData, but was: " + classname, classname, new String(".SQLiteLocal_DB.SQLiteAddData"));

        // Pievieno veikalu
        Intent in2 = new Intent(context, SQLiteAddData.class);
        in2.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE);
        in2.putExtra(SQLiteAddData.STORE_ID, (long)1234);
        in2.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in2.putExtra(SQLiteAddData.STORE_ADDRESS, "Ulmana gatve");

        cn = context.startService(in2);
        classname = cn.getShortClassName();
        assertEquals("Expected class name .SQLiteLocal_DB.SQLiteAddData, but was: " + classname, classname, new String(".SQLiteLocal_DB.SQLiteAddData"));

        try{
            Thread.sleep(2500);
        }catch (InterruptedException e){
        }

        // Pievieno produkta cenu
        Intent intent = new Intent(context, SQLiteAddData.class);
        intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE_PRODUCT_PRICE);
        intent.putExtra(SQLiteAddData.PRODUCT_NAME, "banana");
        intent.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        intent.putExtra(SQLiteAddData.STORE_ADDRESS, "Ulmana gatve");
        intent.putExtra(SQLiteAddData.PRICE, (double)1.4 );

        cn = context.startService(intent);
        classname = cn.getShortClassName();
        assertEquals("Expected class name .SQLiteLocal_DB.SQLiteAddData, but was: " + classname, classname, new String(".SQLiteLocal_DB.SQLiteAddData"));

        try{
            Thread.sleep(2500);
        }catch (InterruptedException e){
        }

        // mekle pievienoto produktu pec nosaukuma
        Intent in3 = new Intent(context, SQLiteQuery.class);
        in3.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);
        in3.putExtra(SQLiteQuery.SRC_NAME, "banana");
        cn = context.startService(in3);
        classname = cn.getShortClassName();
        assertEquals("Expected class name .SQLiteLocal_DB.SQLiteQuery, but was: " + classname, classname, new String(".SQLiteLocal_DB.SQLiteQuery"));

        try{
            Thread.sleep(2500);
        }catch (InterruptedException e){
        }


    }

}








