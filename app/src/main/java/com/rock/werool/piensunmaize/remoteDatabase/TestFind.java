package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Random;

public final class TestFind extends AppCompatActivity {

    public EditText plainText;
    final RemoteDatabase db = new RemoteDatabase(
            "http://www.zesloka.tk/piens_un_maize_db/", this);
    Random random = new Random();
    Product product;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_find_product_by_name);

        store = new Store(random.nextInt(4) + 1, "test", "test");
        product = new Product(random.nextInt(14) + 1, "test", "test", "test", 0.0);


        plainText = (EditText) findViewById(R.id.txt_find_product_by_name);
        plainText.setText("");
        final Spinner spinner = (Spinner) findViewById(R.id.spiner_find_product_by_name);
        Intent mIntent = getIntent();

        final int action = mIntent.getIntExtra("action", 0);

        plainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (action) {

                    case MainActivity.FIND_PRODUCT_BY_NAME:
                        Log.d("testfind", "this is triggered!");
                        db.FindProductByName(s.toString(), new ProductReturnHanler(spinner));
                        break;
                    case MainActivity.FIND_PRODUCT_BY_CATEGORY:
                        db.FindProductByCategory(s.toString(), new ProductReturnHanler(spinner));
                        break;
                    case MainActivity.FIND_PRODUCT_BY_STRING_KEY:
                        db.FindProductByStringKey(s.toString(), new ProductReturnHanler(spinner));
                        break;
                    case MainActivity.FIND_PRODUCT_BY_BARCODE:
                        db.FindProductByBarCode(s.toString(), new ProductReturnHanler(spinner));
                        break;
                    case MainActivity.FIND_STORE_BY_NAME:
                        db.FindStoreByName(s.toString(), new StoreReturnHandler(spinner));
                        break;
                    case MainActivity.FIND_STORE_BY_LOCATION:
                        db.FindStoreByLocation(s.toString(),new StoreReturnHandler(spinner));
                        break;
                    case MainActivity.FIND_STORE_BY_STRING_KEY:
                        db.FindStoreByStringKey(s.toString(),new StoreReturnHandler(spinner));
                        break;
                    case MainActivity.FIND_STORE_PRODUCT_PRICE:
                        if(!s.equals("")) {
                            int productID = 0;
                            try {
                                productID = Integer.parseInt(s.toString());
                            } catch (Exception e) {

                            }

                            db.FindStoreProductPrice(new Product(productID,
                                    "test", "test", "test", 0.5), new StoreProductPriceReturnHanler(spinner));
                        }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button addButon = (Button)findViewById(R.id.btn_add);
        if(action != (MainActivity.ADD_PRODUCT | MainActivity.ADD_STORE | MainActivity.ADD_STOREPRODUCTPRICE | MainActivity.ADD_BARCOE | MainActivity.UPDATE_PRODUCT | MainActivity.UPDATE_STORE | MainActivity.UPDATE_STOREPRODUCTPRICE | MainActivity.UPDATE_BARCODE) ) {
            addButon.setActivated(false);
        }

        addButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = "";
                switch(action) {
                    case MainActivity.ADD_PRODUCT:
                        txt = plainText.getText().toString();
                        db.AddProduct(new Product(txt, txt, txt, random.nextDouble()
                        ), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.ADD_STORE:
                        txt = plainText.getText().toString();
                        db.AddStore(new Store(txt, txt), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.ADD_BARCOE:
                        txt = plainText.getText().toString();
                        db.AddBarcode(new Barcode(txt, random.nextInt(14) + 1),
                                new StringReturnHanler(spinner));
                        break;
                    case MainActivity.ADD_STOREPRODUCTPRICE:
                        db.AddStoreProductPrice(new StoreProductPrice(store, product,
                                random.nextDouble(), new java.sql.Date(
                                        java.util.Calendar.getInstance().getTime().getTime()
                        )), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.UPDATE_PRODUCT:
                        txt = plainText.getText().toString();
                        db.UpdateProduct(new Product(random.nextInt(14) + 1, txt, txt, txt, random.nextDouble()
                        ), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.UPDATE_STORE:
                        txt = plainText.getText().toString();
                        db.UpdateStore(new Store(random.nextInt(5) + 1, txt, txt), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.UPDATE_BARCODE:
                        txt = plainText.getText().toString();
                        db.UpdateBarcode(new Barcode(txt, random.nextInt(14) + 1),
                                new StringReturnHanler(spinner));
                        break;
                    case MainActivity.UPDATE_STOREPRODUCTPRICE:
                        db.UpdateStoreProductPrice(new StoreProductPrice(store, product,
                                random.nextDouble(), new java.sql.Date(
                                java.util.Calendar.getInstance().getTime().getTime()
                        )), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.DELETE_PRODUCT:
                        txt = plainText.getText().toString();
                        db.DeleteProduct(new Product(Integer.parseInt(txt), txt, txt, txt, random.nextDouble()
                        ), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.DELETE_STORE:

                        txt = plainText.getText().toString();
                        db.DeleteStore(new Store(Integer.parseInt(txt), txt, txt), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.DELETE_BARCODE:
                        txt = plainText.getText().toString();
                        db.DeleteBarcode(new Barcode(txt, random.nextInt(14) + 1),
                                new StringReturnHanler(spinner));
                        break;
                    case MainActivity.DELETE_STOREPRODUCTPRICE:
                        db.DeleteStoreProductPrice(new StoreProductPrice(store, product,
                                random.nextDouble(), new java.sql.Date(
                                java.util.Calendar.getInstance().getTime().getTime()
                        )), new StringReturnHanler(spinner));
                        break;
                    case MainActivity.TEST_THIS_SHIT:
                        VeryNiceDatabase vndb = new VeryNiceDatabase(TestFind.this);
                        break;
                }
            }
        });

    }



    private class StringReturnHanler extends DatbaseResponseHandler<String> {

        public StringReturnHanler(Spinner sp) {
            super(sp);
        }
        @Override
        public void onArrive(ArrayList<String> data) {
            Log.d("sqlerror", data.get(0));
            ArrayList<String> products = new ArrayList<String>();
            for(String str : data) {
                products.add(str.toString());
            }
            ArrayAdapter<String> spinAdapter =
                    new ArrayAdapter<String>(
                            TestFind.this,
                            android.R.layout.simple_spinner_item, products);
            spinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.getSpinner().setAdapter(spinAdapter);
        }
    }

    private class ProductReturnHanler extends DatbaseResponseHandler<Product> {

        public ProductReturnHanler(Spinner sp) {
            super(sp);
        }
        @Override
        public void onArrive(ArrayList<Product> data) {
            ArrayList<String> products = new ArrayList<String>();
            for(Product p : data) {
                products.add(p.toString());
            }
            ArrayAdapter<String> spinAdapter =
                    new ArrayAdapter<String>(
                            TestFind.this,
                            android.R.layout.simple_spinner_item, products);
            spinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.getSpinner().setAdapter(spinAdapter);
        }
    }

    private class StoreReturnHandler extends DatbaseResponseHandler<Store> {

        public StoreReturnHandler(Spinner sp) {
            super(sp);
        }
        @Override
        public void onArrive(ArrayList<Store> data) {
            ArrayList<String> products = new ArrayList<String>();
            for(Store s : data) {
                products.add(s.toString());
            }
            ArrayAdapter<String> spinAdapter =
                    new ArrayAdapter<String>(
                            TestFind.this,
                            android.R.layout.simple_spinner_item, products);
            spinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.getSpinner().setAdapter(spinAdapter);
        }
    }

    private class StoreProductPriceReturnHanler extends DatbaseResponseHandler<StoreProductPrice> {

        public StoreProductPriceReturnHanler(Spinner sp) {
            super(sp);
        }
        @Override
        public void onArrive(ArrayList<StoreProductPrice> data) {
            ArrayList<String> products = new ArrayList<String>();
            //ArrayList<String> test = new ArrayList<String>();
            //Log.d("thisiscalled", "hall hallo");
            //test.add("this this is called and data size = " + data.size());
            for(StoreProductPrice spp : data) {
                products.add(spp.toString());
            }
            ArrayAdapter<String> spinAdapter =
                    new ArrayAdapter<String>(
                            TestFind.this,
                            android.R.layout.simple_spinner_item, products);
            spinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.getSpinner().setAdapter(spinAdapter);
        }
    }

    private abstract class DatbaseResponseHandler<T> implements IDatabaseResponseHandler<T> {

        Spinner sp;

        public DatbaseResponseHandler(Spinner sp) { this.sp = sp; }

        public Spinner getSpinner() { return this.sp; }

        @Override
        public void onError(VolleyError error) {
            String [] e = {error.getMessage()};
            ArrayAdapter<String> spinAdapter =
                    new ArrayAdapter<String>(
                            TestFind.this,
                            android.R.layout.simple_spinner_item, e);
            spinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp.setAdapter(spinAdapter);
        }
    }
}
