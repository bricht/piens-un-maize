package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by guntt on 16.08.2017.
 */

public class RemoteDatabase {

    private static final String ACTION_ADD_PRODUCT = "addProduct.php";
    private static final String ACTION_ADD_STORE = "addStore.php";
    private static final String ACTION_ADD_STOREPRODUCTPRICE = "addStoreProductPrice.php";
    private static final String ACTION_ADD_BARCODE = "addBarcode.php";
    private static final String ACTION_ADD_PRODUCT_AND_BARCODE = "addProductAndBarcode.php";

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
    private static final String ACTION_FIND_PRODUCTS_BY_NAME_AND_STORE_ID = "findProductByNameAndStoreID.php";
    private static final String ACTION_FIND_PORDUCT_BY_ID = "findProductByID.php";


    private static final String ACTION_FIND_STORE_BY_NAME = "findStoreByName";
    private static final String ACTION_FIND_STORE_BY_LOCATION = "findStoreByLocation";
    private static final String ACTION_FIND_STORE_BY_STRING_KEY = "findStoreByStringKey.php";
    private static final String ACTION_FIND_STORE_BY_NAME_AND_LOCATION = "findStoreByNameAndLocation.php";

    private static final String ACTION_GET_ALL_PRODUCTS = "getAllProducts.php";
    private static final String ACTION_GET_ALL_STORES = "getAllStores.php";
    private static final String ACTION_GET_ALL_BARCODES = "getAllBarcodes.php";
    private static final String ACTION_GET_ALL_STOREPRODUCTPRICES = "getAllStoreProductPrices.php";

    private static final String ACTION_GET_FAVORITE_PRODUCTS = "getFavoriteProducts.php";
    private static final String ACTION_GET_FAVORITE_STORES = "getFavoriteStores.php";
    private static final String ACTION_SET_FAVORITE_PRODUCT = "setFavoriteProduct.php";
    private static final String ACTION_SET_FAVORITE_STORE = "setFavoriteStore.php";
    private static final String ACTION_DELETE_FAVORITE_PRODUCT = "deleteFavoriteProduct.php";
    private static final String ACTION_DELETE_FAVORITE_STORE = "deleteFavoriteStore.php";



    private static final String ACTION_GET_NEW_USER_ID = "getNewUserID.php";



    private static final String ACTION_FIND_STORE_PRODUCT_PRICE = "findStoreProductPrice.php";

    public static final String KEY_STR_KEY = "str_key";

