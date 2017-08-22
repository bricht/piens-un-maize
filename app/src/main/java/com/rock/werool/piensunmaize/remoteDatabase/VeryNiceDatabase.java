package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by guntt on 21.08.2017.
 */

public class VeryNiceDatabase {

    public static final String REMOTE_DB_URL = "http://www.zesloka.tk/piens_un_maize_db/";

    private RemoteDatabase remoteDb;

    public VeryNiceDatabase(Context context) {
        remoteDb = new RemoteDatabase(REMOTE_DB_URL, context);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };



    private void findProductByBarcode(String barcode, final IDatabaseResponseHandler<Product> responseHandler) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // to do find in local databse
                // if not find
                //serach in remote database

                ArrayList<Product> products = new ArrayList<Product>();
                Product someProduct = new Product(1, "test this", "test that", "test everything", 0 );
                products.add(new Product(1, "test this", "test that", "test everything", 0 ));
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", products);
                msg.setData(bundle);
                handler.sendMessage(msg);
        }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private class FindProductByName  implements Runnable {

        private Handler handler;

        public FindProductByName(Handler h) {
            this.handler = h;
        }



        @Override
        public void run() {

        }

    }

    private class OnProdcutHandler extends Handler {


    }


}
