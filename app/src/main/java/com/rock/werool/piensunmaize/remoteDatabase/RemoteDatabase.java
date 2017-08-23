package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

/**
 * Created by guntt on 16.08.2017.
 */

public class RemoteDatabase {

    private static final String ACTION_ADD_PRODUCT = "addProduct.php";
    private static final String ACTION_ADD_STORE = "addStore.php";
    private static final String ACTION_ADD_STOREPRODUCTPRICE = "addStoreProductPrice.php";
    private static final String ACTION_ADD_BARCODE = "addBarcode.php";

    private static final String ACTION_UPDATE_PRODUCT = "updateProduct.php";
    private static final String ACTION_UPDATE_STORE = "updateStore.php";
    private static final String ACTION_UPDATE_STOREPRODUCTPRICE = "updateStoreProductPrice.php";
    private static final String ACTION_UPDATE_BACRODE = "updateBarcode.php";

    private static final String ACTION_DELETE_PRODUCT = "deleteProduct.php";
    private static final String ACTION_DELETE_STORE = "deleteStore.php";
    private static final String ACTION_DELETE_STOREPRODUCTPRICE = "deleteStoreProductPrice.php";
    private static final String ACTION_DELETE_BACRODE = "deleteBarcode.php";

    private static final String ACTION_FIND_PRODUCT_BY_BARCODE = "findProductByBarcode.php";
    private static final String ACTION_FIND_PRODUCT_BY_NAME = "findProductByName.php";
    private static final String ACTION_FIND_PRODUCT_BY_CATEGORY = "findProductByCategory.php";
    private static final String ACTION_FIND_PORDUCT_BY_STRING_KEY = "findProductByStringKey.php";
    private static final String ACTION_FIND_PORDUCT_BY_ID = "findProductByID.php";


    private static final String ACTION_FIND_STORE_BY_NAME = "findStoreByName";
    private static final String ACTION_FIND_STORE_BY_LOCATION = "findStoreByLocation";
    private static final String ACTION_FIND_STORE_BY_STRING_KEY = "findStoreByStringKey.php";
    private static final String ACTION_FIND_STORE_BY_ID = "findStoreByID.php";

    private static final String ACTION_GET_ALL_PRODUCTS = "getAllProducts.php";
    private static final String ACTION_GET_ALL_STORES = "getAllStores.php";
    private static final String ACTION_GET_ALL_BARCODES = "getAllBarcodes.php";
    private static final String ACTION_GET_ALL_STOREPRODUCTPRICES = "getAllStoreProductPrices.php";


    private static final String ACTION_FIND_STORE_PRODUCT_PRICE = "findStoreProductPrice.php";

    public static final String KEY_STR_KEY = "str_key";

    private String url;
    private Context context;

    private IDatabaseResponseHandler<Product> lastProductHandler;
    private IDatabaseResponseHandler<Store> lastStoreHandler;
    private IDatabaseResponseHandler<StoreProductPrice> lastStoreProductPriceHandler;
    private IDatabaseResponseHandler<Barcode> lastBarcodeHandler;

    public void setUrl(String url) {
        this.url = url;
    }

