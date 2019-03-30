package alerta.riesgos.naturales.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import alerta.riesgos.naturales.R;
import alerta.riesgos.naturales.model.Persona;

public class Contacs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacs);
    }

    public void goToViewTraceRoute(View view){
        Persona p = new Persona("5c9e95889211f52bacde0020", "persona2", null);
        Intent viewTraceRouteIntent= new Intent(this, ViewTraceRoute.class);
        viewTraceRouteIntent.putExtra("persona", p);
        startActivity(viewTraceRouteIntent);
    }
}
