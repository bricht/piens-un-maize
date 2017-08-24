package com.rock.werool.piensunmaize.search.by_product;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.StoreProductPrice;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;
import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;

public class SelectStoreActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter;
    ArrayList<Store> stores = new ArrayList<>();
    ArrayList<Store> storeSearchResults = new ArrayList<>();            //ListView uses storeSearchResults instead of stores!
    String clickedProductName;
    long clickedProductId;
    String [][] array;
    RemoteDatabase remoteDB;
    ImageView buttonFav;
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(SelectStoreSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(SelectStoreSQL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
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

        remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", this);


        buttonFav = (ImageView) findViewById(R.id.addToFavouritesProduct);
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remoteDB.SetFavoriteProduct((int)clickedProductId, new IDatabaseResponseHandler<String>() {
                    @Override
                    public void onArrive(ArrayList<String> data) {
                        Toast.makeText(getApplicationContext(), "Product added to favourites", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error adding product to favourites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        clickedProductName = getIntent().getExtras().getString("clickedProductName");     //Gets the passed parameter
        clickedProductId = getIntent().getExtras().getLong("clickedProductId");
        //String clickedProductAveragePrice = extras.getString("clickedAveragePrice");
        TextView productNameTextView = (TextView) findViewById(R.id.selectedProductName);
        productNameTextView.setText(clickedProductName);

        remoteDB.FindStoreProductPrice(new Product(clickedProductId, null, null, null, 0), new IDatabaseResponseHandler<StoreProductPrice>() {
            @Override
            public void onArrive(ArrayList<StoreProductPrice> data) {
                array = new String[data.size()][4];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    com.rock.werool.piensunmaize.remoteDatabase.Store store = data.get(i).getStore();
                    array[i][1] = store.getName();
                    array[i][2] = store.getLocation();
                    StoreProductPrice prod = data.get(i);
                    array[i][3] = Double.toString(data.get(i).getPrice());
                    al.add("q");
                }
                displayListView(al);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        /*
        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, clickedProductName);     //TODO may need to turn to lowercase
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, "");       //All stores
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, "");     //All addresses
        //startService(intentForSQL);             //Starts SQLite intent service
        */



        Log.v("BroadcastDebug", "SQLite query broadcast sent from SelectStoreActivity");

        final EditText selectStoreName = (EditText)findViewById(R.id.selectStoreNameText);
        final EditText selectStoreAddress = (EditText)findViewById(R.id.selectStoreAddressText);
        addSearchBarListener(selectStoreName);
        addSearchBarListener(selectStoreAddress);    //Add listeners to both text fields

//        ImageView addToFavouritesProduct = (ImageView)findViewById(R.id.addToFavouritesProduct);
//        addToFavouritesProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO add selected product to favourites database
//            }
//        });
    }
    private void displayListView(ArrayList<String> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.storename_address_itemprice, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewselectstore);
        listView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<String> {
        Context context;
        int textViewResourceId;
        private ArrayList<String> storeList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> storeList) {
            super(context, textViewResourceId, storeList);
            this.textViewResourceId = textViewResourceId;
            this.storeList = storeList;
            this.context = context;
        }
        private class ViewHolder {
            TextView name;
            TextView address;
            TextView itemPrice;
            ImageView cart;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (true) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.storename_address_itemprice, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.storeName);
                holder.address = (TextView) convertView.findViewById(R.id.storeAddress);
                holder.itemPrice = (TextView) convertView.findViewById(R.id.selectedProductPriceInStore);
                holder.cart = (ImageView) convertView.findViewById(R.id.selectStoreToList);

                holder.name.setText(array[position][1]);
                holder.address.setText(array[position][2]);
                holder.itemPrice.setText(array[position][3]);

                final String clickedStoreName = holder.name.getText().toString();
                final String clickedStoreAddress = holder.address.getText().toString();
                final String productInStorePrice = holder.itemPrice.getText().toString();

                convertView.setTag(holder);                             //Important! Stores the holder in the View (row)
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {        //TODO implement actions on click

                    }
                });
                holder.address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {        //TODO implement actions on click
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);

                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                /*
                holder.check.setOnClickListener(new View.OnClickListener() {            //Ignore this don't delete
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Store store = (Store) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        store.setChecked(cb.isChecked());
                    }
                });
                */
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();                     //If row is already created then get the holder from it
            }

            holder.name.setText(array[position][1]);
            holder.address.setText(array[position][2]);
            holder.itemPrice.setText(array[position][3]);

            return convertView;
        }
    }

    private void addSearchBarListener(EditText textFieldForListener) {                               //Updates results in ListView
        final EditText selectName = (EditText)findViewById(R.id.selectStoreNameText);
        final EditText selectAddress = (EditText)findViewById(R.id.selectStoreAddressText);

        textFieldForListener.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*
                Intent intentForSQL = new Intent(getApplicationContext(), QueryProcessingIntentService.class);
                intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
                intentForSQL.putExtra(SQLiteQuery.SRC_NAME, clickedProductName);     //TODO may need to turn to lowercase
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, selectName.getText().toString());
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, selectAddress.getText().toString());
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SelectStoreActivity");
                */
                String storeName = ((EditText) findViewById(R.id.selectStoreNameText)).getText().toString();
                String storeAddress = ((EditText) findViewById(R.id.selectStoreAddressText)).getText().toString();

                remoteDB.FindStoreByNameLocationAndProduct(new Product(clickedProductId, null, null, null, 0), storeName, storeAddress, new IDatabaseResponseHandler<StoreProductPrice>() {
                    @Override
                    public void onArrive(ArrayList<StoreProductPrice> data) {
                        array = new String[data.size()][4];
                        ArrayList<String> al = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            com.rock.werool.piensunmaize.remoteDatabase.Store store = data.get(i).getStore();
                            array[i][1] = store.getName();
                            array[i][2] = store.getLocation();
                            array[i][3] = Double.toString(data.get(i).getPrice());
                            al.add("q");
                        }
                        displayListView(al);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    BroadcastReceiver SelectStoreSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent();
            intentForService.putExtra("Cursor", "PLACEHOLDER");     //TODO use real cursor
            intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE");
            Bundle bun = intent.getBundleExtra(SQLiteQuery.QUERY_RESULT);
            array = (String[][]) bun.getSerializable("String[][]");
            ArrayList<String> array1D = new ArrayList<>();
            if (array != null) {
                //array1D = new String[array.length];
                for (int i = 0; i < array.length; i++) {
                    array1D.add(array[i][0]);
                }
            }
            displayListView(array1D);
        }
    };
}
