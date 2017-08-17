package com.rock.werool.piensunmaize.search;

import android.content.Context;
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

import java.util.ArrayList;

public class SearchByProductActivity extends AppCompatActivity {              //TODO implement action for clicking on a row
    MyCustomAdapter dataAdapter;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Product> productSearchResults = new ArrayList<>();               //ListView uses productSearchResults instead of products!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_product);

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
        addSearchBarListener();
    }
    private void displayListView(ArrayList<Product> inputList) {

        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_price, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewproduct);
        listView.setAdapter(dataAdapter);
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
            TextView price;
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
                holder.name = (TextView) convertView.findViewById(R.id.productName);
                holder.price = (TextView) convertView.findViewById(R.id.productPrice);
                convertView.setTag(holder);                     //Important! Stores the holder in the View (row)
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
            holder.price.setText(product.getPrice());
            //holder.check.setChecked(product.getChecked());                //Ignore this
            //holder.check.setTag(product);

            return convertView;
        }
    }

    private void addSearchBarListener() {                       //Updates results in ListView
        final EditText search = (EditText)findViewById(R.id.searchStoreText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productSearchResults.clear();                      //Clears results so the right ones could be readded
                for(int n = 0; n < products.size(); n++) {
                    if(products.get(n).name.toLowerCase().matches(".*" + search.getText().toString().toLowerCase() + ".*")) {  //.matches() is a regular expression
                        productSearchResults.add(products.get(n));    //If product name matches. Not case or index sensitive
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

