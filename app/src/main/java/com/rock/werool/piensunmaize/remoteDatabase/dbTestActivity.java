package com.rock.werool.piensunmaize.remoteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.vision.text.Text;
import com.rock.werool.piensunmaize.R;

import java.util.ArrayList;

public class dbTestActivity extends AppCompatActivity {

    RemoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);


        db = new RemoteDatabase(getResources().getString(R.string.database_url), this);

        final Button button = (Button)findViewById(R.id.btn_db_test);
        final TextView text = (TextView)findViewById(R.id.txt_db_test);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.FindStoreByNameLocationAndProduct(new Product(78,"", "", "", 0), "max", "aug", new IDatabaseResponseHandler<StoreProductPrice>() {
                    @Override
                    public void onArrive(ArrayList<StoreProductPrice> data) {
                        text.setText(data.get(0).getProduct().getName() + ",  " + data.get(0).getStore().getLocation());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        text.setText(error.getMessage());
                    }
                });
            }
        });





    }
}
