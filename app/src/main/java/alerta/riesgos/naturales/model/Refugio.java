package alerta.riesgos.naturales.model;

import com.google.android.gms.maps.model.LatLng;

public class Refugio {
    public String id;
    public String nombre;
    public LatLng location;

    public Refugio(String id, String nombre, LatLng location){
        this.id = id;
        this.nombre = nombre;
        this.location = location;
    }

    @Override
    public String toString(){
        return "Refugio [" + this.nombre + "]["+ this.location + "]";
    }
}

