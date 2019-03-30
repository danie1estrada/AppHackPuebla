package alerta.riesgos.naturales.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import alerta.riesgos.naturales.services.Queue;

public class MyLocation implements LocationListener {

    Context context;
    Queue queue;
    LocationManager locationManager;

    public MyLocation(Context context) {
        this.context = context;
        this.queue = Queue.getInstance(this.context);

        this.locationManager = (LocationManager)
                this.context.getSystemService(Context.LOCATION_SERVICE);
    }

    public LatLng getLocation() {
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
        Location lastKnownLocation = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }


    public void checkOnChangeLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 30, 0, this);
    }

    public void revokeCheckOnChangeLocation(){
        this.locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.sendLocation(location);
    }

    public void sendLocation(Location location){
        final String lng = Double.toString(location.getLongitude());
        final String lat =  Double.toString(location.getLatitude());

        final Toast info = Toast.makeText(context,
                location.getLatitude() + " " + location.getLongitude(),
                Toast.LENGTH_SHORT);

        JSONObject body = new JSONObject();
        try {
            body.put("idPersona", "5c9e95679211f52bacde001f");
            body.put("longitud", lng);
            body.put("latitud", lat);
        } catch (JSONException e) { }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                "http://104.237.130.36:3000/api/locaciones-tiempo-real",
                body,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        info.show();
                        a(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.err.println(error);
                        Toast.makeText(context,
                                "Error al enviar la información de tu ubicación",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ){
          @Override
          protected Map<String, String> getParams(){
              Map<String, String> params = new HashMap<String, String>();
              params.put("id", "5c9e95679211f52bacde001f");
              params.put("longitud", lng);
              params.put("latitud", lat);

              return params;
          }
        };

        queue.addToQueue(request);
    }

    public void a(Object error) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
