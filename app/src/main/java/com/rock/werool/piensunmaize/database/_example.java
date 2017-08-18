package com.rock.werool.piensunmaize.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;

import com.android.volley.VolleyError;
import com.rock.werool.piensunmaize.R;

import java.io.Console;
import java.util.ArrayList;

public class _example extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDatabase db = new RemoteDatabase("", this);
        db.FindProductByCategory("piens", new IRemoteDatabaseResponseHandler<Product>() {
            @Override
            public void onArrive(ArrayList<Product> data) {
                //INFO: data is array returned from database
                // with Product objects what matches keyword;
            }

            @Override
            public void onError(VolleyError error) {
                //INFO: this method is only triggered when some http connection
                //      error is encountered
                Log.d("CONNECTION ERROR", "this is really bad");
            }

            //UPCOMING: method for sql error handling. - really forgot about it.
        });

        db.AddBarcode(new Barcode("45275856723", /*Product ?ID*/ 1), new IRemoteDatabaseResponseHandler<String>() {
            @Override
            public void onArrive(ArrayList<String> data) {
                //INFO: normally only one string entry should be returned.
                //      entry content is respone form mysql database
            }

            @Override
            public void onError(VolleyError error) {

            }

            //UPCOMING: method for sql error handling. - really forgot about it.
        });


    }
}