    public RemoteDatabase(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    //Server side done
    public void AddProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_ADD_PRODUCT, product, responseHandler);
    }

    //Server side done
    public void AddStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_ADD_STORE, store, responseHandler);
    }

    //Server side done
    public void AddStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_ADD_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    //Server side done
    public void AddBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_ADD_BARCODE, barcode, responseHandler);
    }
    //Server side done
    public void UpdateProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_UPDATE_PRODUCT, product, responseHandler);
    }
    //Server side done
    public void UpdateStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_UPDATE_STORE, store, responseHandler);
    }
    //Server side done
    public void UpdateStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_UPDATE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }
    //Server side done
    public void UpdateBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_UPDATE_BACRODE, barcode, responseHandler);
    }

    public void DeleteProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_DELETE_PRODUCT, product, responseHandler);
    }

    public void DeleteStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_DELETE_STORE, store, responseHandler);
    }

    public void DeleteStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_DELETE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void DeleteBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_DELETE_BACRODE, barcode, responseHandler);
    }


    // GETTERS ...

    public void GetAllProducts(IDatabaseResponseHandler<Product> responseHandler) {
        this.doThis(ACTION_GET_ALL_PRODUCTS, responseHandler, new OnProduct(responseHandler));
        this.lastProductHandler = responseHandler;
    }

    public void GetAllStores(IDatabaseResponseHandler<Store> responseHandler) {
        this.doThis(ACTION_GET_ALL_STORES, responseHandler, new OnStore(responseHandler));
        this.lastStoreHandler = responseHandler;
    }

    public void GetAllBarcodes(IDatabaseResponseHandler<Barcode> responseHandler) {
        this.doThis(ACTION_GET_ALL_BARCODES, responseHandler, new OnBarcode(responseHandler));
        this.lastBarcodeHandler = responseHandler;
    }

    public void GetAllStoreProductPrices(IDatabaseResponseHandler<StoreProductPrice> responseHandler) {
        this.doThis(ACTION_GET_ALL_STOREPRODUCTPRICES, responseHandler, new OnStoreProductPrice(responseHandler));
        this.lastStoreProductPriceHandler = responseHandler;
    }

    private <T> void doThis(String action, IDatabaseResponseHandler<T> responseHandler, Response.Listener<String> listener ) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        listener, new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }



    // FIND PRODUCTS .........
    public void FindProductByID(ArrayList<Integer> ids, IDatabaseResponseHandler<Product> responseHandler) {
        String data = "";

        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_BARCODE, Barcode.TAG_BARCODE, data , responseHandler);
        this.lastProductHandler = responseHandler;
    }

    //Server side done
    public void FindProductByBarCode(String barCode, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_BARCODE, Barcode.TAG_BARCODE, barCode, responseHandler);
        this.lastProductHandler = responseHandler;
    }
    //Server side done
    public void FindProductByName(String name, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_NAME, Product.TAG_NAME, name, responseHandler);
        this.lastProductHandler = responseHandler;
    }
    //Server side done
    public void FindProductByCategory(String category, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_CATEGORY, Product.TAG_CATEGORY, category, responseHandler);
        this.lastProductHandler = responseHandler;
    }
    //Server side done
    public void FindProductByStringKey(String value, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PORDUCT_BY_STRING_KEY, KEY_STR_KEY ,value, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    private void FindProductByStringKey(String action, String key, String value, IDatabaseResponseHandler<Product> responseHandler) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + action + "?" + key + "=" + value);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }



    // FIND STORES..........
    //Server side done
    public void FindStoreByID(ArrayList<Integer> ids, IDatabaseResponseHandler<Store> responseHandler) {

        String data = "";
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, Store.TAG_NAME, data , responseHandler);
        this.lastStoreHandler = responseHandler;
    }

    public void FindStoreByName(String key, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, Store.TAG_NAME, key, responseHandler);
        this.lastStoreHandler = responseHandler;
    }
    //Server side done
    public void FindStoreByLocation(String location, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_LOCATION, Store.TAG_LOCATION, location, responseHandler );
        this.lastStoreHandler = responseHandler;
    }
    //Server side done
    public void FindStoreByStringKey(String key, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_STRING_KEY, KEY_STR_KEY, key, responseHandler);
        this.lastStoreHandler = responseHandler;
    }

    private void FindStoreByStringKey(String action, String key, String value, IDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = this.url + action + "?" + key + "=" + value;
        Log.d("requestUrl", requestUrl);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    // FIND STORE PRODUCT PRICE

    public void FindStoreProductPrice(
            Product product,
            IDatabaseResponseHandler<StoreProductPrice> responseHandler){

        String requestUrl = this.removeWhiteSpaceFromUrl(
                createProductParamUrl(product,ACTION_FIND_STORE_PRODUCT_PRICE)) ;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;
    }



    //-----------------------------------------
    //      ... GENERAL METHODS down there ...
    //-----------------------------------------

    private void ExecuteStringRequest(StringRequest request) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);
    }


    // MANAGEER HELPER METHODS

    private void ManageProductByAction(
            String action,
            Product product,
            IDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = this.removeWhiteSpaceFromUrl(createProductParamUrl(product,action));
        Log.d("requestUrl", requestUrl);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageStoreByAction(
            String action,
            Store store,
            IDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = this.removeWhiteSpaceFromUrl(createStoreParamUrl(store, action));
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageStoreProductPriceByAction(
            String action,
            StoreProductPrice storeProductPrice,
            IDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = this.removeWhiteSpaceFromUrl(
                createStoreProductPriceParamUrl(storeProductPrice, action));
        Log.d("requestUrl", requestUrl);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageBarcodeByAction(
            String action,
            Barcode barcode,
            IDatabaseResponseHandler<String> responseHandler){

        String requestUrl = createBarcodeRequestUrl(barcode, action);
        Log.d("requestUrl", requestUrl);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void FindProductByAction(
            String action,
            Product product,
            IDatabaseResponseHandler<Product> responseHandler) {

        String requestUrl = createProductParamUrl(product,action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    private void FindStoreByAction(
            String action,
            Store store,
            IDatabaseResponseHandler<Store> responseHandler) {

        String requestUrl = createStoreParamUrl(store, action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    private String createProductParamUrl(Product product, String action) {
        return this.url + action + "?" +
                Product.TAG_ID + "=" + product.getId() + "&" + // sorry but this was necessary
                Product.TAG_NAME + "=" + product.getName() + "&" +
                Product.TAG_CATEGORY + "=" + product.getCategory() + "&" +
                Product.TAG_DESCRIPTION + "=" + product.getDescription() + "&" +
                Product.TAG_PRICE + "=" + product.getAvaragePricePrice();
    }

    private String createStoreParamUrl(Store store, String action) {
        return this.url + action + "?" +
                Store.TAG_ID + "=" + store.getId() + "&" +
                Store.TAG_NAME + "=" + store.getName() + "&" +
                Store.TAG_LOCATION + "=" + store.getLocation();
    }

    private String createStoreProductPriceParamUrl(StoreProductPrice storeProductPrice, String action) {
        return this.url + action + "?" +
                Store.TAG_ID + "=" + storeProductPrice.getStore().getId() + "&" +
                Store.TAG_NAME + "=" + storeProductPrice.getStore().getName() + "&" +
                Store.TAG_LOCATION + "=" + storeProductPrice.getStore().getLocation() + "&" +
                Product.TAG_ID + "=" + storeProductPrice.getProduct().getId() + "&" +
                Product.TAG_NAME + "=" + storeProductPrice.getProduct().getName() + "&" +
                Product.TAG_CATEGORY + "=" + storeProductPrice.getProduct().getCategory() + "&" +
                Product.TAG_DESCRIPTION + "=" + storeProductPrice.getProduct().getDescription() + "&" +
                Product.TAG_PRICE + "=" + storeProductPrice.getProduct().getAvaragePricePrice() + "&" +
                StoreProductPrice.TAG_PRICE + "=" + storeProductPrice.getPrice() + "&" +
                StoreProductPrice.TAG_LAST_UPDATED + "=" + storeProductPrice.getLastUpdated().toString();
    }

    private String createBarcodeRequestUrl(Barcode barcode, String action) {
        return this.url + action + "?" +
                Barcode.TAG_BARCODE + "=" + barcode.getBarcode() + "&" +
                Barcode.TAG_PRODUCT_ID + "=" + barcode.getProduct_id();
    }

    private String removeWhiteSpaceFromUrl(String url) {
        return url.replaceAll(" ", "%20");
    }


    //------------------------------------
    //      HELPER CLASSES down there ...
    //------------------------------------


    // helper class for expected StoreProductPrice response
    private class OnStoreProductPrice implements Response.Listener<String> {

        private ArrayList<StoreProductPrice> data;
        private IDatabaseResponseHandler handler;
        private Handler parseHandler = new Handler() {
          @Override
            public void handleMessage(Message msg) {
              if(handler == lastStoreProductPriceHandler) {
                  handler.onArrive(data);

              }
          }
        };

        public OnStoreProductPrice(IDatabaseResponseHandler h) {
            handler = h;
            data = new ArrayList<StoreProductPrice>();
            Log.d("testthread" , "created");
        }
        @Override
        public void onResponse(String response) {


            final String res = response;

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Log.d("myapp.testthread" , "started");
                    try {
                        Log.d("jsonerror", res);
                        JSONArray jarray = new JSONArray(res);

                        for(int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj = jarray.getJSONObject(i);
                            data.add(new StoreProductPrice(jobj));
                        }

                    } catch (JSONException e) {
                        Log.d("jsonerror", e.getMessage());
                    }
                    parseHandler.sendEmptyMessage(0);
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    // helper class for expected Store response
    private class OnStore implements Response.Listener<String> {

        ArrayList<Store> data;
        private IDatabaseResponseHandler handler;
        private Handler parseHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(handler == lastStoreHandler) {
                    handler.onArrive(data);
                }
            }
        };

        public OnStore(IDatabaseResponseHandler h) {
            handler = h;
            data = new ArrayList<Store>();
        }
        @Override
        public void onResponse(String response) {
            final String res = response;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jarray = new JSONArray(res);
                        for(int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj = jarray.getJSONObject(i);
                            data.add(new Store(jobj));
                        }
                        parseHandler.sendEmptyMessage(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    // helper class for expected Product response
    private class OnProduct implements Response.Listener<String> {

        private IDatabaseResponseHandler handler;
        private ArrayList<Product> data;
        private Handler parseHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if(handler == lastProductHandler) {
                    Log.d("error", "This is called");
                    handler.onArrive(data);
                }
            }
        };

        public OnProduct(IDatabaseResponseHandler h) {

            handler = h;
            data = new ArrayList<Product>();
        }
        @Override
        public void onResponse(String response) {
            final String res = response;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jarray = new JSONArray(res);
                        for(int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj = jarray.getJSONObject(i);
                            data.add(new Product(jobj));
                        }
                        parseHandler.sendEmptyMessage(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    // helper class for expected barcode response
    private class OnBarcode implements Response.Listener<String> {

        private IDatabaseResponseHandler handler;
        private ArrayList<Barcode> data;
        private Handler parseHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(handler == lastBarcodeHandler) {
                    handler.onArrive(data);
                }
            }
        };

        public OnBarcode(IDatabaseResponseHandler h) {

            handler = h;
            data = new ArrayList<Barcode>();
        }
        @Override
        public void onResponse(String response) {

            final String res = response;

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jarray = new JSONArray(res);
                        for(int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj = jarray.getJSONObject(i);
                            data.add(new Barcode(jobj));
                        }
                        parseHandler.sendEmptyMessage(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }


    // helper class for expected String response
    private class OnString implements Response.Listener<String> {

        private IDatabaseResponseHandler handler;

        public OnString(IDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<String> str = new ArrayList<String>();
            str.add(response);
            handler.onArrive(str);
        }
    }


    // helper class for handling error response
    private class OnError implements Response.ErrorListener {

        private IDatabaseResponseHandler handler;

        public OnError(IDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            handler.onError(error);
        }
    }


    ////// CONNECTION RLATED METHODS .............



}
