package com.rock.werool.piensunmaize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.ProductContract;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteAddData;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteHelper;

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
        SQLiteAddData.insertBarcode("123456789", "banana");
        SQLiteAddData.insertPrice("banana", "Rimi", "Ulmana gatve", 1.4);
    }


}







