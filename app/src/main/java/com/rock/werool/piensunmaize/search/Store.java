package com.rock.werool.piensunmaize.search;

/**
 * Created by MARTINI on 17-Aug-17.
 */

public class Store {
    String name = null;
    String address = null;
    String priceForProduct = null;

    public String getPriceForProduct() {
        return priceForProduct;
    }

    public void setPriceForProduct(String priceForProduct) {
        this.priceForProduct = priceForProduct;
    }


    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Store(String name, String address, String priceForProduct) {
        this.name = name;
        this.address = address;
        this.priceForProduct = priceForProduct;     //For SelectStoreActivity
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }
}
