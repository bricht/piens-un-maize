package com.rock.werool.piensunmaize.favourites;

/**
 * Created by Jaco on 18/08/2017.
 */

public class FavouriteStore {
    String name = null;
    String address = null;

    public FavouriteStore(String name, String address) {
        this.name = name;
        this.address = address;
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
