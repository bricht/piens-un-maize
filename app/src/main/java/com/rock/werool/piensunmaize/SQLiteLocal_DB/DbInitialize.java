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

        RemoteDatabase rDb = new RemoteDatabase("http://zsloka.tk/piens_un_maize_db/", null);

        rDb.GetAllProducts(new IDatabaseResponseHandler<Product>() {
            @Override
            public void onArrive(ArrayList<Product> data) {
                for(Product p:data){
                    SQLiteAddData.insertProduct(p.getId(), p.getName(), p.getCategory());
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
                    SQLiteAddData.insertStore(s.getId(), s.getName(), s.getLocation());
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
                    SQLiteAddData.insertBarcode(b.getBarcode(), null, b.getProduct_id());
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
                    SQLiteAddData.insertPrice(p.getProduct().getName(), p.getStore().getName(), p.getStore().getLocation(), p.getPrice());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}