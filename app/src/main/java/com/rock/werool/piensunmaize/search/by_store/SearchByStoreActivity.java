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

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.add.FillWithHandActivity;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.search.QueryProcessingIntentService;
import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;


public class SearchByStoreActivity extends AppCompatActivity {      //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Store> stores = new ArrayList<>();
    ArrayList<Store> storeSearchResults = new ArrayList<>();            //ListView uses storeSearchResults instead of stores!
    String[][] array;
    RemoteDatabase remoteDB;
    boolean selectStoreFromEdit = false;
    Product productForEdit;
    ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store> storesListForEdit;
    String scannedProductBarcode;
    Boolean addNewForEdit;

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
        remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", this);

        if (getIntent().hasExtra("selectStoreFromEdit")) {              //Checks if called from add/edit product activity
            selectStoreFromEdit = getIntent().getExtras().getBoolean("selectStoreFromEdit");
            productForEdit = (Product) getIntent().getExtras().getSerializable("Product");
            scannedProductBarcode = getIntent().getExtras().getString("scannedProductBarcode");
            addNewForEdit = getIntent().getExtras().getBoolean("addNew");
        }

        remoteDB.FindStoreByNameAndLocation("", "", new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Store>() {
            @Override
            public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store> data) {
                array = new String[data.size()][4];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    array[i][1] = data.get(i).getName();
                    array[i][2] = data.get(i).getLocation();
                    array[i][3] = Integer.toString(data.get(i).getId());
                    al.add("q");
                }
                storesListForEdit = new ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store>();
                storesListForEdit.addAll(data);
                displayListView(al);
            }

            @Override
            public void onError(VolleyError error) {
                String message = error.getMessage();
                array = null;
            }
        });

        /*
        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, (String) null);     //TODO may need to turn to lowercase
        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, "");       //All stores
        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, "");     //All addresses
        startService(intentForSQL);             //Starts SQLite intent service
        Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByStoreActivity");
        */

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
            com.rock.werool.piensunmaize.remoteDatabase.Store store;
            int storeId;
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
                holder.storeId = Integer.parseInt(array[position][3]);
                holder.store = storesListForEdit.get(position);

                final String clickedStoreName = holder.name.getText().toString();
                final String clickedStoreAddress = holder.address.getText().toString();
                final int clickedStoreId = holder.storeId;
                final com.rock.werool.piensunmaize.remoteDatabase.Store storeForEdit = holder.store;


                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(selectStoreFromEdit) {              //Decides which activity to call depending on the passed parameter
                            intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        }
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        intent.putExtra("scannedProductBarcode", scannedProductBarcode);    //For FillWitHandActivity
                        intent.putExtra("Product", productForEdit);
                        intent.putExtra("Store", storeForEdit);
                        intent.putExtra("addNew", addNewForEdit);
                        startActivity(intent);
                    }
                });
                holder.address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(selectStoreFromEdit) {              //Decides which activity to call depending on the passed parameter
                            intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        }
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        intent.putExtra("scannedProductBarcode", scannedProductBarcode);    //For FillWitHandActivity
                        intent.putExtra("Product", productForEdit);
                        intent.putExtra("Store", storeForEdit);
                        intent.putExtra("addNew", addNewForEdit);

                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(selectStoreFromEdit) {              //Decides which activity to call depending on the passed parameter
                            intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        }
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedStoreId", clickedStoreId);
                        intent.putExtra("scannedProductBarcode", scannedProductBarcode);    //For FillWitHandActivity
                        intent.putExtra("Product", productForEdit);
                        intent.putExtra("Store", storeForEdit);
                        intent.putExtra("addNew", addNewForEdit);

                        startActivity(intent);
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();                     //If row is already created then get the holder from it
            }
            holder.name.setText(array[position][1]);
            holder.address.setText(array[position][2]);
            holder.storeId = Integer.parseInt(array[position][3]);
            holder.store = storesListForEdit.get(position);
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
                /*
                Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
                intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRICE);     //Price for specific product
                intentForSQL.putExtra(SQLiteQuery.SRC_NAME, (String) null);     //TODO may need to turn to lowercase
                intentForSQL.putExtra(SQLiteQuery.SRC_STORE, searchStoreName.getText().toString());
                intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, searchStoreAddress.getText().toString());
                startService(intentForSQL);             //Starts SQLite intent service
                Log.v("BroadcastDebug", "SQLite query broadcast sent from SearchByStoreActivity");
                */
                remoteDB.FindStoreByNameAndLocation(searchStoreName.getText().toString(), searchStoreAddress.getText().toString(), new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Store>() {
                    @Override
                    public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store> data) {
                        array = new String[data.size()][4];
                        ArrayList<String> al = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            array[i][1] = data.get(i).getName();
                            array[i][2] = data.get(i).getLocation();
                            array[i][3] = Integer.toString(data.get(i).getId());
                            al.add("q");
                        }
                        storesListForEdit = new ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Store>();
                        storesListForEdit.addAll(data);
                        displayListView(al);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        String message = error.getMessage();
                        array = null;
                    }
                });
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
