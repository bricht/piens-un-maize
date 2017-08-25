package com.rock.werool.piensunmaize.mainpage;

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
import com.rock.werool.piensunmaize.add.FillWithHandActivity;
import com.rock.werool.piensunmaize.add.addProductChoosePage;
import com.rock.werool.piensunmaize.barcode.BarcodeScanner;
import com.rock.werool.piensunmaize.favourites.FavouriteStoresActivity;
import com.rock.werool.piensunmaize.favourites.FavouritesMenu;
import com.rock.werool.piensunmaize.remoteDatabase.dbTestActivity;
import com.rock.werool.piensunmaize.search.SearchMenu;
import com.rock.werool.piensunmaize.shoppingList.ShoppingListActivity;

public class MainMenu extends AppCompatActivity {

    Button buttonShare, buttonSearch, buttonFavourites, buttonShoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //GUNTARS: this is my test page.. should be commented out.
        Intent intent = new Intent(MainMenu.this, dbTestActivity.class);
        startActivity(intent);
        //END OF GUNTARS:


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

        //setContentView(R.layout.checkbox_list);
        //Intent intent = new Intent(this, BarcodeScanner.class);
        //startActivity(intent);

//        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(@res);
//        toolbar.setNavigationIcon(R.mipmap.applogo);


        //start with Greeeting Activity
//        startActivity(new Intent(MainMenu.this, GreetingsActivity.class));
        buttonShare = (Button) findViewById(R.id.mainMenuShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, BarcodeScanner.class);
                intent.putExtra("necessaryAction", "UPDATE_PRODUCT");
                startActivity(intent);
            }
        });
        buttonSearch = (Button) findViewById(R.id.mainMenuSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, SearchMenu.class);
                startActivity(intent);
            }
        });
        buttonFavourites = (Button) findViewById(R.id.mainMenuFavourites);
        buttonFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, FavouritesMenu.class);
                startActivity(intent);
            }
        });
        buttonShoppingList = (Button) findViewById(R.id.mainMenuShoppingList);
        buttonShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, ShoppingListActivity.class);
                startActivity(intent);
            }
        });
    }
}
