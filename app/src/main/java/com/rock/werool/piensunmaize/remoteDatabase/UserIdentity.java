package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;

import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by guntt on 24.08.2017.
 */

public class UserIdentity {

    private static final String FILE_NAME = "piens_un_maize.user";

    private Integer id;
    private boolean validID = false;

    public UserIdentity(RemoteDatabase db, Context context) {
        this.Initialize(db, context);
    }

    private void Initialize(RemoteDatabase db, final Context context) {
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            db.GetNewUserId("random user v 1.0", new IDatabaseResponseHandler<String>() {
                @Override
                public void onArrive(ArrayList<String> data) {
                    if(data.size() == 1) {
                        try {
                            id = Integer.parseInt(data.get(0));
                            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                            ObjectOutputStream os = new ObjectOutputStream(fos);
                            os.writeObject(UserIdentity.this.id);
                            os.close();
                            fos.close();
                            UserIdentity.this.validID = true;
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } else {
            FileInputStream fis = null;
            try {
                fis = context.openFileInput(FILE_NAME);
                ObjectInputStream is = new ObjectInputStream(fis);
                UserIdentity.this.id = (Integer) is.readObject();
                is.close();
                fis.close();
                UserIdentity.this.validID = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int GetID() {
        if(validID){
            return this.id.intValue();
        }
        return -1;
    }
}
