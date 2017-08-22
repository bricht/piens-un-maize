package com.rock.werool.piensunmaize.SQLiteLocal_DB;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

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
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DbInitialize extends IntentService {
    private SQLiteHelper helper;
    private SQLiteDatabase database;

    public DbInitialize(){
        super(SQLiteQuery.class.getName());
    }

    public DbInitialize(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        helper = new SQLiteHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RemoteDatabase rDb = new RemoteDatabase("http://zsloka.tk/piens_un_maize_db/", getApplicationContext());

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
                    intent.putExtra(SQLiteAddData.PRODUCT_NAME, str);
                    getApplicationContext().startService(intent);
                }
            }

            @Override
            public void onError(VolleyError error) {

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

            }
        });

    }
}