package com.rock.werool.piensunmaize.remoteDatabase;

import com.android.volley.VolleyError;

import java.util.ArrayList;

/**
 * Created by guntt on 17.08.2017.
 */

public interface IDatabaseResponseHandler<T> {

    public void onArrive(ArrayList<T> data);

    public void onError(VolleyError error);

}
