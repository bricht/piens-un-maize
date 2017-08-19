package com.rock.werool.piensunmaize.add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;

public class FillWithHandActivity extends AppCompatActivity {

    Button thankButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_with_hand);

        if (getIntent().hasExtra("scannedProductName")) {
            String scannedProductName = getIntent().getExtras().getString("scannedProductName");     //Recieves the passed parameters (from barcode scanner)
            TextView productNameTextView = (TextView) findViewById(R.id.editProductName);
            productNameTextView.setText(scannedProductName);
        }
        if (getIntent().hasExtra("scannedProductCategory")) {
            String scannedProductName = getIntent().getExtras().getString("scannedProductCategory");     //Recieves the passed parameters (from barcode scanner)
            TextView productNameTextView = (TextView) findViewById(R.id.editCategory);
            productNameTextView.setText(scannedProductName);
        }

        thankButton = (Button) findViewById(R.id.addButton);
        thankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FillWithHandActivity.this, ThankActivity.class);
                startActivity(intent);
            }
        });
    }
}
