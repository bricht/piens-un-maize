package com.rock.werool.piensunmaize.barcode;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import com.android.volley.VolleyError;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.rock.werool.piensunmaize.R;
import com.rock.werool.piensunmaize.SQLiteLocal_DB.SQLiteQuery;
import com.rock.werool.piensunmaize.add.FillWithHandActivity;
import com.rock.werool.piensunmaize.mainpage.MainMenu;
import com.rock.werool.piensunmaize.remoteDatabase.IDatabaseResponseHandler;
import com.rock.werool.piensunmaize.remoteDatabase.Product;
import com.rock.werool.piensunmaize.remoteDatabase.RemoteDatabase;
import com.rock.werool.piensunmaize.search.by_product.SearchByProductActivity;
import com.rock.werool.piensunmaize.search.by_product.SelectStoreActivity;

import java.io.IOException;
import java.util.ArrayList;

public class BarcodeScanner extends AppCompatActivity {
    CameraSource cameraSource;
    final int cameraPermissionCode = 1;
    private static final String TAG = "BarcodeScanner";
    private static boolean activityOpen = false;

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(SearchProductList);         //Unregisters BroadcastReceivers
        unregisterReceiver(SearchProductSQL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(SearchProductSQL , new IntentFilter ("QUERY_RESULT"));
        activityOpen = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case cameraPermissionCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    this.recreate();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(BarcodeScanner.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(BarcodeScanner.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(BarcodeScanner.this,
                        new String[]{Manifest.permission.CAMERA},
                        cameraPermissionCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
            final SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_view);
            final TextView barcodeInfo = (TextView) findViewById(R.id.code_info);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(30.0f);
            cameraSource = builder.build();
            final ImageView flash = (ImageView) findViewById(R.id.flash);
            flash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flashOnButton();
                    if (flashmode) {
                        flash.setImageResource(R.drawable.flash_outline_yellow);
                    } else {
                        flash.setImageResource(R.drawable.flash_outline_grey);
                    }
                }
            });
            cameraView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //empty
                }
            });
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        cameraSource.start(cameraView.getHolder());   //ignore
                        /*
                        camera = getCamera(cameraSource);
                        Camera.Parameters param = camera.getParameters();
                        param.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                        camera.setParameters(param);
                        */
                    } catch (IOException e) {
                        Log.e("CAMERA SOURCE", e.getMessage());
                    }
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });
            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                }
                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                    if (barcodes.size() != 0) {
                        if(activityOpen == false) {
                            final String necessaryAction = getIntent().getExtras().getString("necessaryAction");
                            /*
                            Intent intentForSQL = new Intent(getApplicationContext(), SQLiteQuery.class);
                            intentForSQL.putExtra(SQLiteQuery.SRC_TYPE, SQLiteQuery.SRC_PRODUCT_AVG_PRICE);     //Average price for product
                            intentForSQL.putExtra(SQLiteQuery.SRC_NAME, (String) null);     //TODO implement barcode query
                            intentForSQL.putExtra(SQLiteQuery.SRC_STORE, (String) null);
                            intentForSQL.putExtra(SQLiteQuery.SRC_ADDRESS, (String) null);
                            startService(intentForSQL);
                            */
                            final RemoteDatabase remoteDB = new RemoteDatabase("http://zesloka.tk/piens_un_maize_db/", getApplicationContext());
                            remoteDB.FindProductByBarCode(barcodes.valueAt(0).displayValue, new IDatabaseResponseHandler<Product>() {
                                @Override
                                public void onArrive(ArrayList<Product> data) {
                                    switch (necessaryAction) {
                                        case ("FIND_PRODUCT_INFO") : {
                                            if (data.size() > 0) {
                                                Intent intent = new Intent(getApplicationContext(), SearchByProductActivity.class);
                                                String name = data.get(0).getName();
                                                intent.putExtra("scannedProductName", name);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Product not in database", Toast.LENGTH_SHORT).show();
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        activityOpen = false;
                                                    }
                                                }, 500);
                                                break;
                                            }
                                        }case ("UPDATE_PRODUCT") : {
                                            if (data.size() > 0) {
                                                Intent intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                                                //String name = data.get(0).getName();
                                                //intent.putExtra("scannedProductName", data.get(0).getName());
                                                //intent.putExtra("scannedProductBarcode", barcodes.valueAt(0).displayValue);
                                                intent.putExtra("Product", data.get(0));
                                                intent.putExtra("barcodeID", barcodes.valueAt(0).displayValue);
                                                intent.putExtra("addNew", false);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                                                //intent.putExtra("scannedProductBarcode", barcodes.valueAt(0).displayValue);
                                                intent.putExtra("Product", data.get(0));
                                                intent.putExtra("barcodeID", barcodes.valueAt(0).displayValue);
                                                intent.putExtra("addNew", true);
                                                startActivity(intent);
                                            }
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            });
                            activityOpen = true;
                        }else{
                            
                        }
                        /*
//                        barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
//                            public void run() {
                        barcodeInfo.setText(    // Update the TextView
                                barcodes.valueAt(0).displayValue
                        );
                        Log.v(TAG, "It is alive! :" + barcodes.valueAt(0));
                        Intent afs = new Intent(getApplicationContext(), FillWithHandActivity.class);
                        startActivity(afs);

//                            }
//                        });
                        Barcode barc = barcodes.valueAt(0);
                        */
                    }
                }
            });
        }
    }

    private static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    private Camera camera = null;
    boolean flashmode = false;

    private void flashOnButton() {
        camera = getCamera(cameraSource);
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    BroadcastReceiver SearchProductSQL = new BroadcastReceiver() {              //Receives broadcast from SQLite database class
        @Override
        public void onReceive(Context context, Intent intent) {
            String necessaryAction = getIntent().getExtras().getString("necessaryAction");   //Gets variable named necessaryAction from the bundle
            Bundle bun = intent.getBundleExtra(SQLiteQuery.QUERY_RESULT);
            String [][] array = (String[][]) bun.getSerializable("String[][]");
            executeNecessaryAction(necessaryAction, array[0][0]);           //TODO properly implement barcode query
        }
    };
    void executeNecessaryAction(String necessaryAction, String productName) {
        switch (necessaryAction) {
            case "UPDATE_PRODUCT" : {
                Intent intent = new Intent(getApplicationContext(), FillWithHandActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("scannedProductName", productName);
                //intent.putExtra("scannedProductCategory", "Placeholder Category");
                getApplicationContext().startActivity(intent);
                break;
            } case "FIND_PRODUCT_INFO": {
                Intent intent = new Intent(getApplicationContext(), SearchByProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("scannedProductName", productName);
                getApplicationContext().startActivity(intent);
                break;
            } default: {

            }
        }      //BarcodeAction executes necessary action
    }
}
