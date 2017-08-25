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
    private static final String ACTION_FIND_PRODUCTS_BY_NAME_AND_STORE_IN_FAVORITES = "findProductsByNameAndStoreInFavorites.php";
    private static final String ACTION_FIND_PRODUCTS_BY_NAME_IN_FAVORITES = "findProductsByNameInFavorites";

    private static final String ACTION_FIND_STORE_BY_NAME = "findStoreByName";
    private static final String ACTION_FIND_STORE_BY_LOCATION = "findStoreByLocation";
    private static final String ACTION_FIND_STORE_BY_STRING_KEY = "findStoreByStringKey.php";
    private static final String ACTION_FIND_STORE_BY_NAME_AND_LOCATION = "findStoreByNameAndLocation.php";
    private static final String ACTION_STORE_BY_NAME_LOCATION_AND_PRODUCT = "findStoreByNameLocationAndProduct.php";
    private static final String ACTION_FIND_STORE_BY_NAME_lOCATION_AND_PRODUCT_IN_FAVORITES =
            "findStoreByNameLocationAndProductInFavorites.php";
    private static final String ACTION_FIND_STORE_BY_NAME_ADN_LOCATION_IN_FAVORITES = "findStoreByNameAndLocationInFavorites.php";

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

    /**
     *
     * @param url server url where to call php files
     * @param context application con
     */
    public RemoteDatabase(String url, Context context) {
        this.url = url;
        this.context = context;
        user = new UserIdentity(this, context);
    }

    public void AddProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_ADD_PRODUCT, product, responseHandler);
    }


    /**
     *
     * @param product product from space
     * @param barcode space product barcode
     * @param responseHandler a guy hwo cares what server think about this operation
     */
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

    /**
     * Request to update barcode and return server thoughts about this operation.
     * @param product object to delete
     * @param responseHandler this handles returned server response message
     */
    public void UpdateProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_UPDATE_PRODUCT, product, responseHandler);
    }

    /**
     * Request to update store and return server thoughts about this operation.
     * @param store object to delete
     * @param responseHandler this handles returned server response message
     */
    public void UpdateStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_UPDATE_STORE, store, responseHandler);
    }

    /**
     * Request to update store product price and return server thoughts about this operation.
     * @param storePruductPrice object to delete
     * @param responseHandler this handles returned server response message
     */
    public void UpdateStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_UPDATE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    /**
     * Request to update barcode and return server thoughts about this operation.
     * @param barcode object to delete
     * @param responseHandler this handles returned server response message
     */
    public void UpdateBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_UPDATE_BACRODE, barcode, responseHandler);
    }

    /**
     * Request to delete product and return server thoughts about this operation.
     * @param product object to delete
     * @param responseHandler this handles returned server response message
     */
    // TODO make sure it delete all references in database
    public void DeleteProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {
        ManageProductByAction(ACTION_DELETE_PRODUCT, product, responseHandler);
    }

    /**
     * Request to delete store and return server thoughts about this operation.
     * @param store object to delete
     * @param responseHandler this handles returned server response message
     */
    // TODO make sure it delete all references in database
    public void DeleteStore(Store store, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreByAction(ACTION_DELETE_STORE, store, responseHandler);
    }

    /**
     * Request to delete product price in store and return server thoughts about this operation.
     * @param storePruductPrice describes product price in store
     * @param responseHandler this handles returned server response message
     */
    public void DeleteStoreProductPrice(StoreProductPrice storePruductPrice, IDatabaseResponseHandler<String> responseHandler) {
        ManageStoreProductPriceByAction(ACTION_DELETE_STOREPRODUCTPRICE, storePruductPrice, responseHandler);
    }

    /**
     * Request to delete barcode and return server thoughts about this operation.
     * @param barcode object to delete
     * @param responseHandler this handles returned server response message
     */
    public void DeleteBarcode(Barcode barcode, IDatabaseResponseHandler<String> responseHandler) {
        ManageBarcodeByAction(ACTION_DELETE_BACRODE, barcode, responseHandler);
    }



    /**
     * It does what it says.. use it carefully.. data could be huge.
     * @param responseHandler this handle returned data
     */
    public void GetAllProducts(IDatabaseResponseHandler<Product> responseHandler) {
        this.doThis(ACTION_GET_ALL_PRODUCTS, responseHandler, new OnProduct(responseHandler));
        this.lastProductHandler = responseHandler;
    }

    /**
     * It does what it says.. use it carefully.. data could be huge.
     * @param responseHandler this handle returned data
     */
    public void GetAllStores(IDatabaseResponseHandler<Store> responseHandler) {
        this.doThis(ACTION_GET_ALL_STORES, responseHandler, new OnStore(responseHandler));
        this.lastStoreHandler = responseHandler;
    }

    /**
     * It does what it says.. use it carefully.. data could be huge.
     * @param responseHandler this handle returned data
     */
    public void GetAllBarcodes(IDatabaseResponseHandler<Barcode> responseHandler) {
        this.doThis(ACTION_GET_ALL_BARCODES, responseHandler, new OnBarcode(responseHandler));
        this.lastBarcodeHandler = responseHandler;
    }

    /**
     * It does what it says.. use it carefully.. data could be huge.
     * @param responseHandler this handle returned data
     */
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


    /**
     * Return StoreProductPrice objects(Store, Product and their price in store)
     * where product is in store('storeID') and product name is like 'productName'
     * @param storeId
     * @param productName
     * @param responseHandler
     */
    public void FindProductInStoreByName(int storeId, String productName, IDatabaseResponseHandler<StoreProductPrice> responseHandler) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + ACTION_FIND_PRODUCTS_BY_NAME_AND_STORE_ID +
                "?" + Store.TAG_ID + "=" + storeId + "&" + Product.TAG_NAME  + "=" + productName);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;
    }

    /**
     * Return Product object
     * @param barCode
     * @param responseHandler this handles returned data
     */
    public void FindProductByBarCode(String barCode, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_BARCODE, Barcode.TAG_BARCODE, barCode, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    /**
     *  Return Product objects where product name is like 'name'
     * @param name
     * @param responseHandler this handle returned data
     */
    public void FindProductByName(String name, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_NAME, Product.TAG_NAME, name, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    public void FindProductByNameInFavorites(String name, IDatabaseResponseHandler<Product> responseHandler) {
        String requestUrl = this.removeWhiteSpaceFromUrl(this.url + ACTION_ADD_PRODUCT_AND_BARCODE +
                "?" + Product.TAG_NAME + "=" + name + "&" + User.TAG_ID  + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnProduct(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastProductHandler = responseHandler;
    }

    /**
     * Return Product objects where product name or category is like 'value'
     * @param category
     * @param responseHandler
     */
    public void FindProductByCategory(String category, IDatabaseResponseHandler<Product> responseHandler) {
        FindProductByStringKey(ACTION_FIND_PRODUCT_BY_CATEGORY, Product.TAG_CATEGORY, category, responseHandler);
        this.lastProductHandler = responseHandler;
    }

    /**
     * Return Product objects where product name or category is like 'value'
     * @param value
     * @param responseHandler this handles returned data
     */
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


    /**
     * Returned Products filtered by name and Store and are in user Favorite Products
     * @param store
     * @param productName
     * @param responseHandler
     */
    public void FindProductByNameAndStoreInFavorites(
            Store store, String productName, IDatabaseResponseHandler<StoreProductPrice> responseHandler) {

        String requestUrl = removeWhiteSpaceFromUrl(createStoreParamUrl(
                store, ACTION_FIND_PRODUCTS_BY_NAME_AND_STORE_IN_FAVORITES) + "&" +
                Product.TAG_NAME + "=" + productName + "&" +
                User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;

    }



    // FIND STORES..........

    /**
     * Return Store objects where store name is like 'key'
     * @param key
     * @param responseHandler this handles returned data
     */
    public void FindStoreByName(String key, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_NAME, Store.TAG_NAME, key, responseHandler);
        this.lastStoreHandler = responseHandler;
    }

    /**
     * Return Store objects where store location is like 'location'
     * @param location
     * @param responseHandler this handles returned data
     */
    public void FindStoreByLocation(String location, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_LOCATION, Store.TAG_LOCATION, location, responseHandler );
        this.lastStoreHandler = responseHandler;
    }

    /**
     * Return Store objects where store name is like 'name' and store location is like 'location'
     * @param name
     * @param location
     * @param responseHandler this handles returned data
     */
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

    public void FindStoreByNameAndLocationInFavorites(String name, String location, IDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = this.url + ACTION_FIND_STORE_BY_NAME_ADN_LOCATION_IN_FAVORITES + "?" +
                Store.TAG_NAME + "=" + name + "&" +
                Store.TAG_LOCATION + "=" + location + "&" +
                User.TAG_ID + "=" + user.GetID();

        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreHandler = responseHandler;
    }



    /**
     * Return Store objects where store name or location is like 'key'
     * @param key
     * @param responseHandler this should implement logic for returnde data
     */
    public void FindStoreByStringKey(String key, IDatabaseResponseHandler<Store> responseHandler) {
        FindStoreByStringKey(ACTION_FIND_STORE_BY_STRING_KEY, KEY_STR_KEY, key, responseHandler);
        this.lastStoreHandler = responseHandler;
    }

    private void FindStoreByStringKey(String action, String key, String value, IDatabaseResponseHandler<Store> responseHandler) {
        String requestUrl = this.url + action + "?" + key + "=" + value;
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStore(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }


    /**
     * Return store array where Store name like storeName and Store location like storeLocation
     * and Store contains this product
     * @param product desired product
     * @param storeName store name key
     * @param storeLocation store product key
     * @param responseHandler this should know what to do with returned data
     */
    public void FindStoreByNameLocationAndProduct(
            Product product, String storeName, String storeLocation, IDatabaseResponseHandler<StoreProductPrice> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(createProductParamUrl(
                product, ACTION_STORE_BY_NAME_LOCATION_AND_PRODUCT) + "&" +
                Store.TAG_NAME + "=" + storeName + "&" +
                Store.TAG_LOCATION + "=" + storeLocation);
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;
    }


    /**
     * Return Store and product price in this store where filtered by product name store name and locaction
     * and all stores should be in user Favorites
     * @param product chosen product by user
     * @param storeName desired store name
     * @param storeLocation desired store location
     * @param responseHandler a guy hwo cares what will happen after this call
     */
    public void FindStoreByNameLocationAndProductInFavorites(
            Product product, String storeName, String storeLocation, IDatabaseResponseHandler<StoreProductPrice> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(createProductParamUrl(
                product, ACTION_FIND_STORE_BY_NAME_lOCATION_AND_PRODUCT_IN_FAVORITES) + "&" +
                Store.TAG_NAME + "=" + storeName + "&" +
                Store.TAG_LOCATION + "=" + storeLocation + "&" +
                User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnStoreProductPrice(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
        this.lastStoreProductPriceHandler = responseHandler;
    }




    /**
     * Return StoreProductPrice objects (Store, Product and product price in store) matched by Product;
     * @param product Product returned form sever in some previous search
     * @param responseHandler a guy who cares about returned data
     */
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

    /**
     * Return all Products identified by unique user id assigned to machine
     * @param responseHandler a guy hwo cares about data returned form server
     */
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
                this.url + ACTION_SET_FAVORITE_STORE + "?" + Store.TAG_ID + "=" + storeID + "&" +
                            User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void SetFavoriteProduct(int productID, IDatabaseResponseHandler<String> responseHandler) {
        String requestUrl = removeWhiteSpaceFromUrl(
                this.url + ACTION_SET_FAVORITE_PRODUCT + "?" +Product.TAG_ID + "=" + productID + "&" +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void DeleteFavoriteProduct(Product product, IDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = removeWhiteSpaceFromUrl(
                createProductParamUrl(product, ACTION_DELETE_FAVORITE_PRODUCT) + "&" +
                        User.TAG_ID + "=" + user.GetID());
        StringRequest strRequest =
                new StringRequest(Request.Method.GET, requestUrl,
                        new OnString(responseHandler), new OnError(responseHandler));
        this.ExecuteStringRequest(strRequest);
    }

    public void DeleteFavoriteStore(Store store, IDatabaseResponseHandler<String> responseHandler) {

        String requestUrl = removeWhiteSpaceFromUrl(
                createStoreParamUrl(store, ACTION_DELETE_FAVORITE_STORE) + "&" +
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
