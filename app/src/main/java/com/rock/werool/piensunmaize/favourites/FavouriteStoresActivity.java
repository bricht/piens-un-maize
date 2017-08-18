package com.rock.werool.piensunmaize.favourites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.rock.werool.piensunmaize.R;

import java.util.ArrayList;

public class FavouriteStoresActivity extends AppCompatActivity {

    ImageButton removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_stores);


        ArrayList<FavouriteStore> favStoreArray = new ArrayList<>();
        removeButton = (ImageButton) findViewById(R.id.cross_button);
        //TODO implement the button when needed: https://www.youtube.com/watch?v=VX5nxFwAQ-M

        favStoreArray.add(new FavouriteStore("Rimi", "Anywhere 42")); ///Where are they stored??? Prob some local db
        favStoreArray.add(new FavouriteStore("Maxima", "Anywhere 43"));
        favStoreArray.add(new FavouriteStore("SuperGhetto", "Brīvības iela 822"));
        favStoreArray.add(new FavouriteStore("Rimi", "Anywhere 2"));
        favStoreArray.add(new FavouriteStore("Maxima", "Anywhere 242"));
        favStoreArray.add(new FavouriteStore("Maxima", "Anywhere 452"));

        ListView favourite_shops_listView = (ListView) findViewById(R.id.favourite_shops_listView);     //creates listview


        // TODO change the simple_list_item_1
        // transfers the favouriteStore object into a  list item
        ListAdapter favouriteAdapter = new ArrayAdapter<FavouriteStore>(favourite_shops_listView.getContext(), android.R.layout.simple_list_item_1, favStoreArray);
        favourite_shops_listView.setAdapter(favouriteAdapter);

        favourite_shops_listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    //reacts every time user clicks on a list item
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //EXAMPLE FROM THE VIDEO - String food = String.valueOf(parent.getItemAtPosition(position)); ---> https://youtu.be/A-_hKWMA7mk?t=360 <---
                        //TODO      1. convert the list item back into an object
                        //TODO      2. go to search by shop and input that object there

                    }
                }
        );




//        String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
//        ListView buckysListView = (ListView) findViewById(R.id.favourite_shops_listView);
//        ListAdapter buckysAdapter = new ArrayAdapter<String>(buckysListView.getContext(), android.R.layout.simple_list_item_1, foods);
//        buckysListView.setAdapter(buckysAdapter);


    }
}
