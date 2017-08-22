package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.sql.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final int FIND_PRODUCT_BY_NAME = 1;
    public static final int FIND_PRODUCT_BY_CATEGORY = 2;
    public static final int FIND_PRODUCT_BY_BARCODE = 3;
    public static final int FIND_PRODUCT_BY_STRING_KEY = 4;
    public static final int FIND_STORE_BY_NAME = 5;
    public static final int FIND_STORE_BY_LOCATION = 6;
    public static final int FIND_STORE_BY_STRING_KEY = 7;
    public static final int FIND_STORE_PRODUCT_PRICE = 8;
    public static final int ADD_PRODUCT = 9;
    public static final int ADD_STORE = 10;
    public static final int ADD_BARCOE = 11;
    public static final int ADD_STOREPRODUCTPRICE = 12;
    public static final int UPDATE_PRODUCT = 13;
    public static final int UPDATE_STORE = 14;
    public static final int UPDATE_BARCODE = 15;
    public static final int UPDATE_STOREPRODUCTPRICE = 16;
    public static final int DELETE_PRODUCT = 17;
    public static final int DELETE_STORE = 18;
    public static final int DELETE_BARCODE = 19;
    public static final int DELETE_STOREPRODUCTPRICE = 20;
    public static final int TEST_THIS_SHIT = 21;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Product product = new Product(2,"Valmieras Piens", "piens", "nav desa", 0.90);
        Store store = new Store("Kāpostu Veikals", "Kāpostu iela 134, Kāposete, Kāpostija ");
        StoreProductPrice spp = new StoreProductPrice(store, product, 12.0,
                new Date(Calendar.getInstance().getTime().getTime()));

        addButtonListenr((Button)findViewById(R.id.btn_find_produc_name), FIND_PRODUCT_BY_NAME);
        addButtonListenr((Button)findViewById(R.id.btn_find_product_cat), FIND_PRODUCT_BY_CATEGORY);
        addButtonListenr((Button)findViewById(R.id.btn_find_product_key), FIND_PRODUCT_BY_STRING_KEY);
        addButtonListenr((Button)findViewById(R.id.btn_find_product_barcode), FIND_PRODUCT_BY_BARCODE);
        addButtonListenr((Button)findViewById(R.id.btn_find_store_name), FIND_STORE_BY_NAME);
        addButtonListenr((Button)findViewById(R.id.btn_find_store_loc), FIND_STORE_BY_LOCATION);
        addButtonListenr((Button)findViewById(R.id.btn_find_store_product_price), FIND_STORE_PRODUCT_PRICE);
        addButtonListenr((Button)findViewById(R.id.btn_add_product), ADD_PRODUCT);
        addButtonListenr((Button)findViewById(R.id.btn_add_store), ADD_STORE);
        addButtonListenr((Button)findViewById(R.id.btn_add_spp), ADD_STOREPRODUCTPRICE);
        addButtonListenr((Button)findViewById(R.id.btn_add_barcode), ADD_BARCOE);
        addButtonListenr((Button)findViewById(R.id.btn_update_product), UPDATE_PRODUCT);
        addButtonListenr((Button)findViewById(R.id.btn_update_store), UPDATE_STORE);
        addButtonListenr((Button)findViewById(R.id.btn_update_spp), UPDATE_STOREPRODUCTPRICE);
        addButtonListenr((Button)findViewById(R.id.btn_update_barcode), UPDATE_BARCODE);
        addButtonListenr((Button)findViewById(R.id.btn_delete_product), DELETE_PRODUCT);
        addButtonListenr((Button)findViewById(R.id.btn_delete_store), DELETE_STORE);
        addButtonListenr((Button)findViewById(R.id.btn_delete_spp), DELETE_STOREPRODUCTPRICE);
        addButtonListenr((Button)findViewById(R.id.btn_delete_barcode), TEST_THIS_SHIT);

    }

    private void addButtonListenr(Button b, final int extraData) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent superDuper = new Intent(MainActivity.this, TestFind.class);
                superDuper.putExtra("action", extraData);
                MainActivity.this.startActivity(superDuper);
            }
        });
    }
}
