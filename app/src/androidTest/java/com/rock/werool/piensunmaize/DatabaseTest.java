package com.rock.werool.piensunmaize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteHelper;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by user on 2017.08.16.
 */

@RunWith(AndroidJUnit4.class)

public class DatabaseTest {

    private Context context;
    private SQLiteHelper db;
    private SQLiteDatabase database;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown(){
        context.deleteDatabase(db.getDatabaseName());
    }

    @Test
    public void createDbTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new SQLiteHelper(appContext);
        assertNotNull(db);
    }

    @Test
    public void testProductInsertion(){
        String name = "banana";
        String category = "fruits";

        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new SQLiteHelper(appContext);
        database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.COLUMN_PRODUCT_NAME, name);
        values.put(ProductContract.COLUMN_CATEGORY, category);
        long newRowId = database.insert(ProductContract.TABLE_NAME, null, values);
        long unexpectedLong = -1;
        long expectedLong = 1;
        assertNotEquals(unexpectedLong, newRowId);
        assertEquals(expectedLong, newRowId);
    }

    @Test
    public void testProductRead(){

        String name = "banana";
        String category = "fruits";

        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new SQLiteHelper(appContext);
        database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.COLUMN_PRODUCT_NAME, name);
        values.put(ProductContract.COLUMN_CATEGORY, category);
        long newRowId = database.insert(ProductContract.TABLE_NAME, null, values);

        Cursor cursor = database.rawQuery("SELECT " + ProductContract.COLUMN_PRODUCT_NAME + ", " + ProductContract.COLUMN_CATEGORY +  " FROM " + ProductContract.TABLE_NAME, null);
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
        assertTrue("Expected banana, fruits gatve but was: " + str, str.equals("banana, fruits"));

    }






}