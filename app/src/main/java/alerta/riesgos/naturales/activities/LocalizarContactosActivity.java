package alerta.riesgos.naturales.activities;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import alerta.riesgos.naturales.R;
import alerta.riesgos.naturales.model.Persona;
import alerta.riesgos.naturales.services.Queue;

public class LocalizarContactosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Queue queue;
    private MyLocation location;
    private ArrayList<Persona> personas;

    private MarkerOptions options;

    Timer timer;
    final Handler handler = new Handler();
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.location = new MyLocation(this);
        this.personas = new ArrayList<Persona>();
        options = new MarkerOptions();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        queue = Queue.getInstance(this);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Se ejecuta cada 5 segundos
                        getContacto();
                    }
                });
            }
        };
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        putMarker(this.location.getLocation(), "Posición actual",0, true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getLocation(), 13.0f ));
        options.title("");
        options.position(location.getLocation());
        mMap.addMarker(options);

        timer.schedule(task, 1, 5000);
        Toast.makeText(this, "empieza", Toast.LENGTH_SHORT).show();
    }

    public void putMarker(LatLng location, String title,  int imageType, boolean moveCamera){
        Marker marker = mMap.addMarker(
            new MarkerOptions().position(location ).title(title)
        );

        switch (imageType){
            case 1: {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.man));
                break;
            }
            case 2: {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.home));
                break;
            }
        }
        if(moveCamera){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f ));
        }
    }

    public void getContactos() {
        StringRequest request = new StringRequest(
            Request.Method.GET,
            "http://104.237.130.36:3000/api/locaciones",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    responseHandler(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorHandler(error);
                }
            }
        ) {
            @Override
            protected Map<String, String> getParams() throws  AuthFailureError  {
                Map<String, String> params = new HashMap<>();
                params.put("filter", "{\"where\": { \"idPersona\": {\"inq\": [\"5c9e95889211f52bacde0020\"]}}}");
                return params;
            }
        };
        queue.addToQueue(request);
    }

    public void responseHandler(String res) {
        try {
            JSONArray response = new JSONArray(res);
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            for(int i = 0, e = response.length(); i < e; i++){
                    JSONObject rawPersona = (JSONObject) response.get(i);
                    JSONObject rawPersonaPersona = rawPersona.getJSONObject("persona");
                    LatLng l = new LatLng(
                            Double.parseDouble(rawPersona.get("latitud").toString()),
                            Double.parseDouble(rawPersona.get("longitud").toString())
                    );

                    Persona p = new Persona(
                            rawPersona.get("id").toString(),
                            rawPersonaPersona.get("nombre").toString(),
                            l
                    );

                    personas.add(p);
                    System.out.println(p);
                    this.putMarker(l, p.nombre, 1,false);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public void errorHandler(VolleyError error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }


    public void getContacto() {
        StringRequest request = new StringRequest(
            Request.Method.GET,
            "http://104.237.130.36:3000/api/locaciones-tiempo-real/5c9e95679211f52bacde001f/locacion",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    respuesta(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error(error);
                }
            }
        );

        queue.addToQueue(request);
    }

    public void respuesta(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        try {
            JSONObject position = new JSONObject(response);
            options.position(new LatLng(
                Double.parseDouble(position.get("latitud").toString()),
                Double.parseDouble(position.get("longitud").toString())
            ));
        } catch (JSONException e) { }
    }

    public void error(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
