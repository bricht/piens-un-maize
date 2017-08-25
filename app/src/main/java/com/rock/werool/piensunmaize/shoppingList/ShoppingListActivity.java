package com.rock.werool.piensunmaize.shoppingList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

/**
 * Activity for displaying and manipulating user's shopping list.
 * Shopping list is displayed in a ListView component. Total price of items
 * in the shopping list is displayed in a TextView component. Each list entry
 * has added products name and price. It is possible to delete a list entry by
 * pressing the "X" button next to product details. Button "Clear" deletes
 * all entries from the shopping list. Shopping list is stored in a text
 * file in devices internal storage. This file is generated and updated by
 * {@link ShoppingListHandler} class.
 * @author Lauris Lazda
 */
public class ShoppingListActivity extends AppCompatActivity {
    private Button clearButton;
    ShoppingListHandler shoppingListHandler;
    private double total = 0;

    /**
     * Loads shopping list layout. Initialises title bar and Clear button.
     * Creates {@link ShoppingListHandler} object and gets all entries stored
     * in the shopping list file. Calls {@link #displayShoppingList()} method.
     * Gets total price of stored items and calls {@link #displayTotal(double)}
     * method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //SHOW ICON
        getSupportActionBar().setLogo(R.mipmap.applogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.applogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // /SHOW ICON
        Spannable word = new SpannableString("bread n milk");
        word.setSpan(new ForegroundColorSpan(Color.rgb(177, 227, 251)), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(word);

        clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0;
                displayTotal(total);
                shoppingListHandler.clearFile();
                displayShoppingList();
            }
        });

        shoppingListHandler = new ShoppingListHandler(this);
        shoppingListHandler.readFile();
        displayShoppingList();

        total = shoppingListHandler.calculateTotalPrice();
        displayTotal(total);
    }

    /**
     * Initialises ListView component for shoving content of shopping list file.
     * Creates object of inner class {@link CustomAdapter} and applies it to the
     * ListView component.
     */
    private void displayShoppingList() {
        ListView productList = (ListView) findViewById(R.id.productListView);
        CustomAdapter listAdapter = new CustomAdapter(productList.getContext());
        productList.setAdapter(listAdapter);
    }

    /**
     * Gets total price, formats it to show 2 decimal places and displays the
     * result in TextView element.
     * @param totalPrice total price of the items in the shopping list
     */
    private void displayTotal(double totalPrice) {
        String total = String.format("%.2f", totalPrice);
        TextView textView = (TextView) findViewById(R.id.totalPrice);
        textView.setText("Total price: " + total);
    }

    private class CustomAdapter extends BaseAdapter implements ListAdapter {
        private Context context;

        CustomAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return shoppingListHandler.getShoppingList().size();
        }

        @Override
        public Object getItem(int position) {
            return shoppingListHandler.getShoppingList().get(position);
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

            final TextView listItemName = (TextView) view.findViewById(R.id.productName);
            listItemName.setText(shoppingListHandler.getShoppingList().get(position).getName());
            TextView listItemPrice = (TextView) view.findViewById(R.id.productPrice);
            // Should be seeing decimal numbers delimited by '.' or ',', depending on locale
            double price = Double.parseDouble(shoppingListHandler.getShoppingList().get(position).getPrice());
            listItemPrice.setText(String.format("%.2f", price));

            ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    total -= Double.parseDouble(shoppingListHandler.getShoppingList().get(position).getPrice());
                    displayTotal(total);
                    shoppingListHandler.remove(position);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }
}
