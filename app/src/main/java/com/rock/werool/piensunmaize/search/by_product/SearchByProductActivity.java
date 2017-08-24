package com.rock.werool.piensunmaize.search.by_product;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.search.Product;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;

import java.util.ArrayList;

public class SearchByProductActivity extends AppCompatActivity {              //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Product> productSearchResults = new ArrayList<>();               //ListView uses productSearchResults instead of products!
    String [][] array;
    String scannedProductName = "";
    RemoteDatabase remoteDB;

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(SearchProductList , new IntentFilter ("ProcessedQueryResult"));         //Registers BroadcastReceivers
        registerReceiver(SearchProductSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
            //unregisterReceiver(SearchProductList);         //Unregisters BroadcastReceivers
            unregisterReceiver(SearchProductSQL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_product);

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

        if (getIntent().hasExtra("scannedProductName")) {
            scannedProductName = getIntent().getExtras().getString("scannedProductName");     //Recieves the passed parameters
            TextView productNameTextView = (TextView) findViewById(R.id.searchProductText);
            productNameTextView.setText(scannedProductName);
            productNameTextView.setEnabled(false);
        }

        remoteDB.FindProductByName(scannedProductName, new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Product>() {
            @Override
            public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Product> data) {
                array = new String[data.size()][3];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    array[i][0] = data.get(i).getName();
                    array[i][1] = Double.toString(data.get(i).getAvaragePricePrice());
                    array[i][2] = Long.toString(data.get(i).getId());
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
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, "");     //All products
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByProductActivity");
        */

        addSearchBarListener();
    }
    private void displayListView(ArrayList<String> inputList) {
        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_price, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewsearchproduct);
        listView.setAdapter(dataAdapter);
    }
    private class MyCustomAdapter extends ArrayAdapter<String> {

        private ArrayList<String> productList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> productList) {
            super(context, textViewResourceId, productList);
            this.productList = productList;   //1D
        }

        private class ViewHolder {
            TextView name;
            TextView averagePrice;
            long productId;
            Space productClckSpace;
            int index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View v;
            Log.v("ConvertView", String.valueOf(position));


            if (true) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.itemname_price, null);

                holder = new ViewHolder();                                                //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.searchProductName);
                holder.averagePrice = (TextView) convertView.findViewById(R.id.searchProductPrice);
                holder.productClckSpace = (Space) convertView.findViewById(R.id.searchProductClickSpace);

                holder.name.setText(array[position][0]);
                holder.averagePrice.setText(array[position][1]);
                holder.productId = Long.parseLong(array[position][2]);
                //Log.v("ConvertView", "productId: " + holder.productId);

                final String clickedProductName = holder.name.getText().toString();
                final String clickedProductAveragePrice = holder.averagePrice.getText().toString();
                final long clickedProductId = holder.productId;

                Log.v("ConvertView", "productId: " + clickedProductId);

                convertView.setTag(holder);                     //Important! Stores the holder in the View (row)
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedProductId", clickedProductId);
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedProductId", clickedProductId);
                        startActivity(intent);
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();                         //If row is already created then get the holder from it
            }

            holder.name.setText(array[position][0]);
            holder.averagePrice.setText(array[position][1]);
            holder.productId = Long.parseLong(array[position][2]);
            //Log.v("ConvertView", "productId: " + holder.productId);


            return convertView;
        }
    }

    private void addSearchBarListener() {                       //Updates results in ListView
        final EditText search = (EditText)findViewById(R.id.searchProductText);
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
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByProductActivity");
                */
                remoteDB.FindProductByName(search.getText().toString(), new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Product>() {
                    @Override
                    public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Product> data) {
                        array = new String[data.size()][3];
                        ArrayList<String> al = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            array[i][0] = data.get(i).getName();
                            array[i][1] = Double.toString(data.get(i).getAvaragePricePrice());
                            array[i][2] = Long.toString(data.get(i).getId());
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

    BroadcastReceiver SearchProductSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent(getApplicationContext(), QueryProcessingIntentService.class);
            Bundle bun = intent.getBundleExtra(SQLiteQuery.QUERY_RESULT);
            array = (String[][]) bun.getSerializable("String[][]");
            //intentForService.putExtra("String[][]", array);
            //intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE");
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

