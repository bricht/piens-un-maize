package com.rock.werool.piensunmaize.remoteDatabase;

import java.io.Serializable;

/**
 * Created by guntt on 24.08.2017.
 */

public class User implements Serializable {

    public final static String TAG_ID = "u_id";
    public final static String TAG_DATA = "u_data";

    private int id;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public User(String data) {
        this.data = data;
    }
}
