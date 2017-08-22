package com.rock.werool.piensunmaize.shoppingList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private CustomAdapter listAdapter;
    // TODO: ? store/access shopping list in/from the local DB
    // TODO: change arrayList to an Array
    private ArrayList<Product> shoppingList = new ArrayList<>();
    private String[][] shoppingListArray;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // TEMPORARY ITEMS
        shoppingList.add(new Product("Orange", "42.00"));
        shoppingList.add(new Product("Apple", "21.00"));
        shoppingList.add(new Product("Orange", "42.00"));
        shoppingList.add(new Product("Apple", "21.00"));
        shoppingList.add(new Product("Orange", "42.01"));
        shoppingList.add(new Product("Apple", "21.01"));
        shoppingList.add(new Product("Orange", "42.00"));
        shoppingList.add(new Product("Apple", "21.50"));
        shoppingList.add(new Product("Orange", "42.43"));
        shoppingList.add(new Product("Apple", "21.00"));
        shoppingList.add(new Product("Orange", "42.00"));
        shoppingList.add(new Product("Apple", "21.99"));
        shoppingList.add(new Product("Orange", "42.99"));
        shoppingList.add(new Product("Apple", "42.99"));

        for (Product product : shoppingList) {
            total += Double.parseDouble(product.getPrice());
        }

        displayListView(shoppingList);
        displayTotal(total);

        final Button clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0;
                displayTotal(total);
                shoppingList.clear();
                displayListView(shoppingList);
            }
        });
    }

    private void displayListView(ArrayList<Product> inputList) {
        ListView productList = (ListView) findViewById(R.id.productListView);
        listAdapter = new CustomAdapter(shoppingList, productList.getContext());
        productList.setAdapter(listAdapter);
    }

    private void displayTotal(double totalPrice){
        String total = String.format("%.2f", totalPrice);
        TextView textView = (TextView) findViewById(R.id.totalPrice);
        textView.setText("Total price: " + total);
    }

    private class CustomAdapter extends BaseAdapter implements ListAdapter {

        private List<Product> list = new ArrayList<>();
        private Context context;

        CustomAdapter(ArrayList<Product> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // This should return ID of the record in local DB (if there is an ID)
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.itemname_price_remove, null);
            }

            TextView listItemName = (TextView) view.findViewById(R.id.productName);
            listItemName.setText(list.get(position).getName());
            TextView listItemPrice = (TextView) view.findViewById(R.id.productPrice);
            // Should be seeing decimal numbers delimited by '.' or ',', depending on locale
            double price = Double.parseDouble(list.get(position).getPrice());
            listItemPrice.setText(String.format("%.2f", price));

            ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    total -= Double.parseDouble(list.get(position).getPrice());
                    displayTotal(total);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }

    // (maybe) Add method and button to add products to the shopping list
    // Clicking the button could open the product searching activity.
}
