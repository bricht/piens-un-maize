package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.remoteDatabase.Barcode;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.Store;
import com.rock.werool.piensunmaize.remoteDatabase.StoreProductPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erensts on 2017.08.20.
 */

/**
 * Service class that should be used only when passing intent to it in SQLiteHelper class onCreate method.
 * It is a service that upon database creation copies data from online database into it.
 * This class onHandleIntent method, that executes data pasting in database can be called when passing explicit intent to it.
 */

public final class DbInitialize extends IntentService {
    private SQLiteHelper helper;
    private SQLiteDatabase database;

    private DbInitialize(){
        super(SQLiteQuery.class.getName());
    }

    private DbInitialize(String name) {
        super(name);
    }

    /**
     * Initializes connection to database.
     */
    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    /**
     * Copies data from online database to local database.
     * @param intent
     */

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // pievieno produktu
        Intent in1 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in1.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_PRODUCT);
        in1.putExtra(SQLiteAddData.PRODUCT_NAME, "banana");
        in1.putExtra(SQLiteAddData.CATEGORY, "fruits");
        in1.putExtra(SQLiteAddData.PRODUCT_ID, (long)123);

        getApplicationContext().startService(in1);

        // pievieno produktu
        Intent in4 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in4.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_PRODUCT);
        in4.putExtra(SQLiteAddData.PRODUCT_NAME, "potata");
        in4.putExtra(SQLiteAddData.CATEGORY, "fruits");
        in4.putExtra(SQLiteAddData.PRODUCT_ID, (long)124);

        getApplicationContext().startService(in4);

        // Pievieno veikalu
        Intent in2 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in2.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE);
        in2.putExtra(SQLiteAddData.STORE_ID, (long)1234);
        in2.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in2.putExtra(SQLiteAddData.STORE_ADDRESS, "Ulmana gatve");

        getApplicationContext().startService(in2);

        // Pievieno veikalu
        Intent in5 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in5.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE);
        in5.putExtra(SQLiteAddData.STORE_ID, (long)1234);
        in5.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in5.putExtra(SQLiteAddData.STORE_ADDRESS, "Silinu gatve");

        getApplicationContext().startService(in5);

        // Pievieno produkta cenu
        Intent in3 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in3.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE_PRODUCT_PRICE);
        in3.putExtra(SQLiteAddData.PRODUCT_NAME, "banana");
        in3.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in3.putExtra(SQLiteAddData.STORE_ADDRESS, "Ulmana gatve");
        in3.putExtra(SQLiteAddData.PRICE, (double)1.4 );

        getApplicationContext().startService(in3);

        // Pievieno produkta cenu
        Intent in6 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in6.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE_PRODUCT_PRICE);
        in6.putExtra(SQLiteAddData.PRODUCT_NAME, "banana");
        in6.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in6.putExtra(SQLiteAddData.STORE_ADDRESS, "Silinu gatve");
        in6.putExtra(SQLiteAddData.PRICE, (double)0.75 );

        getApplicationContext().startService(in6);

        // Pievieno produkta cenu
        Intent in7 = new Intent(getApplicationContext(), SQLiteAddData.class);
        in7.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE_PRODUCT_PRICE);
        in7.putExtra(SQLiteAddData.PRODUCT_NAME, "potata");
        in7.putExtra(SQLiteAddData.STORE_NAME, "Rimi");
        in7.putExtra(SQLiteAddData.STORE_ADDRESS, "Ulmana gatve");
        in7.putExtra(SQLiteAddData.PRICE, (double)0.68 );

        getApplicationContext().startService(in7);





        RemoteDatabase rDb = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());

        rDb.GetAllProducts(new IDatabaseResponseHandler<Product>() {
            @Override
            public void onArrive(ArrayList<Product> data) {
                for(Product p:data){
                    Intent intent = new Intent(getApplicationContext(), SQLiteAddData.class);
                    intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_PRODUCT);
                    intent.putExtra(SQLiteAddData.PRODUCT_NAME, p.getName());
                    intent.putExtra(SQLiteAddData.CATEGORY, p.getCategory());
                    intent.putExtra(SQLiteAddData.PRODUCT_ID, p.getId());
                    getApplicationContext().startService(intent);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DB initialization error", Toast.LENGTH_LONG).show();
            }
        });

        rDb.GetAllStores(new IDatabaseResponseHandler<Store>() {
            @Override
            public void onArrive(ArrayList<Store> data) {
                for(Store s:data){
                    Intent intent = new Intent(getApplicationContext(), SQLiteAddData.class);
                    intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE);
                    intent.putExtra(SQLiteAddData.STORE_ID, s.getId());
                    intent.putExtra(SQLiteAddData.STORE_NAME, s.getName());
                    intent.putExtra(SQLiteAddData.STORE_ADDRESS, s.getLocation());
                    getApplicationContext().startService(intent);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DB initialization error", Toast.LENGTH_LONG).show();

            }
        });

        rDb.GetAllBarcodes(new IDatabaseResponseHandler<Barcode>() {
            @Override
            public void onArrive(ArrayList<Barcode> data) {
                for(Barcode b:data){
                    String str = null;
                    Intent intent = new Intent(getApplicationContext(), SQLiteAddData.class);
                    intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_BARCODE);
                    intent.putExtra(SQLiteAddData.BARCODE, b.getBarcode());
                    intent.putExtra(SQLiteAddData.PRODUCT_ID, b.getProduct_id());
//                    intent.putExtra(SQLiteAddData.PRODUCT_NAME, str);
                    getApplicationContext().startService(intent);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DB initialization error", Toast.LENGTH_LONG).show();

            }
        });

        rDb.GetAllStoreProductPrices(new IDatabaseResponseHandler<StoreProductPrice>() {
            @Override
            public void onArrive(ArrayList<StoreProductPrice> data) {
                for(StoreProductPrice p:data){
                    Intent intent = new Intent(getApplicationContext(), SQLiteAddData.class);
                    intent.putExtra(SQLiteAddData.ADD_TYPE, SQLiteAddData.ADD_STORE_PRODUCT_PRICE);
                    intent.putExtra(SQLiteAddData.PRODUCT_NAME, p.getProduct().getName());
                    intent.putExtra(SQLiteAddData.STORE_NAME, p.getStore().getName());
                    intent.putExtra(SQLiteAddData.STORE_ADDRESS, p.getStore().getLocation());
                    intent.putExtra(SQLiteAddData.PRICE,  p.getPrice());
                    getApplicationContext().startService(intent);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DB initialization error", Toast.LENGTH_LONG).show();

            }
        });

    }
}