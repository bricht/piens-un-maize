package com.rock.werool.piensunmaize.favourites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import com.rock.werool.piensunmaize.R;

/**
 * Created by Jaco on 21/08/2017.
 * Edited by Roberts on 22/08/2017.
 */

public class FavouritesMenu extends AppCompatActivity {

    Button buttonFavStores, buttonFavProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_menu);
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


        buttonFavStores = (Button) findViewById(R.id.favouriteStoresButton);
        buttonFavStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesMenu.this, FavouriteStoresActivity.class);
                intent.putExtra("necessaryAction", "FIND_PRODUCT_INFO");
                startActivity(intent);
            }
        });
        buttonFavProducts = (Button) findViewById(R.id.favouriteProductsButton);
        buttonFavProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesMenu.this, FavouriteProductsActivity.class);
                startActivity(intent);
            }
        });


    }
}