package com.rock.werool.piensunmaize.shoppingList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    CustomAdapter listAdapter;
    // TODO: ? store/access shopping list in/from the local DB
    ArrayList<Product> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // TEMPORARY ITEMS
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "21"));
        shoppingList.add(new Product("Orange", "42"));
        shoppingList.add(new Product("Apple", "42"));

        displayListView(shoppingList);
    }

    private void displayListView(ArrayList<Product> inputList) {
        ListView productList = (ListView) findViewById(R.id.productListView);
        listAdapter = new CustomAdapter(shoppingList, productList.getContext());
        productList.setAdapter(listAdapter);
    }

    private class CustomAdapter extends BaseAdapter implements ListAdapter {

        private List<Product> list = new ArrayList<>();
        private Context context;

        public CustomAdapter(ArrayList<Product> list, Context context) {
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
            listItemPrice.setText((list.get(position).getPrice()));

            ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }

    // TODO: add boxes to hold total price of the list
    // TODO: method + button for clearing all items
    // Clear all deletes shopping list from local DB
    // TODO: implement a method to add products to the shopping list
    // Clicking the button could open the product searching activity.
}
