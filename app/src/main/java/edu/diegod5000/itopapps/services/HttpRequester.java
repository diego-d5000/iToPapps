package edu.diegod5000.itopapps.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by diego-d on 16/02/16.
 */
public class HttpRequester {
    private static HttpRequester instance;
    private RequestQueue requestQueue;
    private static Context actualContext;

    private HttpRequester(Context context) {
        actualContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized HttpRequester getInstance(Context context) {
        if (instance == null) {
            instance = new HttpRequester(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(actualContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}