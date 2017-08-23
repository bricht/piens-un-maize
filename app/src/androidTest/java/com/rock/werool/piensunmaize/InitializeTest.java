package com.rock.werool.piensunmaize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ernes on 8/23/2017.
 */

public class InitializeTest {

    private Context context;
    private SQLiteHelper helper;
    private SQLiteDatabase database;

    @Before
    public void setup(){
    }

    @After
    public void tearDown(){
        context.deleteDatabase(helper.getDatabaseName());
    }

    @Test
    public void createDbTest(){
        context = InstrumentationRegistry.getTargetContext();
        helper = new SQLiteHelper(context);         // apmeram seit tiek izsaukta SQLiteHelper metode onCreate no kuras palaizas DbInitialize
        database = helper.getWritableDatabase();
        assertNotNull(helper);
        try {
            Thread.sleep(25000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
