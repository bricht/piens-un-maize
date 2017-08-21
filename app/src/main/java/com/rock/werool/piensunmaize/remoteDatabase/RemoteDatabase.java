package com.rock.werool.piensunmaize.remoteDatabase;

import android.content.Context;
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

    //Server side done
    public void AddProduct(Product product, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_ADD_PRODUCT, product, responseHandler);
    }

    //Server side done
    public void AddStore(Store store, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_ADD_STORE, store, responseHandler);
    }

    //Server side done
    public void AddStoreProductPrice(StoreProductPrice storePruductPrice, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_ADD_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    //Server side done
    public void AddBarcode(Barcode barcode, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_ADD_BARCODE, barcode, responseHandler);
    }
    //Server side done
    public void UpdateProduct(Product product, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_UPDATE_PRODUCT, product, responseHandler);
    }
    //Server side done
    public void UpdateStore(Store store, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_UPDATE_STORE, store, responseHandler);
    }
    //Server side done
    public void UpdateStoreProductPrice(StoreProductPrice storePruductPrice, IRemoteDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_UPDATE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }
    //Server side done
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

    // FIND PRODUCTS .........
    //Server side done
    public void FindProductByBarCode(String barCode, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_BARCODE, Barcode.TAG_BARCODE, barCode, responseHandler);
    }
    //Server side done
    public void FindProductByName(String name, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_NAME, Product.TAG_NAME, name, responseHandler);
    }
    //Server side done
    public void FindProductByCategory(String category, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_CATEGORY, Product.TAG_CATEGORY, category, responseHandler);
    }
    //Server side done
    public void FindProductByStringKey(String value, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PORDUCT_BY_STRING_KEY, KEY_STR_KEY ,value, responseHandler);
    }

    private void FindProductByStringKey(String action, String key, String value, IRemoteDatabaseResponseHandler<Product> responseHandler) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + action + "?" + key + "=" + value);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    // FIND STORES..........
    //Server side done
    public void FindStoreByName(String key, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, Store.TAG_NAME, key, responseHandler);
    }
    //Server side done
    public void FindStoreByLocation(String location, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_LOCATION, Store.TAG_LOCATION, location, responseHandler );
    }
    //Server side done
    public void FindStoreByStringKey(String key, IRemoteDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_STRING_KEY, KEY_STR_KEY, key, responseHandler);
    }

    private void FindStoreByStringKey(String action, String key, String value, IRemoteDatabaseResponseHandler<Store> responseHandler) {
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
            IRemoteDatabaseResponseHandler<StoreProductPrice> responseHandler){

        String requestUrl = this.removeWhiteSpaceFromUrl(
                createProductParamUrl(product,ACTION_FIND_STORE_PRODUCT_PRICE)) ;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler, product), new OnError(responseHandler));
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
            IRemoteDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = this.removeWhiteSpaceFromUrl(createStoreParamUrl(store, action));
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    private void ManageStoreProductPriceByAction(
            String action,
            StoreProductPrice storeProductPrice,
            IRemoteDatabaseResponseHandler<String> responseHandler) {

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
            IRemoteDatabaseResponseHandler<String> responseHandler){

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

        private IRemoteDatabaseResponseHandler handler;

        public OnStoreProductPrice(IRemoteDatabaseResponseHandler h, Product p) {
            handler = h;
        }
        @Override
        public void onResponse(String response) {
            ArrayList<StoreProductPrice> storeProductPrices = new ArrayList<StoreProductPrice>();
            try {
                Log.d("jsonerror", response);
                JSONArray jarray = new JSONArray(response);

                for(int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);
                    storeProductPrices.add(new StoreProductPrice(jobj));
                }
                handler.onArrive(storeProductPrices);

            } catch (JSONException e) {
                Log.d("jsonerror", e.getMessage());
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
            Log.d("jsonerror", response);
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
            ArrayList<String> str = new ArrayList<String>();
            str.add(response);
            handler.onArrive(str);
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