    private String url;
    private Context context;
    private UserIdentity user;

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
        user = new UserIdentity(this, context);
    }

    public void AddProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_ADD_PRODUCT, product, responseHandler);
    }


    // Use this for barcode scan to add new products
    public void AddProductAndBarcode(Product product, String barcode, IDatabaseResponseHandler<String> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url +
                        createProductParamUrl(product,ACTION_ADD_PRODUCT_AND_BARCODE) + "&" +
                        Barcode.TAG_BARCODE + "=" + barcode);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void AddStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_ADD_STORE, store, responseHandler);
    }

    public void AddStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_ADD_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void AddBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_ADD_BARCODE, barcode, responseHandler);
    }

    public void UpdateProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_UPDATE_PRODUCT, product, responseHandler);
    }

    public void UpdateStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_UPDATE_STORE, store, responseHandler);
    }

    public void UpdateStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_UPDATE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void UpdateBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_UPDATE_BACRODE, barcode, responseHandler);
    }

    // TODO make sure it delete all references in database
    public void DeleteProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_DELETE_PRODUCT, product, responseHandler);
    }

    // TODO make sure it delete all references in database
    public void DeleteStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_DELETE_STORE, store, responseHandler);
    }

    // Does what it says
    public void DeleteStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_DELETE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    // Does what it says
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

    // Does that
    private <T> void doThis(String action, IDatabaseResponseHandler<T> responseHandler, Response.Listener<String> listener ) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        listener, new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    // Return StoreProductPrice objects(Store, Product and their price in store)
    // where product is in store('storeID') and product name is like 'productName'
    public void FindProductInStoreByName(int storeId, String productName, IDatabaseResponseHandler<StoreProductPrice> responseHandler) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + ACTION_FIND_PRODUCTS_BY_NAME_AND_STORE_ID +
                "?" + Store.TAG_ID + "=" + storeId + "&" + Product.TAG_NAME  + "=" + productName);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;
    }

    // Return Product object where product barcode equals  'barCode'
    public void FindProductByBarCode(String barCode, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_BARCODE, Barcode.TAG_BARCODE, barCode, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    // Return Product objects where product name is like 'name'
    public void FindProductByName(String name, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_NAME, Product.TAG_NAME, name, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    // Return Product objects were product category is like 'category'
    public void FindProductByCategory(String category, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_CATEGORY, Product.TAG_CATEGORY, category, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    // Return Product objects where product name or category is like 'value'
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


    // Return Store objects where store name is like 'key'
    public void FindStoreByName(String key, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, Store.TAG_NAME, key, responseHandler);
        this.lastStoreHandler = responseHandler;
    }

    // Return Store objects where store location is like 'location'
    public void FindStoreByLocation(String location, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_LOCATION, Store.TAG_LOCATION, location, responseHandler );
        this.lastStoreHandler = responseHandler;
    }

    // Return Store objects where store name is like 'name' and store location is like 'location'
    public void FindStoreByNameAndLocation(String name, String location, IDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = this.url + ACTION_FIND_STORE_BY_NAME_AND_LOCATION + "?" +
                Store.TAG_NAME + "=" + name + "&" +
                Store.TAG_LOCATION + "=" + location;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreHandler = responseHandler;
    }

    // Return Store objects where store name or location is like 'key'
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

    // Return StoreProductPrice objects (Store, Product and product price in store) matched by Product;
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

    // DON NOT USES THIS..
    public void GetFavoriteProducts(IDatabaseResponseHandler<Product> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_GET_FAVORITE_PRODUCTS + "?" +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastProductHandler = responseHandler;
    }

    //DO NOT USE THIS
    public void GetFavoriteStores(IDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_GET_FAVORITE_STORES + "?" +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreHandler = responseHandler;
    }

    public void SetFavoriteStore(int storeID, IDatabaseResponseHandler<String> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_SET_FAVORITE_STORE + "?" + Store.TAG_NAME + "=" + storeID + "&" +
                            User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void SetFavoriteProduct(int productID, IDatabaseResponseHandler<String> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_SET_FAVORITE_PRODUCT + "?" +Product.TAG_NAME + "=" + productID + "&" +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void DeleteFavoriteProduct(Product product, IDatabaseResponseHandler<Product> responseHandler) {

        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + createProductParamUrl(product, ACTION_DELETE_FAVORITE_PRODUCT) +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void DeleteFavoriteStore(Store store, IDatabaseResponseHandler<Store> responseHandler) {

        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + createStoreParamUrl(store, ACTION_DELETE_FAVORITE_STORE) +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    public void GetNewUserId(String bullshitAboutUser, IDatabaseResponseHandler<String> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_GET_NEW_USER_ID + "?" + User.TAG_DATA + "=" + bullshitAboutUser);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void SetUserIdentity(){
        UserIdentity ui = new UserIdentity(this, context);
    }



    //-----------------------------------------
    //      ... GENERAL METHODS down there ...
    //-----------------------------------------

    private void ExecuteStringRequest(StringRequest request) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);
    }

    // MANAGER HELPER METHODS

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
    // pack out response and pass it to listeners as array of objects
    private class OnStoreProductPrice implements Response.Listener<String> {

        private ArrayList<StoreProductPrice> data;
        private IDatabaseResponseHandler handler;
        private Handler parseHandler = new Handler(Looper.getMainLooper()) {
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
                            data.add(new StoreProductPrice(jobj));
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
    // pack out response and pass it to listener as array of objects
    private class OnStore implements Response.Listener<String> {

        ArrayList<Store> data;
        private IDatabaseResponseHandler handler;
        private Handler parseHandler = new Handler(Looper.getMainLooper()) {
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
    // pack out response and pass it to listener as array of objects
    private class OnProduct implements Response.Listener<String> {

        private IDatabaseResponseHandler handler;
        private ArrayList<Product> data;
        private Handler parseHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                if(handler == lastProductHandler) {
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
    // pack out response and pass it to listener as array of objects
    private class OnBarcode implements Response.Listener<String> {

        private IDatabaseResponseHandler handler;
        private ArrayList<Barcode> data;
        private Handler parseHandler = new Handler(Looper.getMainLooper()) {
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
    // pack out response and pass it to listener
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

}
