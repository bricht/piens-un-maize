package com.rock.werool.piensunmaize.barcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.add.FillWithHandActivity;
import com.rock.werool.piensunmaize.search.by_product.SearchByProductActivity;

import java.util.ArrayList;

/**
 * Created by Martin on 15-Aug-17.
 */

public class BarcodeAction {
    protected Context context;
    /*
    public enum BarcodeDetectedAction {
        UPDATE_PRODUCT, FIND_PRODUCT_INFO, FIND_PRODUCT_COMPARE,
    }
    */

    private String necessaryAction;       //Defines which action to execute after barcode is read
    private String[][] array;

    public String  getNecessaryAction() {
        return necessaryAction;
    }

    public void setContextAndNecessaryAction(Context context, String necessaryAction) {
        this.context = context;
        this.necessaryAction = necessaryAction;
        this.context.registerReceiver(BarcodeSQL , new IntentFilter("QUERY_RESULT"));
    }

    public void executeActionFromBarcode(String barcodeContent) {
        switch (this.necessaryAction) {
            case "UPDATE_PRODUCT" : {
                Intent intent = new Intent(context, FillWithHandActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("scannedProductName", "Placeholder Name");
                intent.putExtra("scannedProductCategory", "Placeholder Category");
                context.startActivity(intent);
                this.context.registerReceiver(BarcodeSQL , new IntentFilter("QUERY_RESULT"));
                break;
            } case "FIND_PRODUCT_INFO": {
                Intent intent = new Intent(context, SearchByProductActivity.class);     //TODO get product name from barcode id
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("scannedProductName", "Placeholder Name");
                context.startActivity(intent);
                break;
            } case "FIND_PRODUCT_COMPARE": {        //Maybe not needed

                break;
            } default: {

            }
        }
    }
    BroadcastReceiver BarcodeSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bun = intent.getBundleExtra(SQLiteQuery.QUERY_RESULT);
            array = (String[][]) bun.getSerializable("String[][]");
        }
    };
}
