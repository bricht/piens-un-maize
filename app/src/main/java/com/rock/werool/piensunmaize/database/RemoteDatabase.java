package com.rock.werool.piensunmaize.database;

import android.content.Context;

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
    private static final String ACTION_ADD_STOREPRODUCTPRICE = "addStoreProductPrice";
    private static final String ACTION_ADD_BARCODE = "addBarcode.php";

    private static final String ACTION_UPDATE_PRODUCT = "updateProduct.php";
    private static final String ACTION_UPDATE_STORE = "updateStore.php";
    private static final String ACTION_UPDATE_STOREPRODUCTPRICE = "updateStoreProductPrice.php";
    private static final String ACTION_UPDATE_BACRODE = "updateProduct.php";

    private static final String ACTION_DELETE_PRODUCT = "deleteProduct.php";
    private static final String ACTION_DELETE_STORE = "deleteStore.php";
    private static final String ACTION_DELETE_STOREPRODUCTPRICE = "deleteStoreProductPrice.php";
    private static final String ACTION_DELETE_BACRODE = "deleteProduct.php";

    private static final String ACTION_FIND_PRODUCT_BY_BARCODE = "findProductByBarcode.php";
    private static final String ACTION_FIND_PRODUCT_BY_NAME = "findProductByName.php";
    private static final String ACTION_FIND_PRODUCT_BY_CATEGORY = "findProductByCategory.php";
    private static final String ACTION_FIND_PORDUCT_BY_STRING_KEY = "findProductByStringKey.php";

    private static final String ACTION_FIND_STORE_BY_NAME = "findStoreByName";
    private static final String ACTION_FIND_STORE_BY_LOCATION = "findStoreByLocation";
    private static final String ACTION_FIND_STORE_BY_STRING_KEY = "findStoreByStringKey.php";

    private static final String ACTION_FIND_STORE_PRODUCT_PRICE = "findStoreProductPrice.php";

    public static final String KEY_STR_KEY = "str_key";

    private String url;
    private Context context;

    public void setUrl(String url) {
        this.url = url;
    }

    public RemoteDatabase(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    public void AddProduct(Product product, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_ADD_PRODUCT, product, responseHandler);
    }

    public void AddStore(Store store, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_ADD_STORE, store, responseHandler);
    }

    public void AddStoreProductPrice(StoreProductPrice storePruductPrice, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_ADD_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void AddBarcode(Barcode barcode, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_ADD_BARCODE, barcode, responseHandler);
    }

    public void UpdateProduct(Product product, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_UPDATE_PRODUCT, product, responseHandler);
    }

    public void UpdateStore(Store store, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_UPDATE_STORE, store, responseHandler);
    }

    public void UpdateStoreProductPrice(StoreProductPrice storePruductPrice, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_UPDATE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void UpdateBarcode(Barcode barcode, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_UPDATE_BACRODE, barcode, responseHandler);
    }

    public void DeleteProduct(Product product, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_DELETE_PRODUCT, product, responseHandler);
    }

    public void DeleteStore(Store store, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_DELETE_STORE, store, responseHandler);
    }

    public void DeleteStoreProductPrice(StoreProductPrice storePruductPrice, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_DELETE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    public void DeleteBarcode(Barcode barcode, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_DELETE_BACRODE, barcode, responseHandler);
    }

    public void FindProductByBarCode(
            String barCode,
            IRemoteDatabaseResponseHandler<Product> responseHandler) {

        String requestUrl = this.url + ACTION_FIND_PRODUCT_BY_BARCODE + "?" + KEY_STR_KEY + "=" + barCode;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    // FIND PRODUCTS .........

    public void FindProductByName(String name, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_NAME, name, responseHandler);
}

    public void FindProductByCategory(String category, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_CATEGORY, category, responseHandler);
    }

    public void FindProductByStringKey(String key, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PORDUCT_BY_STRING_KEY, key, responseHandler);
    }

    private void FindProductByStringKey(String action, String key, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        String requestUrl = this.url + action + "?" + KEY_STR_KEY + "=" + key;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void FindStoreByName(String key, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, key, responseHandler);
    }


    // FIND STORES..........

    public void FindStoreByLocation(String location, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_LOCATION, location, responseHandler );
    }

    public void FinStoreByStringKey(String key, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_STRING_KEY, key, responseHandler);
    }

    private void FindStoreByStringKey(String action, String key, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = this.url + action + "?" + KEY_STR_KEY + "=" + key;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    // FIND STORE PRODUCT PRICE

    public void FindStoreProductPrice(
            Product product,
            IRemoteDatabaseResponseHandler<StoreProductPrice> responseHandler){

        String requestUrl = this.url + createProductParamUrl(product,ACTION_FIND_STORE_PRODUCT_PRICE) ;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }



    //-------------------------------------
    //      GENERAL METHODS down there ...
    //-------------------------------------

    private void ExecuteStringRequest(StringRequest request) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);
    }


    // MANAGEER HELPER METHODS

    private void ManageProductByAction(
            String action,
            Product product,
            IRemoteDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = createProductParamUrl(product,action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageStoreByAction(
            String action,
            Store store,
            IRemoteDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = createStoreParamUrl(store, action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageStoreProductPriceByAction(
            String action,
            StoreProductPrice storeProductPrice,
            IRemoteDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = createStoreProductPriceParamUrl(storeProductPrice, action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageBarcodeByAction(
            String action,
            Barcode barcode,
            IRemoteDatabaseResponseHandler<String> responseHandler){

        String requestUrl = createBarcodeRequestUrl(barcode, action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnBarcode(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void FindProductByAction(
            String action,
            Product product,
            IRemoteDatabaseResponseHandler<Product> responseHandler) {

        String requestUrl = createProductParamUrl(product,action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    private void FindStoreByAction(
            String action,
            Store store,
            IRemoteDatabaseResponseHandler<Store> responseHandler) {

        String requestUrl = createStoreParamUrl(store, action);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    private String createProductParamUrl(Product product, String action) {
        return this.url + action + "?" +
                Product.TAG_ID + "=" + product.getId() + "&" +
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
                StoreProductPrice.TAG_STORE_ID + "=" + storeProductPrice.getStore().getId() + "&" +
                StoreProductPrice.TAG_STORE_NAME + "=" + storeProductPrice.getStore().getName() + "&" +
                StoreProductPrice.TAG_STORE_LOCATION + "=" + storeProductPrice.getStore().getLocation() + "&" +
                StoreProductPrice.TAG_PRODUCT_ID + "=" + storeProductPrice.getProduct().getId() + "&" +
                StoreProductPrice.TAG_PRODUCT_NAME + "=" + storeProductPrice.getProduct().getName() + "&" +
                StoreProductPrice.TAG_PRODUCT_CATEGORY + "=" + storeProductPrice.getProduct().getCategory() + "&" +
                StoreProductPrice.TAG_PRODUCT_DESCRIPTION + "=" + storeProductPrice.getProduct().getDescription() + "&" +
                StoreProductPrice.TAG_PRODUCT_AVERAGE_PRICE + "=" + storeProductPrice.getProduct().getAvaragePricePrice() + "&" +
                StoreProductPrice.TAG_PRICE + "=" + storeProductPrice.getPrice() + "&" +
                StoreProductPrice.TAG_LAST_UPDATED + "=" + storeProductPrice.getLastUpdated() + "&";
    }

    private String createBarcodeRequestUrl(Barcode barcode, String action) {
        return this.url + action + "?" +
                Barcode.TAG_BARCODE + "=" + barcode.getBarcode() + "&" +
                Barcode.TAG_PRODUCT_ID + "=" + barcode.getProduct_id();
    }


    //------------------------------------
    //      HELPER CLASSES down there ...
    //------------------------------------


    // helper class for expected StoreProductPrice response
    private class OnStoreProductPrice implements Response.Listener<String> {

        private IRemoteDatabaseResponseHandler handler;

        public OnStoreProductPrice(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<StoreProductPrice> storeProductPrices = new ArrayList<StoreProductPrice>();
            try {
                JSONArray jarray = new JSONArray(response);
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);
                    storeProductPrices.add(new StoreProductPrice(jobj));
                }
                handler.onArrive(storeProductPrices);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // helper class for expected Store response
    private class OnStore implements Response.Listener<String> {

        private IRemoteDatabaseResponseHandler handler;

        public OnStore(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<Store> stores = new ArrayList<Store>();
            try {
                JSONArray jarray = new JSONArray(response);
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);
                    stores.add(new Store(jobj));
                }
                handler.onArrive(stores);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // helper class for expected Product response
    private class OnProduct implements Response.Listener<String> {

        private IRemoteDatabaseResponseHandler handler;

        public OnProduct(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<Product> products = new ArrayList<Product>();
            try {
                JSONArray jarray = new JSONArray(response);
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);
                    products.add(new Product(jobj));
                }
                handler.onArrive(products);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // helper class for expected barcode response
    private class OnBarcode implements Response.Listener<String> {

        private IRemoteDatabaseResponseHandler handler;

        public OnBarcode(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<Barcode> products = new ArrayList<Barcode>();
            try {
                JSONArray jarray = new JSONArray(response);
                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);
                    products.add(new Barcode(jobj));
                }
                handler.onArrive(products);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // helper class for expected String response
    private class OnString implements Response.Listener<String> {

        private IRemoteDatabaseResponseHandler handler;

        public OnString(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            handler.onArrive(new ArrayList<String>());
        }
    }


    // helper class for handling error response
    private class OnError implements Response.ErrorListener {

        private IRemoteDatabaseResponseHandler handler;

        public OnError(IRemoteDatabaseResponseHandler h) {
            handler = h;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            handler.onError(error);
        }
    }

}
