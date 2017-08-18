package com.rock.werool.piensunmaize.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guntt on 16.08.2017.
 */

public class Product {

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_PRICE = "price";

    public Product(long id, String name, String category, String description, double avaragePrice) {
        this.setId(id);
        this.setName(name);
        this.setCategory(category);
        this.setDescription(description);
        this.setAvaragePricePrice(avaragePrice);
    }

    public Product (JSONObject jobj) throws JSONException {
        this(jobj.getInt(TAG_ID), jobj.getString(TAG_NAME), jobj.getString(TAG_CATEGORY),
                jobj.getString(TAG_DESCRIPTION), jobj.getDouble(TAG_PRICE));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAvaragePricePrice() { return avaragePrice; }

    public void setAvaragePricePrice(double avaragePrice) { this.avaragePrice = avaragePrice; }

    private long id;
    private String name;
    private String category;
    private String description;
    private double avaragePrice;
}
