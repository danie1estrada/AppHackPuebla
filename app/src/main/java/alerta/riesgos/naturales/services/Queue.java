package alerta.riesgos.naturales.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Queue {

    private static Queue instance;
    private static RequestQueue requestQueue;

    private Queue(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized Queue getInstance(Context context) {
        if (instance == null) {
            instance = new Queue(context);
        }
        return instance;
    }

    public <T> void addToQueue(Request request) {
        requestQueue.add(request);
    }
}
