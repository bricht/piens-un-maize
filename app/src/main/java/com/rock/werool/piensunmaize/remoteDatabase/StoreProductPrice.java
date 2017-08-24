package com.rock.werool.piensunmaize.remoteDatabase;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;



/**
 * Created by guntt on 17.08.2017.
 */

public class StoreProductPrice implements Serializable {

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
                    jobj.getInt(Store.TAG_ID),
                    jobj.getString(Store.TAG_NAME),
                    jobj.getString(Store.TAG_LOCATION));
            this.product = new Product(
                    jobj.getInt(Product.TAG_ID),
                    jobj.getString(Product.TAG_NAME),
                    jobj.getString(Product.TAG_CATEGORY),
                    jobj.getString(Product.TAG_DESCRIPTION),
                    jobj.getDouble(Product.TAG_PRICE));
            this.price = jobj.getDouble(TAG_PRICE);
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
                " | cena: " + this.getPrice() +
                " | last update: " + this.getLastUpdated().toString();
    }
}
