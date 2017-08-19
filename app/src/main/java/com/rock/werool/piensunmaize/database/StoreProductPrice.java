package com.rock.werool.piensunmaize.database;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.sql.Date;



/**
 * Created by guntt on 17.08.2017.
 */

public class StoreProductPrice {

    public static final String TAG_PRODUCT_ID = "p_id";
    public static final String TAG_PRODUCT_NAME = "p_name";
    public static final String TAG_PRODUCT_CATEGORY = "p_category";
    public static final String TAG_PRODUCT_DESCRIPTION = "p_descript";
    public static final String TAG_PRODUCT_AVERAGE_PRICE = "p_price";

    public static final String TAG_STORE_ID = "s_id";
    public static final String TAG_STORE_NAME = "s_name";
    public static final String TAG_STORE_LOCATION = "s_location";

    public static final String TAG_PRICE = "spp_price";
    public static final String TAG_LAST_UPDATED = "spp_last_update";


    public StoreProductPrice(Store store, Product product, double price, Date lastUpdated) {
        this.store = store;
        this.product = product;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }

    public StoreProductPrice(JSONObject jobj) {
        try {
            this.store = new Store(
                    jobj.getInt(TAG_STORE_ID),
                    jobj.getString(TAG_STORE_NAME),
                    jobj.getString(TAG_STORE_LOCATION));
            this.product = new Product(
                    jobj.getInt(TAG_PRODUCT_ID),
                    jobj.getString(TAG_PRODUCT_NAME),
                    jobj.getString(TAG_PRODUCT_CATEGORY),
                    jobj.getString(TAG_PRODUCT_DESCRIPTION),
                    jobj.getDouble(TAG_PRODUCT_AVERAGE_PRICE));
            this.price = jobj.getDouble(TAG_PRICE);
            Log.d("dateerror", jobj.getString(TAG_LAST_UPDATED));
            this.lastUpdated = Date.valueOf(jobj.getString(TAG_LAST_UPDATED));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Store store;
    private Product product;
    private double price;
    private Date lastUpdated;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return store.toString() + " " + product.toString() +
                " spp_price:" + this.getPrice() +
                " spp_last_update" + this.getLastUpdated().toString();
    }
}
