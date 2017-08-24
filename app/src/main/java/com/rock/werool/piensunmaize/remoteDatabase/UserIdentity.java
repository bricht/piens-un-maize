package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;

import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by guntt on 24.08.2017.
 */

public class UserIdentity {

    private static final String FILE_NAME = "user.pum";
    private static final String FOLDER_NAME = "piensunmaize";

    private Integer id;
    private boolean validID = false;

    public UserIdentity(RemoteDatabase db, Context context) {
        this.Initialize(db, context);
    }

    private void Initialize(RemoteDatabase db, final Context context) {
        File file = new File(context.getFilesDir() + File.separator, FILE_NAME);
        if(!file.exists()) {
            db.GetNewUserId("random user v 1.0", new IDatabaseResponseHandler<String>() {
                @Override
                public void onArrive(ArrayList<String> data) {
                    if (data.size() == 1) {

                            ObjectOutput out = null;

                            try {
                                id = parseInteger(data.get(0));
                                out = new ObjectOutputStream(new FileOutputStream(
                                        new File(context.getFilesDir() + File.separator + FILE_NAME)));
                                out.writeObject(id);
                                out.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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
                fis = new FileInputStream(new File(context.getFilesDir() + File.separator + FILE_NAME));
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

    private int parseInteger(String integer) {
        return Integer.parseInt(integer.replaceAll(" ", ""));
    }
}
