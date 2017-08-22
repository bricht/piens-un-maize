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
import android.widget.ListView;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;
import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;


public class SearchByStoreActivity extends AppCompatActivity {      //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Store> stores = new ArrayList<>();
    ArrayList<Store> storeSearchResults = new ArrayList<>();            //ListView uses storeSearchResults instead of stores!
    String[][] array;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(SearchStoreSQL , new IntentFilter ("QUERY_RESULT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(SearchStoreSQL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_store);
        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, (String) null);     //TODO may need to turn to lowercase
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, "");       //All stores
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, "");     //All addresses
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByStoreActivity");

        final EditText searchStoreName = (EditText) findViewById(R.id.searchStoreNameText);
        final EditText searchStoreAddress = (EditText) findViewById(R.id.searchStoreAddressText);
        addSearchBarListener(searchStoreName);
        addSearchBarListener(searchStoreAddress);

    }
    private void displayListView(ArrayList<String> inputArray) {

        dataAdapter = new MyCustomAdapter(this, R.layout.storename_address, inputArray);
        ListView listView = (ListView)findViewById(R.id.listviewsearchstore);
        listView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<String> {
        Context context;
        int textViewResourceId;
        private ArrayList<String> storeArray;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> storeList) {
            super(context, textViewResourceId, storeList);
            this.textViewResourceId = textViewResourceId;
            this.storeArray = storeList;
            this.context = context;
        }
        private class ViewHolder {
            TextView name;
            TextView address;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.storename_address, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.storeName);
                holder.address = (TextView) convertView.findViewById(R.id.storeAddress);

                convertView.setTag(holder);                             //Important! Stores the holder in the View (row)

                holder.name.setText(array[position][1]);
                holder.address.setText(array[position][2]);

                final String clickedStoreName = holder.name.getText().toString();
                final String clickedStoreAddress = holder.address.getText().toString();

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                holder.address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
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
                holder = (ViewHolder) convertView.getTag();                     //If row is already created then get the holder from it
            }
            //holder.check.setChecked(store.getChecked());                      //Ignore this
            //holder.check.setTag(store);
            holder.name.setText(array[position][1]);
            holder.address.setText(array[position][2]);
            return convertView;
        }
    }

    private void addSearchBarListener(EditText textFieldForListener) {                               //Updates results in ListView
        final EditText searchStoreName = (EditText)findViewById(R.id.searchStoreNameText);
        final EditText searchStoreAddress = (EditText)findViewById(R.id.searchStoreAddressText);

        textFieldForListener.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
                intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
                intentForSQL.putExtra(SQLiteQuery.SRC_NAME, (String) null);     //TODO may need to turn to lowercase
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, searchStoreName.getText().toString());
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, searchStoreAddress.getText().toString());
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByStoreActivity");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    /*
    BroadcastReceiver SearchStoreList = new BroadcastReceiver() {              //Receives an ArrayList from QueryProcessingIntentService
        @Override
        public void onReceive(Context context, Intent intent) {
            storeSearchResults.clear();
            storeSearchResults.addAll((ArrayList<Store>) intent.getSerializableExtra("ArrayList<Store>"));
            displayListView(storeSearchResults);                 //TODO Maybe not a good way to update ListView
        }

    };
    */
    BroadcastReceiver SearchStoreSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intentForService = new Intent();
            intentForService.putExtra("Cursor", "PLACEHOLDER");     //TODO use real cursor
            intentForService.putExtra("currentQuery", "SEND_STORENAME_GET_PRODUCTNAME_PRODUCTPRICE");
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
