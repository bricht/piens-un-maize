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
        SEND_STORENAME_GET_PRODUCTNAME_PRODUCTPRICE,
        SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_PRODUCTNAME_PRODUCTPRICE
    */
    String currentQuery;

    public QueryProcessingIntentService() {
        super("QueryProcessingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent recievedIntent) {
        if (recievedIntent.hasExtra("currentQuery") && recievedIntent.hasExtra("Cursor")) {
            currentQuery = recievedIntent.getStringExtra("currentQuery");
            Cursor cursor = (Cursor) recievedIntent.getSerializableExtra("Cursor");

            switch (currentQuery) {
                case ("SEND_PRODUCTNAME_GET_PRODUCTNAME_AVERAGEPRICE") : {      //1.2 SearchByProductActivity

                    ArrayList<Product> products = new ArrayList<>();
                    while (cursor.moveToNext()){
                        products.add(new Product(cursor.getString(1), cursor.getString(2)));    //TODO may be incorrect
                    }
                    Intent sendableIntent = new Intent();
                    sendableIntent.setAction("ProcessedQueryResult");
                    sendableIntent.putExtra("ArrayList<Product>", (Serializable) products);
                    sendBroadcast(sendableIntent);
                    break;
                }
                case ("SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_STORENAME_STOREADDRESS_PRODUCTPRICE") : {        //1.2.1 SelectStoreActivity

                    break;
                }
                case ("SEND_STORENAME_GET_PRODUCTNAME_PRODUCTPRICE") : {        //1.3 SearchByStoreActivity

                    break;
                }
                case ("SEND_PRODUCTNAME_STORENAME_STOREADDRESS_GET_PRODUCTNAME_PRODUCTPRICE") : {           //1.3.1 SelectProductActivity

                    break;
                }
            }
        }
    }


}
