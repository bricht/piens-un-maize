package com.rock.werool.piensunmaize.search;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

/**
 * Created by Martin on 2017.08.19..
 */

public class TalkToLocalDatabase {      //TODO Finish implementing this class
    protected Context context;

    public TalkToLocalDatabase(Context context) {
        this.context = context;
    }

    public Cursor productAvgPrice(String productName) {
        Intent intent = new Intent(context, null);
        return null;
    }
}
