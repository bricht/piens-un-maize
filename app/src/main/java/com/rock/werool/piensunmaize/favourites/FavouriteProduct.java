package com.rock.werool.piensunmaize.favourites;


public class FavouriteProduct {
    String name = null;
    String price = null;

    public FavouriteProduct(String name, String price) {
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