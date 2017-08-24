package com.rock.werool.piensunmaize.search;

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

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.barcode.BarcodeScanner;
import com.rock.werool.piensunmaize.search.by_product.SearchByProductActivity;
import com.rock.werool.piensunmaize.search.by_store.SearchByStoreActivity;

public class SearchMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);

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

        Button buttonSearchByBarcode = (Button) findViewById(R.id.searchByBarcodeButton);
        buttonSearchByBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchMenu.this, BarcodeScanner.class);
                intent.putExtra("necessaryAction", "FIND_PRODUCT_INFO");
                startActivity(intent);
            }
        });

        Button buttonSearchByProductName = (Button) findViewById(R.id.searchByProductNameButton);
        buttonSearchByProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchMenu.this, SearchByProductActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSearchByStore = (Button) findViewById(R.id.searchByStoreButton);
        buttonSearchByStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchMenu.this, SearchByStoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
