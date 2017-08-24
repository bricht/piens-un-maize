package com.rock.werool.piensunmaize.add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;

import java.util.ArrayList;

public class FillWithHandActivity extends AppCompatActivity {
    boolean addNew = false;
    String scannedProductName;
    String scannedProductBarcode;
    String barcodeId;
    Product product;

    Button thankButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_with_hand);

        if (getIntent().hasExtra("Product")) {
            product = (Product) getIntent().getExtras().getSerializable("Product");
            scannedProductName = product.getName();
            TextView productNameTextView = (TextView) findViewById(R.id.editProductName);
            productNameTextView.setText(scannedProductName);
        }
        if (getIntent().hasExtra("addNew")) {
            addNew = getIntent().getExtras().getBoolean("addNew");
        }
        if (getIntent().hasExtra("barcodeId")) {
            barcodeId = getIntent().getExtras().getString("barcodeId");
        }
        /*
        if (getIntent().hasExtra("scannedProductName")) {
            scannedProductName = getIntent().getExtras().getString("scannedProductName");     //Recieves the passed parameters (from barcode scanner)
            TextView productNameTextView = (TextView) findViewById(R.id.editProductName);
            productNameTextView.setText(scannedProductName);
        }
        if (getIntent().hasExtra("scannedProductBarcode")) {
            scannedProductBarcode = getIntent().getExtras().getString("scannedProductName");     //Recieves the passed parameters (from barcode scanner)
        }
        if (getIntent().hasExtra("scannedProductCategory")) {
            String scannedProductName = getIntent().getExtras().getString("scannedProductCategory");     //Recieves the passed parameters (from barcode scanner)
            TextView productNameTextView = (TextView) findViewById(R.id.editCategory);
            productNameTextView.setText(scannedProductName);
        }
        */

        thankButton = (Button) findViewById(R.id.addButton);
        thankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteDatabase remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());
                if (addNew) {
                    remoteDB.AddProductAndBarcode(product, barcodeId, new IDatabaseResponseHandler<String>() {
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
                Intent intent = new Intent(FillWithHandActivity.this, ThankActivity.class);
                startActivity(intent);
            }
        });
    }
}
