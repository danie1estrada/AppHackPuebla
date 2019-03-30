package alerta.riesgos.naturales.activities;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import alerta.riesgos.naturales.R;
import alerta.riesgos.naturales.services.Queue;

public class ViewTraceRoute extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Queue queue;
    private MyLocation location;
    private ArrayList<LatLng> locations;
    private MarkerOptions options;
    private Marker marker;

    Timer timer;
    final Handler handler = new Handler();
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trace_route);

        this.location = new MyLocation(this);
        this.locations = new ArrayList<LatLng>();
        options = new MarkerOptions();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewTraceRoute);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getLocation(), 13.0f ));
        options.position(location.getLocation());
        marker = mMap.addMarker(options);

        timer.schedule(task, 1, 5000);
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
        //Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        try {
            JSONObject position = new JSONObject(response);
            JSONObject locacion = position.getJSONObject("locacion");
            marker.setPosition(new LatLng(
                    Double.parseDouble(locacion.get("latitud").toString()),
                    Double.parseDouble(locacion.get("longitud").toString())
            ));

        } catch (JSONException e) { }
    }

    public void error(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
