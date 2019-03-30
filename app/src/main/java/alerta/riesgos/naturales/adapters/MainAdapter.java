package alerta.riesgos.naturales.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import alerta.riesgos.naturales.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    String [] titulos = {
        "Reporte del monitoreo de CENAPRED al volcán Popocatépetl",
        "Tres huracanes azotan a la vez el Golfo de México y el Caribe",
        "Consejos para tener en cuenta en caso de desastre"
    };

    String [] contenido = {
        "Semáforo de alerta volcánica AMARILLO - FASE 3. El CENAPRED exhorta a NO ACERCARSE al volcán y sobre todo al cráter, por el peligro que implica la caída de fragmentos balísticos.",
        "Una de las tormentas más potentes en los registros de observaciones en el Atlántico, azota con fuerza inusual las islas del noreste de ese mar, el Centro Nacional de Huracanes de EE.UU.",
        "Fenómenos como un terremoto nos hace replantearnos la importancia de la prevención, visualizando esta como la oportunidad de salvaguardar nuestra integridad física y evitar más desastre del que por sí solo causan los fenómenos naturales."
    };

    int [] images = { R.drawable.img_article_1, R.drawable.img_article_2, R.drawable.img_article_3 };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
            LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.layout_item_main, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(titulos[i]);
        viewHolder.content.setText(contenido[i]);
        viewHolder.image.setImageDrawable(AppCompatResources.getDrawable(context, images[i]));
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, content;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.label_article_title);
            content = itemView.findViewById(R.id.label_article_content);
            image = itemView.findViewById(R.id.image_article_main);
        }
    }
}
