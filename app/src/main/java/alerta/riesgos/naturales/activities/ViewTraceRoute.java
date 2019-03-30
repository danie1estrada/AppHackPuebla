package alerta.riesgos.naturales.activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.ArrayList;

import alerta.riesgos.naturales.R;
import alerta.riesgos.naturales.services.Queue;

public class ViewTraceRoute extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Queue queue;
    private MyLocation location;
    private ArrayList<LatLng> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trace_route);

        this.location = new MyLocation(this);
        this.locations = new ArrayList<LatLng>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewTraceRoute);
        mapFragment.getMapAsync(this);
        queue = Queue.getInstance(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.traceRoute();
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

    public void traceRoute(){
        putMarker(this.location.getLocation(), "Posisión", 0, true);
        this.locations.add(new LatLng(19.021016, -98.248955));
        this.locations.add(new LatLng(19.021037, -98.248910));


        PolylineOptions opt = new PolylineOptions();

        for (int i = 0, e = this.locations.size(); i < e; i++) {

        }

        opt.add(new LatLng(19.021016, -98.248910),new LatLng(19.021016, -98.248915));


        opt.width(5);
        opt.color(Color.RED);

        mMap.addPolyline(opt);
    }
}
