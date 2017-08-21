package com.rock.werool.piensunmaize.shoppingList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.search.Product;
import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    ListAdapter listAdapter;
    // TODO: add list to hold multiple shopping lists (separate list for each store)
    ArrayList<Product> shoppingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Store shopping list in the local DB??
        // TODO: implement a method + button to add products to the shopping list
        // TODO: implement a method + button to add a shopping list for a separate store
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

        displayListView(shoppingList);
    }

    private void displayListView(ArrayList<Product> inputList) {
        ListView productList = (ListView)findViewById(R.id.productListView);
        listAdapter = new ListAdapter(productList.getContext(), R.layout.itemname_price, shoppingList);
        productList.setAdapter(listAdapter);
    }

    private class ListAdapter extends ArrayAdapter<Product> {
        private ArrayList<Product> productList;

        public ListAdapter(Context context, int textViewResourceId,
                               ArrayList<Product> productList) {
            super(context, textViewResourceId, productList);
            this.productList = new ArrayList<Product>();
            this.productList.addAll(productList);
        }
        // TODO: finish this method
    }
}
