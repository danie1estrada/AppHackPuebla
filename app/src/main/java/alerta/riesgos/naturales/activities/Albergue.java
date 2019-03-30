package alerta.riesgos.naturales.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import alerta.riesgos.naturales.R;
import alerta.riesgos.naturales.model.Refugio;
import alerta.riesgos.naturales.services.Queue;

public class Albergue extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Queue queue;
    private MyLocation location;
    private ArrayList<Refugio> refugios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albergue);

        this.location = new MyLocation(this);
        this.refugios = new ArrayList<Refugio>();

        SupportMapFragment mapFrangement = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapAlbergues);

        mapFrangement.getMapAsync(this);
        queue = Queue.getInstance(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.putMarker(this.location.getLocation(), "Posición actual",0, true);
        //this.putMarker(new LatLng(-98.2448816, 19.0196216), "albergue",2, true);

        this.getAlbergues();
    }


    public void getAlbergues(){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://104.237.130.36:3000/api/refugios",
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
        );
        queue.addToQueue(request);
    }

    public void responseHandler(String res){
        try {
            JSONArray response = new JSONArray(res);
            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            for(int i = 0, e = response.length(); i < e; i++){
                JSONObject rawAlbergue = (JSONObject) response.get(i);

                Double latitud = Double.parseDouble(rawAlbergue.get("longitd").toString());
                Double longitud =Double.parseDouble(rawAlbergue.get("latitud").toString());

                LatLng latlng = new LatLng(latitud, longitud);
                Refugio refugio = new Refugio(
                        rawAlbergue.get("id").toString(),
                        rawAlbergue.get("nombre").toString(),
                        latlng
                );

                refugios.add(refugio);
                System.out.println(refugio);
                this.putMarker(latlng, refugio.nombre, 2,false);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public void errorHandler(VolleyError  error){ }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void putMarker(LatLng location, String title, int imageType, boolean moveCamera){
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
}
