package com.rock.werool.piensunmaize.remoteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by guntt on 16.08.2017.
 */

public class Product implements Serializable {



    public static final String TAG_ID = "p_id";
    public static final String TAG_NAME = "p_name";
    public static final String TAG_CATEGORY = "p_category";
    public static final String TAG_DESCRIPTION = "p_descript";
    public static final String TAG_PRICE = "p_price";

    public Product(long id, String name, String category, String description, double avaragePrice) {
        this.setId(id);
        this.setName(name);
        this.setCategory(category);
        this.setDescription(description);
        this.setAvaragePricePrice(avaragePrice);
    }
    public Product(String name, String category, String description, double avaragePrice) {
        this(0, name, category, description, avaragePrice);
    }

    public Product (JSONObject jobj) throws JSONException {
        this(jobj.getInt("p_id"), jobj.getString("p_name"), jobj.getString("p_category"),
                jobj.getString("p_descript"), jobj.getDouble("p_price"));
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

    @Override
    public String toString() {
        return this.getId() + " | " + this.getName() +
                " | " + this.getCategory() +
                " | " + this.getDescription() +
                " | " + this.getAvaragePricePrice();
    }

    private long id;
    private String name;
    private String category;
    private String description;
    private double avaragePrice;
}
