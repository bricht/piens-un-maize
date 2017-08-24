package com.rock.werool.piensunmaize.add;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.Store;
import com.rock.werool.piensunmaize.remoteDatabase.StoreProductPrice;
import com.rock.werool.piensunmaize.search.by_store.SearchByStoreActivity;

import java.util.ArrayList;
import java.sql.Date;

public class FillWithHandActivity extends AppCompatActivity {
    boolean addNew = false;
    String scannedProductName = "";
    String scannedProductBarcode;
    RemoteDatabase remoteDB;
    Double productPrice;

    String selectedStoreNameAddress = "";
    int selectedStoreId = -1;
    Product product;
    Store store;

    Button thankButton;
    Button chooseStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_with_hand);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //SHOW ICON
        getSupportActionBar().setLogo(R.mipmap.applogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.applogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // /SHOW ICON
        Spannable word = new SpannableString("bread n milk");
        word.setSpan(new ForegroundColorSpan(Color.rgb(177, 227, 251)), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(word);

        RemoteDatabase remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());

        if (getIntent().hasExtra("Product")) {
            product = (Product) getIntent().getExtras().getSerializable("Product");
            scannedProductName = product.getName();
            TextView productNameTextView = (TextView) findViewById(R.id.editProductName);
            productNameTextView.setText(scannedProductName);
            productNameTextView.setEnabled(false);
        }
        if (getIntent().hasExtra("Store")) {
            store = (Store) getIntent().getExtras().getSerializable("Store");
        }
        //if (getIntent().hasExtra("addNew")) {
        //    addNew = getIntent().getExtras().getBoolean("addNew");
        //}
        if (getIntent().hasExtra("scannedProductBarcode")) {
            scannedProductBarcode = getIntent().getExtras().getString("barcodeId");
        }


        if (getIntent().hasExtra("clickedStoreName")) {
            selectedStoreNameAddress = getIntent().getExtras().getString("clickedStoreName");
            if (getIntent().hasExtra("clickedStoreAddress")) {
                selectedStoreNameAddress += " " + getIntent().getExtras().getString("clickedStoreAddress");
            }
            TextView storeName = (TextView) findViewById(R.id.editShop);
            storeName.setText(selectedStoreNameAddress);
        }
        if (getIntent().hasExtra("clickedStoreId")) {
            selectedStoreId = getIntent().getExtras().getInt("clickedStoreId");
        }

        remoteDB.FindProductByBarCode(scannedProductBarcode, new IDatabaseResponseHandler<Product>() {
            @Override
            public void onArrive(ArrayList<Product> data) {
                addNew = false;
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        final EditText productName = (EditText) findViewById(R.id.editProductName);

        thankButton = (Button) findViewById(R.id.addButton);
        thankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*              //TODO DON'T DELETE THIS! ITS COMMENTED JUST SO IT CANT CHANGE DB.
                RemoteDatabase remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());
                productPrice = Double.parseDouble(((EditText) findViewById(R.id.editPrice)).getText().toString());

                if (!(productName.getText().toString().equals(""))) {

                    if (selectedStoreId < 0) {

                        if (productPrice != null && productPrice < 0) {

                            product.setName(productName.getText().toString());
                            if (addNew) {
                                remoteDB.AddProductAndBarcode(product, scannedProductBarcode, new IDatabaseResponseHandler<String>() {
                                    @Override
                                    public void onArrive(ArrayList<String> data) {
                                        Toast.makeText(getApplicationContext(), "Product successfully added", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error while adding to database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                remoteDB.UpdateProduct(product, new IDatabaseResponseHandler<String>() {
                                    @Override
                                    public void onArrive(ArrayList<String> data) {
                                        Toast.makeText(getApplicationContext(), "Product successfully updating", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error while updating database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            StoreProductPrice storeProductPrice = new StoreProductPrice(store, product, productPrice);
                            remoteDB.AddStoreProductPrice(storeProductPrice, new IDatabaseResponseHandler<String>() {
                                @Override
                                public void onArrive(ArrayList<String> data) {
                                }

                                @Override
                                public void onError(VolleyError error) {
                                }
                            });
                            remoteDB.UpdateStoreProductPrice(storeProductPrice, new IDatabaseResponseHandler<String>() {
                                @Override
                                public void onArrive(ArrayList<String> data) {
                                }

                                @Override
                                public void onError(VolleyError error) {
                                }
                            });

                            Intent intent = new Intent(FillWithHandActivity.this, ThankActivity.class);
                            startActivity(intent);
                        } else {    //productPrice = null || productPrice > 0
                            Toast.makeText(getApplicationContext(), "Product price is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {    //selectedStoreId <= 0
                        Toast.makeText(getApplicationContext(), "Store is not selected", Toast.LENGTH_SHORT).show();
                    }
                } else {    //productName.getText().toString().equals("")
                    Toast.makeText(getApplicationContext(), "Product name is empty", Toast.LENGTH_SHORT).show();
                }
                */              //TODO DON'T DELETE THIS! ITS COMMENTED JUST SO IT CANT CHANGE DB.
            }
        });
        chooseStore = (Button) findViewById(R.id.editChooseStore);
        chooseStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchByStoreActivity.class);
                intent.putExtra("selectStoreFromEdit", true);
                intent.putExtra("Product", product);    //Sends product so activity can start this activity with the same data after selection
                intent.putExtra("scannedProductBarcode", scannedProductBarcode);
                intent.putExtra("addNew", addNew);
                startActivity(intent);
            }
        });

    }
}
