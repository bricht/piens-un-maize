package com.rock.werool.piensunmaize.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
    String scannedProductCategory;
    RemoteDatabase remoteDB;
    Double productPrice = 0.0;

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
            scannedProductCategory = product.getCategory();
            TextView productNameTextView = (TextView) findViewById(R.id.editProductName);
            productNameTextView.setText(scannedProductName);
            productNameTextView.setEnabled(false);

            TextView productCategoryTextView = (TextView) findViewById(R.id.editCategory);
            productCategoryTextView.setText(scannedProductCategory);
            productCategoryTextView.setEnabled(false);
        }
        if (getIntent().hasExtra("scannedProductPrice")) {
            productPrice = getIntent().getExtras().getDouble("scannedProductPrice");
            TextView productPriceTextView = (TextView) findViewById(R.id.editPrice);
            if (productPrice > 0)
                productPriceTextView.setText(Double.toString(productPrice));
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
            //Toast.makeText(getApplicationContext(), Integer.toString(selectedStoreId), Toast.LENGTH_SHORT).show();

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

                if (!(productName.getText().toString().equals(""))) {

                    if (selectedStoreId > 0) {
                        productPrice = Double.parseDouble(((EditText) findViewById(R.id.editPrice)).getText().toString());
                        if (productPrice != null && productPrice > 0) {
                            product.setName(scannedProductName);

                            new AlertDialog.Builder(FillWithHandActivity.this)
                                    .setTitle("Confirmation")
                                    .setMessage("Are you sure the entered data is correct?")
                                    .setIcon(getResources().getIdentifier("warning", "drawable", getPackageName()))
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            executeUpdate();        //WARNING!!! writes changes to online DB
                                        }})
                                    .setNegativeButton("NO", null).show();


                        } else {    //productPrice = null || productPrice > 0
                            Toast.makeText(getApplicationContext(), "Product price is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {    //selectedStoreId <= 0
                        Toast.makeText(getApplicationContext(), "Store is not selected", Toast.LENGTH_SHORT).show();
                    }
                } else {    //productName.getText().toString().equals("")
                    Toast.makeText(getApplicationContext(), "Product name is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chooseStore = (Button) findViewById(R.id.editChooseStore);
        chooseStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView productPriceTextView = (TextView) findViewById(R.id.editPrice);
                if (productPriceTextView.getText().toString().length() > 0) {
                    productPrice = Double.parseDouble(productPriceTextView.getText().toString());
                }

                Intent intent = new Intent(getApplicationContext(), SearchByStoreActivity.class);
                intent.putExtra("selectStoreFromEdit", true);
                intent.putExtra("Product", product);    //Sends product so activity can start this activity with the same data after selection
                intent.putExtra("scannedProductBarcode", scannedProductBarcode);
                intent.putExtra("addNew", addNew);
                intent.putExtra("scannedProductPrice", productPrice);
                startActivity(intent);
            }
        });

    }
    protected void executeUpdate() {
        RemoteDatabase remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());
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
    }
}
