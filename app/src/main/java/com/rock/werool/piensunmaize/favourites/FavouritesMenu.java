package com.rock.werool.piensunmaize.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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