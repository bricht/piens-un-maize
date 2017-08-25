package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Connection;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import static android.R.attr.action;
import static android.R.attr.timePickerDialogTheme;

/**
 * Created by Guntars Berzins  on 22.08.2017.
 * Class to verify connection to online databse
 *
 */

public class ConnectionVerifyer {

    public static final String ACTION_VERIFY_CONNECTION = "verifyConnection.php";
    public static final String CONNECTION_OK = "OK";
    public static final String STATUS_NAME = "status";
    public static final int STATUS_VALUE_CONNECTION_OK = 1;
    public static final int STATUS_VALUE_NO_CONNECTION = 0;
    public static final String STATUS_MESSAGE = "status_msg";
    public static final long DEFAULT_VERIFY_TIMEOUT = 3000;

    private String url;
    private Context context;
    private ArrayList<IRemoteDBConnectionFerifyHandler> listeners;
    private boolean hasConnection;
    private long timeout;
    private boolean running;

    public ConnectionVerifyer(String url, Context c) {
        this.url = url;
        context = c;
        timeout = DEFAULT_VERIFY_TIMEOUT;
        hasConnection = false;
        running = false;
        listeners = new ArrayList<IRemoteDBConnectionFerifyHandler>();
    }

    Handler threadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.getData().getInt(STATUS_NAME) == STATUS_VALUE_CONNECTION_OK){
                if(!hasConnection) {
                    for(IRemoteDBConnectionFerifyHandler l : listeners) {
                        if(l != null) {
                            l.OnConnection();
                        }
                    }
                    hasConnection = true;
                }
            } else {
                for(IRemoteDBConnectionFerifyHandler l : listeners) {
                    if(l != null) {
                        l.OnConnectionLost(msg.getData().getString(STATUS_MESSAGE));
                    }

                }
                hasConnection = false;
            }
        }
    };

    private final Runnable statusVerifyer = new StatucChekRunnable();

    /**
     * Starts beckgroud thread to check connection by time delay parameter
     */
    public void Start(){
        Thread t = new Thread(statusVerifyer);
        t.start();
    }

    /**
     * Terminates thread for checking connection status
     */
    public void Stop() {
        this.running = false;
    }

    /**
     * Sets timeout for conection statuc chek
     * @param timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return this.timeout;
    }


    /**
     * Return boolean if verifyer instance is running thread to verify connection status
     * @return
     */
    public boolean IsRunning() {
        return this.running;
    }

    /**
     * Per
     */
    public void VerifyConnection() {
        StatucChekRunnable sch = (StatucChekRunnable)statusVerifyer;
        sch.CheckStatus();
    }

    /**
     * Add handler for connection status change events
     * @param listener
     */
    public void addListener(IRemoteDBConnectionFerifyHandler listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove handler for connection status change events
     * @param listener
     */
    public void removeListener(IRemoteDBConnectionFerifyHandler listener) {
        this.listeners.remove(listener);
    }


    // HELPER METHOD .........
    private Message createMessage(String statusNameTag, int connectionValue) {
        return createMessage(statusNameTag, connectionValue, STATUS_MESSAGE, "chill");
    }

    private Message createMessage(
            String statusNameTag, int connectionValue,
            String statusMessageTag, String message
    ) {
        Bundle b = new Bundle();
        b.putInt(statusNameTag, connectionValue);
        b.putString(statusMessageTag, message);
        Message msg = new Message();
        msg.setData(b);
        return msg;
    }

    private class StatucChekRunnable implements Runnable {

        public void CheckStatus() {
            String requestUrl = ConnectionVerifyer.this.url + ACTION_VERIFY_CONNECTION;
            StringRequest strRequest =
                    new StringRequest(Request.Method.GET, requestUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals(CONNECTION_OK)) {
                                        threadHandler.sendMessage(createMessage(
                                                STATUS_NAME, STATUS_VALUE_CONNECTION_OK
                                        ));
                                    } else {
                                        threadHandler.sendMessage(createMessage(
                                                STATUS_NAME, STATUS_VALUE_NO_CONNECTION,
                                                STATUS_MESSAGE, response
                                        ));
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            threadHandler.sendMessage(createMessage(
                                    STATUS_NAME, STATUS_VALUE_NO_CONNECTION,
                                    STATUS_MESSAGE, error.getMessage()));

                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(strRequest);
        }

        @Override
        public void run() {
            running = true;
            while(running) {
                CheckStatus();
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
