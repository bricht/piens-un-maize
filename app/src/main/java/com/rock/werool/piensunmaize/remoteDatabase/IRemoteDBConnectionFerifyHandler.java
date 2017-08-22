package com.rock.werool.piensunmaize.remoteDatabase;

/**
 * Created by guntt on 22.08.2017.
 */

public interface IRemoteDBConnectionFerifyHandler {

    public void OnConnectionLost(String msg);

    public void OnConnection();
}
