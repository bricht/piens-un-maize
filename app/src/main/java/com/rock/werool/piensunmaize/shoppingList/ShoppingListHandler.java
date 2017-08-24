package com.rock.werool.piensunmaize.shoppingList;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.rock.werool.piensunmaize.search.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class ShoppingListHandler {
    final private String fileName = "shopping_list.txt";
    private Context context;
    private ArrayList<Product> shoppingList;

    public ShoppingListHandler(Context context) {
        this.context = context;
        shoppingList = new ArrayList<>();

        createFile();
    }

    public ArrayList<Product> getShoppingList() {
        return shoppingList;
    }

    // Method ensures there is shopping_list.txt file in devices internal storage
    private void createFile() {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                IOErrorDialog("ShoppingListHandler()", e.getMessage());
            }
        }
    }

    public void readFile() {
        createFile();

        String temp;
        String[] data;

        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((temp = br.readLine()) != null) {
                data = temp.split("\\^");
                // Ensures that price is allways delimited by '.' (for double parsing)
                if (data[1].contains(",")) {
                    data[1] = data[1].replace(',', '.');
                }
                shoppingList.add(new Product(data[0], data[1]));
            }
            fis.close();
        } catch (FileNotFoundException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        } catch (IOException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        }
    }

    public void add(String product, String price) {
        createFile();
        shoppingList.add(shoppingList.size(), new Product(product, price));
        String data = product + "^" + price + "\n";

        try {
            FileOutputStream fos = context.openFileOutput(fileName, MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        } catch (IOException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        }
    }

    public void remove(int id) {
        context.deleteFile(fileName);
        createFile();
        shoppingList.remove(id);

        String data;

        try {
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);

            for (Product p : shoppingList) {
                data = p.getName() + "^" + p.getPrice() + "\n";
                fos.write(data.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        } catch (IOException e) {
            IOErrorDialog("ShoppingListHandler()", e.getMessage());
        }
    }

    public void clearFile () {
        context.deleteFile(fileName);
        shoppingList.clear();
    }

    public double calculateTotalPrice () {
        double total = 0;
        for (Product product : shoppingList) {
            total += Double.parseDouble(product.getPrice());
        }
        return total;
    }

    // Method for displaying file I/O error messages
    private void IOErrorDialog(String method, String message) {
        // Message for DEBUG log
        Log.d("EXCEPTION: " + method, message);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(method);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setNeutralButton("OK", null);
        alertDialog.show();
    }
}
