package com.rock.werool.piensunmaize.search.by_product;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.search.Product;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;

import java.util.ArrayList;

public class SearchByProductActivity extends AppCompatActivity {              //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Product> productSearchResults = new ArrayList<>();               //ListView uses productSearchResults instead of products!
    String [][] array;

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
        if (getIntent().hasExtra("scannedProductName")) {
            String scannedProductName = getIntent().getExtras().getString("scannedProductName");     //Recieves the passed parameters
            TextView productNameTextView = (TextView) findViewById(R.id.searchProductText);
            productNameTextView.setText(scannedProductName);
        }

        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, "siers");     //All products
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByProductActivity");

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
            Space productClckSpace;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.itemname_price, null);

                holder = new ViewHolder();                                                //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.searchProductName);
                holder.averagePrice = (TextView) convertView.findViewById(R.id.searchProductPrice);
                holder.productClckSpace = (Space) convertView.findViewById(R.id.searchProductClickSpace);

                holder.name.setText(array[position][0]);
                holder.averagePrice.setText(array[position][1]);

                final String clickedProductName = holder.name.getText().toString();
                final String clickedProductAveragePrice = holder.averagePrice.getText().toString();

                convertView.setTag(holder);                     //Important! Stores the holder in the View (row)
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
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
                holder = (ViewHolder) convertView.getTag();                         //If row is already created then get the holder from it
            }

            holder.name.setText(array[position][0]);
            holder.averagePrice.setText(array[position][1]);

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
                Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
                intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
                intentForSQL.putExtra(SQLiteQuery.SRC_NAME, search.getText().toString());     //TODO may need to turn to lowercase
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByProductActivity");
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

