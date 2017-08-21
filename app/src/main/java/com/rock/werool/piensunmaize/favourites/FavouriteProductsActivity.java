package com.rock.werool.piensunmaize.favourites;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;
//import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;
//import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;

public class FavouriteProductsActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter;
    ArrayList<FavouriteProduct> products = new ArrayList<>();
    ArrayList<FavouriteProduct> productSearchResults = new ArrayList<>();            //ListView uses productSearchResults instead of stores!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_products);

        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));          //TODO Implement local database query and format the data into ArrayList<Store>
        products.add(new FavouriteProduct("Ma\nxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi\nRimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));
        products.add(new FavouriteProduct("Rimi", "Somewhere 24"));
        products.add(new FavouriteProduct("Maxima", "Anywhere 42"));

        productSearchResults.addAll(products);                  //ListView initially shows all stores
        displayListView(productSearchResults);
        addSearchBarListener();
    }
    private void displayListView(ArrayList<FavouriteProduct> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_remove, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewfavouriteproduct);
        listView.setAdapter(dataAdapter);
    }
    //done so far
    private class MyCustomAdapter extends ArrayAdapter<FavouriteProduct> {
        Context context;
        int textViewResourceId;
        private ArrayList<FavouriteProduct> storeList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<FavouriteProduct> storeList) {
            super(context, textViewResourceId, storeList);
            this.textViewResourceId = textViewResourceId;
            this.storeList = new ArrayList<FavouriteProduct>();
            this.storeList.addAll(storeList);
            this.context = context;
        }
        private class ViewHolder {
            TextView name;
         //   TextView address;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.itemname_remove, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.favProductName);
                //holder.price = (TextView) convertView.findViewById(R.id.favProductPrice); <<<<<<<<<---------TODO PRICE

                convertView.setTag(holder);                             //Important! Stores the holder in the View (row)

                FavouriteProduct product = storeList.get(position);
                //holder.check.setChecked(store.getChecked());                      //Ignore this
                //holder.check.setTag(store);
                holder.name.setText(product.getName());
                //holder.address.setText(store.getAddress());

                final String clickedStoreName = holder.name.getText().toString();
                //final String clickedStoreAddress = holder.price.getText().toString(); <<<<<<<<<---------TODO PRICE

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        //final String clickedStoreName = holder.name.toString();
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        //intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue) <<<<<<<<<---------TODO PRICE
                        startActivity(intent);
                    }
                });
//                holder.address.setOnClickListener(new View.OnClickListener() { <<<<<<<<<---------TODO PRICE
//                    @Override <<<<<<<<<---------TODO PRICE
//                    public void onClick(View view) { <<<<<<<<<---------TODO PRICE
//                        Intent intent = new Intent(getApplicationContext(), FavouriteProductsActivity.class);
//                        //final String clickedStoreName = holder.name.toString();
//                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
//                        //intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue) <<<<<<<<<---------TODO PRICE
//                        startActivity(intent);
//                    }
//                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);
                        //final String clickedStoreName = holder.name.toString();
                        intent.putExtra("clickedStoreName", clickedStoreName);      //Passes parameters to the activity
                        //intent.putExtra("clickedStoreAddress", clickedStoreAddress);    //.putExtra(variableName, variableValue) <<<<<<<<<---------TODO PRICE
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
            FavouriteProduct store1 = storeList.get(position);
            //holder.check.setChecked(store.getChecked());                      //Ignore this
            //holder.check.setTag(store);
            holder.name.setText(store1.getName());
            //holder.address.setText(store1.getAddress());                          <<<<<<<<<---------TODO PRICE
            return convertView;     //FIXME listView is not ordered and the entries change while not visible
        }
    }

    private void addSearchBarListener() {                               //Updates results in ListView
        final EditText search = (EditText)findViewById(R.id.searchFavouriteProductName);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productSearchResults.clear();                           //Clears results so the right ones could be readded
                for(int n = 0; n < products.size(); n++) {
                    if(products.get(n).getName().toLowerCase().matches(".*" + search.getText().toString().toLowerCase() + ".*")) {     ////.matches() is a regular expression
                        productSearchResults.add(products.get(n));                          //If product name matches. Not case or index sensitive
                    }
                }
                displayListView(productSearchResults);                 //TODO Maybe not a good way to update ListView
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}