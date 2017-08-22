package com.rock.werool.piensunmaize.search;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Martin on 2017.08.19..
 */

public class QueryProcessingIntentService extends IntentService{      //TODO Finish implementing this class
    /*
    Query types:
        SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE,
        SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE,
        SEND_STORENAME_ADDRESS_GET_STORENAME_ADDRESS,
        SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_PRODUCTNAME_PRODUCTPRICE
    */
    String currentQuery;

    public QueryProcessingIntentService() {
        super("QueryProcessingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent recievedIntent) {
        if (recievedIntent.hasExtra("currentQuery") && recievedIntent.hasExtra("String[][]")) {
            currentQuery = recievedIntent.getStringExtra("currentQuery");
            String [][] array = (String[][]) recievedIntent.getSerializableExtra("String[][]");

            switch (currentQuery) {
                case ("SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE") : {      //1.2 SearchByProductActivity
                    ArrayList<Product> products = new ArrayList<>();
                    if (array != null) {
                        for (String[] product : array) {
                            products.add(new Product(product[0], product[1]));  //Name and average price
                        }
                    }
                    Intent intent = new Intent();
                    intent.setAction("ProcessedQueryResult");
                    intent.putExtra("ArrayList<Product>", (Serializable) products);
                    sendBroadcast(intent);
                    break;
                }
                case ("SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE") : {        //1.2.1 SelectStoreActivity
                    ArrayList<Store> stores = new ArrayList<>();
                    if (array != null) {
                        for (String[] store : array) {
                            stores.add(new Store(store[1], store[2], store[3]));  //StoreName, address, price (productName, storeName, storeAddress, productprice)
                        }
                    }
                    Intent intent = new Intent();
                    intent.setAction("ProcessedQueryResult");
                    intent.putExtra("ArrayList<Store>", stores);
                    sendBroadcast(intent);
                    break;
                }
                case ("SEND_STORENAME_ADDRESS_GET_STORENAME_ADDRESS") : {        //1.3 SearchByStoreActivity
                    ArrayList<Store> stores = new ArrayList<>();
                    if (array != null) {
                        for (String[] store : array) {
                            stores.add(new Store(store[1], store[2]));  //StoreName, address (productName, storeName, storeAddress, productprice)
                        }
                    }
                    Intent intent = new Intent();
                    intent.setAction("ProcessedQueryResult");
                    intent.putExtra("ArrayList<Store>", stores);
                    sendBroadcast(intent);
                    break;
                }
                case ("SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_PRODUCTNAME_PRODUCTPRICE") : {           //1.3.1 SelectProductActivity
                    ArrayList<Product> products = new ArrayList<>();
                    if (array != null) {
                        for (String[] product : array) {
                            products.add(new Product(product[0], product[1]));  //Name and price
                        }
                    }
                    Intent intent = new Intent();
                    intent.setAction("ProcessedQueryResult");
                    intent.putExtra("ArrayList<Product>", products);
                    sendBroadcast(intent);
                    break;
                }
            }
        }
    }


}
