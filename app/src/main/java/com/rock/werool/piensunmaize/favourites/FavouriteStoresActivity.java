package com.rock.werool.piensunmaize.favourites;


import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.Spannable;
import android.text.SpannableString;

import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;

import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;

import com.rock.werool.piensunmaize.search.Store;
import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;

import java.util.ArrayList;

/**
 * Displays a list of favourite stores defined previously by the user in another activity.
 * Favourite stores can be removed from the list and deleted from the remote database.
 * Information regarding favourite stores is stored and retrieved from a remote database.
 */
public class FavouriteStoresActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter;
    String[][] array;
    RemoteDatabase remoteDB;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_stores);

        //Instantiating toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Getting the icon to appear on toolbar
        getSupportActionBar().setLogo(R.mipmap.applogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.applogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Multicoloring the toolbar text
        Spannable word = new SpannableString("bread n milk");
        word.setSpan(new ForegroundColorSpan(Color.rgb(177, 227, 251)), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(word);

        remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", this);

        remoteDB.GetFavoriteStores(new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Store>() {
            /**
             * Fills an array with the data provided by GetFavoriteStores
             */
            @Override
            public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store> data) {
                array = new String[data.size()][3];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    array[i][0] = data.get(i).getName();
                    array[i][1] = data.get(i).getLocation();
                    array[i][2] = Integer.toString(data.get(i).getId());
                    al.add("q");
                }
                displayListView(al);
            }

            @Override
            public void onError(VolleyError error) {
                String message = error.getMessage();
                array = null;
            }
        });

    }

    private void displayListView(ArrayList<String> inputArray) {

        dataAdapter = new MyCustomAdapter(this, R.layout.storename_address_remove, inputArray);
        ListView listView = (ListView) findViewById(R.id.listviewfavouritestore);
        listView.setAdapter(dataAdapter);
    }

    /**
     * Returns views constructed from the input data collection.
     */
    private class MyCustomAdapter extends ArrayAdapter<String> {
        Context context;
        int textViewResourceId;
        private ArrayList<String> storeArray;

        /**
         * Returns views constructed from the storeList.
         * @param context application context.
         * @param textViewResourceId the layout location of the view to be used in list view.
         * @param storeList input data collection to be returned as views.
         */
        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> storeList) {
            super(context, textViewResourceId, storeList);
            this.textViewResourceId = textViewResourceId;
            this.storeArray = storeList;
            this.context = context;
        }

        /**
         *Defines and used to instantiate the variables to be converted in views.
         */
        private class ViewHolder {
            TextView name;
            TextView address;
            int storeId;
            ImageView remove;
        }

        /**
         * {@inheritDoc}
         * @param position position of the view to be rendered
         * @param convertView recycled instance of View previously returned
         * @param parent ViewGroup which is filled with views
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.storename_address_remove, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.favStoreName);
                holder.address = (TextView) convertView.findViewById(R.id.favStoreAddress);
                holder.remove = (ImageView) convertView.findViewById(R.id.removeFromFav1);

                convertView.setTag(holder);                             //Important! Stores the holder in the View (row)


                holder.name.setText(array[position][0]);
                holder.address.setText(array[position][1]);
                holder.storeId = Integer.parseInt(array[position][2]);

                final int positionOfElement = position;
                final String clickedStoreName = holder.name.getText().toString();
                final String clickedStoreAddress = holder.address.getText().toString();
                final int clickedStoreId = holder.storeId;

                /**
                 * Leads to SelectProductActivity providing search results for the store, which was clicked on.
                 */
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        startActivity(intent);
                    }
                });
                /**
                 * Leads to SelectProductActivity providing search results for the store, which was clicked on.
                 */
                holder.address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        startActivity(intent);
                    }
                });
                /**
                 * Leads to SelectProductActivity providing search results for the store, which was clicked on.
                 */
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        startActivity(intent);
                    }
                });
                /**
                 * Removes the clicked entry from Favourite Stores list and deletes it from the remote database.
                 */
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {        //TODO implement actions on click
                        com.rock.werool.piensunmaize.remoteDatabase.Store tempStore = new com.rock.werool.piensunmaize.remoteDatabase.Store(clickedStoreId, clickedStoreName, clickedStoreAddress);

                        remoteDB.DeleteFavoriteStore(tempStore, new IDatabaseResponseHandler<String>() {
                            @Override
                            public void onArrive(ArrayList<String> data) {
                            }

                            @Override
                            public void onError(VolleyError error) {
                            }
                        });

                        storeArray.remove(positionOfElement);
                        String[][] array1 = new String[array.length][4];
                        int n = 0;
                        for (int i = 0; i < array.length; i++) {
                            if (i != positionOfElement) {
                                array1[n][0] = array[i][0];
                                array1[n][1] = array[i][1];
                                array1[n][2] = array[i][2];
                                n++;
                            }
                        }
                        array = array1;
                        notifyDataSetChanged();
                    }
                });


            } else {
                holder = (ViewHolder) convertView.getTag();                     //If row is already created then get the holder from it
            }
            holder.name.setText(array[position][0]);
            holder.address.setText(array[position][1]);
            holder.storeId = Integer.parseInt(array[position][2]);
            return convertView;
        }
    }
}
