package com.rock.werool.piensunmaize.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guntt on 17.08.2017.
 */

public class Barcode {

    public static final String TAG_BARCODE = "barcode";
    public static final String TAG_PRODUCT_ID = "p_id";

    private int product_id;
    private String barcode;

    public Barcode(String barcode, int product_id) {
        this.barcode = barcode;
        this.product_id = product_id;
    }

    public Barcode(JSONObject jobj) throws JSONException {
        this(jobj.getString(TAG_BARCODE), jobj.getInt(TAG_PRODUCT_ID));
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
