package com.rock.werool.piensunmaize.add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.barcode.BarcodeScanner;

public class addProductChoosePage extends AppCompatActivity {

    Button buttonScanBarcode, buttonFillByHand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_choose_page);
        buttonScanBarcode = (Button) findViewById(R.id.scanButton);
        buttonScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addProductChoosePage.this, BarcodeScanner.class);
                startActivity(intent);
            }
        });
        buttonFillByHand = (Button) findViewById(R.id.refreshBarcode);
        buttonFillByHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addProductChoosePage.this, FillWithHandActivity.class);
                startActivity(intent);
            }
        });
    }
}
