package com.rock.werool.piensunmaize.search;

/**
 * Created by Martin on 17-Aug-17.
 */

public class Product {
    String name = null;
    //String price = null;
    Boolean isChecked = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Product(String name) {
        this.name = name;

    }
}
