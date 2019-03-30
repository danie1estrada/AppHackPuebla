package alerta.riesgos.naturales.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import alerta.riesgos.naturales.R;

public class TransmititrUbicacionActivity extends AppCompatActivity {

    boolean transmitiendo = false;
    MyLocation location;
    Button trasmitirUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmititr_ubicacion);
        this.trasmitirUbicacion = findViewById(R.id.btn_trasmitir);
        this.location = new MyLocation(this);

        getSupportActionBar().setTitle("Transmitir ubicación");
    }

    public void reportLocation(View view) {
        if (!transmitiendo) {
            trasmitirUbicacion.setText("Detener transmisión");
            Toast.makeText(this, "Enviando tu ubicación", Toast.LENGTH_LONG).show();
            this.location.checkOnChangeLocation();
        } else {
            trasmitirUbicacion.setText("Transmitir");
            Toast.makeText(this, "Se detuvo el envió de ubicación", Toast.LENGTH_LONG).show();
            this.location.revokeCheckOnChangeLocation();
        }
        transmitiendo = !transmitiendo;
    }
}
