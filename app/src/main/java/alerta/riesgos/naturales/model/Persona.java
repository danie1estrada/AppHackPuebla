package alerta.riesgos.naturales.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Persona implements Serializable {
    public String id;
    public String nombre;
    public LatLng location;

    public Persona(String id, String nombre, LatLng location){
        this.id = id;
        this.nombre = nombre;
        this.location = location;
    }

    @Override
    public String toString(){
        return "Persona [" + this.nombre + "]["+ this.location.latitude + "]["+ this.location.longitude+ "]";
    }
}
