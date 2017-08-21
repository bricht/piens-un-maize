package com.rock.werool.piensunmaize.search.by_product;

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
import android.widget.ListView;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;
import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;

public class SelectStoreActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter;
    ArrayList<Store> stores = new ArrayList<>();
    ArrayList<Store> storeSearchResults = new ArrayList<>();            //ListView uses storeSearchResults instead of stores!

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(SelectStoreList , new IntentFilter("ProcessedQueryResult"));         //Registers BroadcastReceivers
        registerReceiver(SelectStoreSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(SelectStoreList);         //Unregisters BroadcastReceivers
        unregisterReceiver(SelectStoreSQL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_store);
        String clickedProductName = getIntent().getExtras().getString("clickedProductName");     //Gets the passed parameter
        //String clickedProductAveragePrice = extras.getString("clickedAveragePrice");
        TextView productNameTextView = (TextView) findViewById(R.id.selectedProductName);
        productNameTextView.setText(clickedProductName);

        stores.add(new Store("Rimi", "Somewhere 24"));          //TODO Implement local database query and format the data into ArrayList<Store>
        stores.add(new Store("Ma\nxima", "Anywhere 42"));
        stores.add(new Store("Rimi\nRimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));
        stores.add(new Store("Rimi", "Somewhere 24"));
        stores.add(new Store("Maxima", "Anywhere 42"));

        storeSearchResults.addAll(stores);                  //ListView initially shows all stores
        displayListView(storeSearchResults);

        final EditText searchName = (EditText)findViewById(R.id.selectStoreNameText);
        final EditText searchAddress = (EditText)findViewById(R.id.selectStoreAddressText);
        addSearchBarListener(searchName);
        addSearchBarListener(searchAddress);    //Add listeners to both text fields
    }
    private void displayListView(ArrayList<Store> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.storename_address_itemprice, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewselectstore);
        listView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<Store> {
        Context context;
        int textViewResourceId;
        private ArrayList<Store> storeList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Store> storeList) {
            super(context, textViewResourceId, storeList);
            this.textViewResourceId = textViewResourceId;
            this.storeList = new ArrayList<Store>();
            this.storeList.addAll(storeList);
            this.context = context;
        }
        private class ViewHolder {
            TextView name;
            TextView address;
            TextView itemPrice;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.storename_address_itemprice, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.storeName);
                holder.address = (TextView) convertView.findViewById(R.id.storeAddress);
                holder.itemPrice = (TextView) convertView.findViewById(R.id.selectedProductPriceInStore);

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

            Store store = storeList.get(position);
            //holder.check.setChecked(store.getChecked());                      //Ignore this
            //holder.check.setTag(store);
            holder.name.setText(store.getName());
            holder.address.setText(store.getAddress());
            holder.itemPrice.setText("1234");           //TODO implement items price in the specific store

            return convertView;
        }
    }

    private void addSearchBarListener(EditText textFieldForListener) {                               //Updates results in ListView
        final EditText searchName = (EditText)findViewById(R.id.selectStoreNameText);
        final EditText searchAddress = (EditText)findViewById(R.id.selectStoreAddressText);

        textFieldForListener.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intent intentForSQL = new Intent(getApplicationContext(), QueryProcessingIntentService.class);
                intentForSQL.putExtra("type", "SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE");
                intentForSQL.putExtra("queryStoreName", searchName.getText().toString());     //TODO may need to turn to lowercase
                intentForSQL.putExtra("queryStoreAddress", searchAddress.getText().toString());
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SelectStoreActivity");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    BroadcastReceiver SelectStoreList = new BroadcastReceiver() {              //Receives an ArrayList from QueryProcessingIntentService
        @Override
        public void onReceive(Context context, Intent intent) {
            storeSearchResults.clear();
            storeSearchResults.addAll((ArrayList<Store>) intent.getSerializableExtra("ArrayList<Store>"));
            displayListView(storeSearchResults);                 //TODO Maybe not a good way to update ListView
        }

    };

    BroadcastReceiver SelectStoreSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent();
            intentForService.putExtra("Cursor", "PLACEHOLDER");     //TODO use real cursor
            intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE");
            startService(intent);
        }
    };
}
