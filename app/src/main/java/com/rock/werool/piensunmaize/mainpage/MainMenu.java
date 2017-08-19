package com.rock.werool.piensunmaize.mainpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.add.addProductChoosePage;
import com.rock.werool.piensunmaize.search.by_product.SearchByProductActivity;
import com.rock.werool.piensunmaize.search.SearchMenu;
import com.rock.werool.piensunmaize.search.by_product.SelectStoreActivity;
import com.rock.werool.piensunmaize.search.by_store.SearchByStoreActivity;
import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;

public class MainMenu extends AppCompatActivity {

    Button buttonShare, buttonSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //setContentView(R.layout.checkbox_list);
        //Intent intent = new Intent(this, BarcodeScanner.class);
        //startActivity(intent);
        startActivity(new Intent(MainMenu.this, SearchByProductActivity.class));      //Debug only

        buttonShare = (Button) findViewById(R.id.button19);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, addProductChoosePage.class);
                startActivity(intent);
            }
        });
        buttonSearch = (Button) findViewById(R.id.button20);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, SearchMenu.class);
                startActivity(intent);
            }
        });
    }
}
