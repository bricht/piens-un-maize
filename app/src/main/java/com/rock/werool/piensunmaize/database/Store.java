package com.rock.werool.piensunmaize.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guntt on 17.08.2017.
 */

public class Store {

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_LOCATION = "location";

    private int id;
    private String name;
    private String location;

    public Store(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Store(JSONObject jobj) throws JSONException {
        this(jobj.getInt(TAG_ID), jobj.getString(TAG_NAME), jobj.getString(TAG_LOCATION));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
