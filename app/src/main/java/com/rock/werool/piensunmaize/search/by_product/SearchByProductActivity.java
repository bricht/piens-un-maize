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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(SearchProductList , new IntentFilter ("ProcessedQueryResult"));         //Registers BroadcastReceivers
        registerReceiver(SearchProductSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
            unregisterReceiver(SearchProductList);         //Unregisters BroadcastReceivers
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
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, "");     //All products
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByProductActivity");
        /*
        products.add(new Product("Apple", "21"));           //TODO Implement local database query and format the data into ArrayList<Product>
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));
        products.add(new Product("Apple", "21"));
        products.add(new Product("Orange", "42"));

        productSearchResults.addAll(products);                 //ListView initially shows all products
        displayListView(productSearchResults);
        */
        addSearchBarListener();
    }
    private void displayListView(ArrayList<Product> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_price, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewsearchproduct);
        listView.setAdapter(dataAdapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                //dataAdapter.ViewHolder holderForClicked = dataAdapter.getItemAtPosition(position);
                //View item = (View) adapterView.getItemAtPosition(i);
                final String clickedProductName = dataAdapter.productList.get(i).getName();
                final String clickedProductAveragePrice = dataAdapter.productList.get(i).getPrice();
                intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                startActivity(intent);
            }
        });
        */
    }
    private class MyCustomAdapter extends ArrayAdapter<Product> {

        private ArrayList<Product> productList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Product> productList) {
            super(context, textViewResourceId, productList);
            this.productList = new ArrayList<Product>();
            this.productList.addAll(productList);
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

                Product product = productList.get(position);
                holder.name.setText(product.getName());
                holder.averagePrice.setText(product.getPrice());

                final String clickedProductName = holder.name.getText().toString();
                final String clickedProductAveragePrice = holder.averagePrice.getText().toString();

                convertView.setTag(holder);                     //Important! Stores the holder in the View (row)
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        //final String clickedProductName = holder.name.toString();
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class);
                        //final String clickedProductName = holder.name.toString();
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

            Product product = productList.get(position);
            holder.name.setText(product.getName());
            holder.averagePrice.setText(product.getPrice());

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
    BroadcastReceiver SearchProductList = new BroadcastReceiver() {              //Receives an ArrayList from QueryProcessingIntentService
        @Override
        public void onReceive(Context context, Intent intent) {
            productSearchResults.clear();
            productSearchResults.addAll((ArrayList<Product>) intent.getSerializableExtra("ArrayList<Product>"));
            displayListView(productSearchResults);                 //TODO Maybe not a good way to update ListView
        }

    };

    BroadcastReceiver SearchProductSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent(getApplicationContext(), QueryProcessingIntentService.class);
            intentForService.putExtra("String[][]", intent.getBundleExtra(SQLiteQuery.QUERY_RESULT));
            intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE");
            getApplicationContext().startService(intentForService);
        }
    };
}

