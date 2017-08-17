package com.rock.werool.piensunmaize.search;

/**
 * Created by Martin on 17-Aug-17.
 */

public class Product {
    String name = null;
    String price = null;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
