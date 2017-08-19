package com.rock.werool.piensunmaize.search.by_store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.Store;
import com.rock.werool.piensunmaize.search.by_product.SelectStoreActivity;

import java.util.ArrayList;


public class SearchByStoreActivity extends AppCompatActivity {      //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Store> stores = new ArrayList<>();
    ArrayList<Store> storeSearchResults = new ArrayList<>();            //ListView uses storeSearchResults instead of stores!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_store);

        stores.add(new Store("Rimi first", "Somewhere 24"));          //TODO Implement local database query and format the data into ArrayList<Store>
        stores.add(new Store("Maxima second", "Anywhere 42"));
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
        stores.add(new Store("Rimi second last", "Somewhere 24"));
        stores.add(new Store("Maxima last", "Anywhere 42"));

        storeSearchResults.addAll(stores);                  //ListView initially shows all stores
        displayListView(storeSearchResults);
        addSearchBarListener();
    }
    private void displayListView(ArrayList<Store> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.storename_address, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewsearchstore);
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

                Store store = storeList.get(position);
                //holder.check.setChecked(store.getChecked());                      //Ignore this
                //holder.check.setTag(store);
                holder.name.setText(store.getName());
                holder.address.setText(store.getAddress());

                final String clickedStoreName = holder.name.getText().toString();
                final String clickedStoreAddress = holder.address.getText().toString();

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        //final String clickedStoreName = holder.name.toString();
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                holder.address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        //final String clickedStoreName = holder.name.toString();
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue)
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        //final String clickedStoreName = holder.name.toString();
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
            Store store1 = storeList.get(position);
            //holder.check.setChecked(store.getChecked());                      //Ignore this
            //holder.check.setTag(store);
            holder.name.setText(store1.getName());
            holder.address.setText(store1.getAddress());
            return convertView;     //FIXME listView is not ordered and the entries change while not visible
        }
    }

    private void addSearchBarListener() {                               //Updates results in ListView
        final EditText search = (EditText)findViewById(R.id.searchStoreText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                storeSearchResults.clear();                           //Clears results so the right ones could be readded
                for(int n = 0; n < stores.size(); n++) {
                    if(stores.get(n).getName().toLowerCase().matches(".*" + search.getText().toString().toLowerCase() + ".*")) {     ////.matches() is a regular expression
                        storeSearchResults.add(stores.get(n));                          //If product name matches. Not case or index sensitive
                    }
                }
                displayListView(storeSearchResults);                 //TODO Maybe not a good way to update ListView
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
