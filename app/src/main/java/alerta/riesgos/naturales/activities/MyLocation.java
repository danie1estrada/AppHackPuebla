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

import com.google.android.gms.maps.model.LatLng;

public class MyLocation implements LocationListener {

    Context context;
    LocationManager locationManager;

    public MyLocation(Context context) {
        this.context = context;
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
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 0, this);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Toast.makeText(context, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
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
