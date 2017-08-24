package com.rock.werool.piensunmaize.search.by_store;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.StoreProductPrice;
import com.rock.werool.piensunmaize.search.Product;
import com.rock.werool.piensunmaize.search.by_product.SelectStoreActivity;

import java.util.ArrayList;

public class SelectProductActivity extends AppCompatActivity {      //TODO this is just a copy of SearchByProductActivity
    MyCustomAdapter dataAdapter;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Product> productSearchResults = new ArrayList<>();               //ListView uses productSearchResults instead of products!
    String clickedStoreName;
    String clickedStoreAddress;
    int clickedStoreId;
    String[][] array;
    RemoteDatabase remoteDB;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(SelectProductSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(SelectProductSQL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", this);

        ImageView buttonFav = (ImageView) findViewById(R.id.addToFavouritesStore);
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remoteDB.SetFavoriteStore(clickedStoreId, new IDatabaseResponseHandler<String>() {
                    @Override
                    public void onArrive(ArrayList<String> data) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });


        Bundle extras = getIntent().getExtras();            //Recieves the passed parameters in a bundle
        clickedStoreName = extras.getString("clickedStoreName");     //Gets the specified param from the bundle
        clickedStoreAddress = extras.getString("clickedStoreAddress");
        int test = extras.getInt("clickedStoreId");
        clickedStoreId = extras.getInt("clickedStoreId");

        TextView storeNameAddressTextView = (TextView) findViewById(R.id.selectedStoreNameAddress);
        storeNameAddressTextView.setText(clickedStoreName + " " + clickedStoreAddress);
        /*
        remoteDB.FindProductInStoreByName(clickedStoreId, "", new IDatabaseResponseHandler<StoreProductPrice>() {
            @Override
            public void onArrive(ArrayList<StoreProductPrice> data) {
                array = new String[data.size()][3];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    com.rock.werool.piensunmaize.remoteDatabase.Product ta = data.get(i).getProduct();
                    array[i][0] = data.get(i).getProduct().getName();
                    array[i][1] = Double.toString(data.get(i).getPrice());
                    array[i][2] = Long.toString(data.get(i).getProduct().getId());
                    al.add("q");
                }
                displayListView(al);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        */
        /*
        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, "");     //All products
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, clickedStoreAddress);
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, clickedStoreAddress);
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SelectProductActivity");
        */

        addSearchBarListener();

    }
    private void displayListView(ArrayList<String> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_price_addtolist, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewselectproduct);
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
            TextView priceInStore;
            ImageView cart;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.itemname_price_addtolist, null);

                holder = new MyCustomAdapter.ViewHolder();                                                //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.productName);
                holder.priceInStore = (TextView) convertView.findViewById(R.id.productPrice);
                holder.cart = (ImageView) convertView.findViewById(R.id.selectProductToList);

                holder.name.setText(array[position][0]);
                holder.priceInStore.setText(array[position][1]);

                final String clickedProductName = holder.name.getText().toString();
                final String clickedProductAveragePrice = holder.priceInStore.getText().toString();

                convertView.setTag(holder);                     //Important! Stores the holder in the View (row)


                holder.cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {        //TODO implement actions on click
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);

                    }
                });
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);

                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);

                    }
                });

                /*
                holder.check.setOnClickListener(new View.OnClickListener() {        //Ignore this don't delete
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Product product = (Product) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        product.setChecked(cb.isChecked());
                    }
                });
                */
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();                         //If row is already created then get the holder from it
            }

            //holder.check.setChecked(product.getChecked());                //Ignore this
            //holder.check.setTag(product);
            holder.name.setText(array[position][0]);
            holder.priceInStore.setText(array[position][1]);
            return convertView;
        }
    }

    private void addSearchBarListener() {                       //Updates results in ListView
        final EditText search = (EditText)findViewById(R.id.selectProductNameText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*
                Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
                intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
                intentForSQL.putExtra(SQLiteQuery.SRC_NAME, search.getText().toString());     //TODO may need to turn to lowercase
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, clickedStoreAddress);
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, clickedStoreAddress);
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SelectProductActivity");
                */
                remoteDB.FindProductInStoreByName(clickedStoreId, search.getText().toString(), new IDatabaseResponseHandler<StoreProductPrice>() {
                    @Override
                    public void onArrive(ArrayList<StoreProductPrice> data) {
                        array = new String[data.size()][3];
                        ArrayList<String> al = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            array[i][0] = data.get(i).getProduct().getName();
                            array[i][1] = Double.toString(data.get(i).getPrice());
                            array[i][2] = Long.toString(data.get(i).getProduct().getId());
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
    BroadcastReceiver SelectProductSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent();
            intentForService.putExtra("Cursor", "PLACEHOLDER");     //TODO use real cursor
            intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_PRODUCTNAME_PRODUCTPRICE");
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
