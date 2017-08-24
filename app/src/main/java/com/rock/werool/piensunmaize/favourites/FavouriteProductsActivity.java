package com.rock.werool.piensunmaize.favourites;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.remoteDatabase.Store;
import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;
//import com.rock.werool.piensunmaize.search.by_store.SelectProductActivity;
//import com.rock.werool.piensunmaize.search.Store;

import java.util.ArrayList;

public class FavouriteProductsActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter;
    ArrayList<FavouriteProduct> products = new ArrayList<>();
    ArrayList<FavouriteProduct> productSearchResults = new ArrayList<>();            //ListView uses productSearchResults instead of stores!
    String [][] array;
    RemoteDatabase remoteDB;

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(SearchProductList , new IntentFilter ("ProcessedQueryResult"));         //Registers BroadcastReceivers
        //registerReceiver(SearchProductSQL , new IntentFilter("QUERY_RESULT"));  //<------------- NETIEK LIETOTS LAIKAM
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(SearchProductList);         //Unregisters BroadcastReceivers
        //unregisterReceiver(SearchProductSQL);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_products);
        remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", this);



//        remoteDB.FindProductByName("", new IDatabaseResponseHandler<com.rock.werool.piensunmaize.remoteDatabase.Product>() {
//            @Override
//            public void onArrive(ArrayList<com.rock.werool.piensunmaize.remoteDatabase.Product> data) {
//                array = new String[data.size()][3];
//                ArrayList<String> al = new ArrayList<>();
//                for (int i = 0; i < data.size(); i++) {
//                    array[i][0] = data.get(i).getName();
//                    array[i][1] = Double.toString(data.get(i).getAvaragePricePrice());
//                    array[i][2] = Long.toString(data.get(i).getId());
//                    al.add("q");
//                }
//                displayListView(al);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//
//            }
//        });

        remoteDB.GetFavoriteProducts(new IDatabaseResponseHandler<Product>() {
            @Override
            public void onArrive(ArrayList<Product> data) {
                array = new String[data.size()][3];
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    array[i][0] = data.get(i).getName();
                    array[i][1] = Double.toString(data.get(i).getAvaragePricePrice());
                    array[i][2] = Long.toString(data.get(i).getId());
                    al.add("q");
                }
                displayListView(al);
            }
            @Override
            public void onError(VolleyError error) {

            }
        });





//        Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
//        intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
//        intentForSQL.putExtra(SQLiteQuery.SRC_NAME, "");                                    //All products
//        intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);                        //No store name
//        intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);                      //No store address
//        startService(intentForSQL);                                                             //Starts SQLite intent service

        //productSearchResults.addAll(products);                                              //ListView initially shows all stores
        //displayListView(productSearchResults);

        // addSearchBarListener();

    }
    private void displayListView(ArrayList<String> inputList) {
        dataAdapter = new MyCustomAdapter(this, R.layout.itemname_price_remove, inputList);
        ListView listView = (ListView)findViewById(R.id.listviewfavouriteproduct);
        listView.setAdapter(dataAdapter);
    }
    //done so far jap
    private class MyCustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> productList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> productList) {
            super(context, textViewResourceId, productList);
            this.productList = productList;
        }

        private class ViewHolder {
            TextView name;
            TextView averagePrice;
            ImageView remove;
            //TextView averagePrice;
            long productId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.itemname_price_remove, null);

                holder = new ViewHolder();                              //makes holder object with the values of the fields
                holder.name = (TextView) convertView.findViewById(R.id.productName);
                holder.averagePrice = (TextView) convertView.findViewById(R.id.productPrice);
                holder.remove = (ImageView) convertView.findViewById(R.id.delete_btn);

                holder.name.setText(array[position][0]);
                holder.averagePrice.setText(array[position][1]);
                holder.productId = Long.parseLong(array[position][2]);

                final String clickedProductName = holder.name.getText().toString();
                final String clickedProductAveragePrice = holder.averagePrice.getText().toString();
                final long clickedProductId = holder.productId;
                final int positionOfElement = position;

                convertView.setTag(holder);                             //Important! Stores the holder in the View (row)

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);  //TODO For some reason Martins has SelectStoreActivity.class here
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedProductId", clickedProductId);
                        startActivity(intent);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SelectProductActivity.class);  //TODO For some reason Martins has SelectStoreActivity.class here
                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
                        intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
                        intent.putExtra("clickedProductId", clickedProductId);
                        startActivity(intent);
                    }
                });

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    double clickedProductAveragePriceD = Double.parseDouble(clickedProductAveragePrice);
                    Product tempProduct = new Product(clickedProductId, clickedProductName, "dummy", "dummy", clickedProductAveragePriceD);
                    @Override
                    public void onClick(View view) {        //TODO implement actions on click

                        remoteDB.DeleteFavoriteProduct(tempProduct, new IDatabaseResponseHandler<String>() {
                            @Override
                            public void onArrive(ArrayList<String> data) {

                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });

                        productList.remove(positionOfElement);
                        String[][] array1 = new String[array.length-1][4];
                        for (int i = 0; i < array.length; i++) {
                            while (i != positionOfElement) {
                                array1[i][1] = array[i][1];
                                array1[i][2] = array[i][2];
                                array1[i][3] = array[i][3];
                            }
                        }
                        notifyDataSetChanged();

//                        Intent intent = new Intent(getApplicationContext(), SelectStoreActivity.class); ///////which class???
//                        intent.putExtra("clickedProductName", clickedProductName);      //Passes parameters to the activity
//                        //intent.putExtra("clickedProductAveragePrice", clickedProductAveragePrice);    //.putExtra(variableName, variableValue)
//                        intent.putExtra("clickedProductId", clickedProductId);
                    }
                });

            }


            else {
                holder = (ViewHolder) convertView.getTag();                         //If row is already created then get the holder from it
            }

            holder.name.setText(array[position][0]);
            //holder.averagePrice.setText(array[position][1]);

            return convertView;
        }
    }

//    private void addSearchBarListener() {                               //Updates results in ListView
//        final EditText search = (EditText)findViewById(R.id.searchFavouriteProductName);
//
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                productSearchResults.clear();                           //Clears results so the right ones could be readded
//                for(int n = 0; n < products.size(); n++) {
//                    if(products.get(n).getName().toLowerCase().matches(".*" + search.getText().toString().toLowerCase() + ".*")) {     ////.matches() is a regular expression
//                        productSearchResults.add(products.get(n));                          //If product name matches. Not case or index sensitive
//                    }
//                }
//                displayListView(productSearchResults);                 //TODO Maybe not a good way to update ListView
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }





//    BroadcastReceiver SearchProductSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //Intent intentForService = new Intent(getApplicationContext(), QueryProcessingIntentService.class);
//            Bundle bun = intent.getBundleExtra(SQLiteQuery.QUERY_RESULT);
//            array = (String[][]) bun.getSerializable("String[][]");
//            //intentForService.putExtra("String[][]", array);
//            //intentForService.putExtra("currentQuery", "SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE");
//            ArrayList<String> array1D = new ArrayList<>();
//            if (array != null) {
//                //array1D = new String[array.length];
//                for (int i = 0; i < array.length; i++) {
//                    array1D.add(array[i][0]);
//                }
//            }
//            displayListView(array1D);
//        }
//    };
}

