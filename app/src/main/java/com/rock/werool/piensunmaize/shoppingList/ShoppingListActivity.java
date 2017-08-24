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
    ShoppingListHandler listHandler;
    private ArrayList<Product> shoppingList;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingList = new ArrayList<>();
        listHandler = new ShoppingListHandler(this, shoppingList);
        listHandler.readFile();
        displayShoppingList(shoppingList);

        for (Product product : shoppingList) {
            total += Double.parseDouble(product.getPrice());
        }
        displayTotal(total);

        final Button clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0;
                displayTotal(total);
                shoppingList.clear();
                displayShoppingList(shoppingList);
            }
        });
    }

    private void displayShoppingList(ArrayList<Product> inputList) {
        ListView productList = (ListView) findViewById(R.id.productListView);
        CustomAdapter listAdapter = new CustomAdapter(shoppingList, productList.getContext());
        productList.setAdapter(listAdapter);
    }

    private void displayTotal(double totalPrice) {
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
            // This should return ID of the record in local DB
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
}
