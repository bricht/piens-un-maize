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

public class ShoppingListActivity extends AppCompatActivity {
    private Button clearButton;
    ShoppingListHandler shoppingListHandler;
    private double total = 0;

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

        shoppingListHandler = new ShoppingListHandler(this);
        shoppingListHandler.readFile();
        displayShoppingList();

        total = shoppingListHandler.calculateTotalPrice();
        displayTotal(total);

        clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                total = 0;
                displayTotal(total);
                shoppingListHandler.clearFile();
                displayShoppingList();
            }
        });
    }

    private void displayShoppingList() {
        ListView productList = (ListView) findViewById(R.id.productListView);
        CustomAdapter listAdapter = new CustomAdapter(productList.getContext());
        productList.setAdapter(listAdapter);
    }

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
