package com.rock.werool.piensunmaize.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rock.werool.piensunmaize.R;

import java.util.ArrayList;

public class SearchByProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_product);
    }
    private void displayListView() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Apple"));
        products.add(new Product("Orange"));
        products.add(new Product("Pear"));
        products.add(new Product("Banana"));

        MyCustomAdapter dataAdapter = new MyCustomAdapter(this, R.layout.checkbox_list, products);
        ListView listView = (ListView)findViewById(R.id.listviewproduct);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Product product = (Product) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + product.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private class MyCustomAdapter extends ArrayAdapter<Product> {

        private ArrayList<Product> productList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Product> productList) {
            super(context, textViewResourceId, productList);
            this.productList = new ArrayList<Product>();
            this.productList.addAll(productList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.product_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Product product = (Product) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        product.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Product product = productList.get(position);
            //holder.code.setText(" (" +  product.getCode() + ")");
            holder.name.setText(product.getName());
            holder.name.setChecked(product.isChecked());
            holder.name.setTag(product);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Product> productList = dataAdapter.productList;
                for(int i=0;i<productList.size();i++){
                    Product product = productList.get(i);
                    if(product.isSelected()){
                        responseText.append("\n" + product.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}
}
