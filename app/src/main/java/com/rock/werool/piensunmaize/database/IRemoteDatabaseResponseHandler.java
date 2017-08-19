package com.rock.werool.piensunmaize.database;

import com.android.volley.VolleyError;

import java.util.ArrayList;

/**
 * Created by guntt on 17.08.2017.
 */

public interface IRemoteDatabaseResponseHandler<T> {

    public void onArrive(ArrayList<T> data);

    public void onError(VolleyError error);
}
