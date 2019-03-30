package alerta.riesgos.naturales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alerta.riesgos.naturales.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    String [] titulos = {
        "Título 1", "Título 2", "Título 3", "Título 4", "Título 6", "Título 7",
    };

    String [] artículo = {
        "Contenido del artículo"
    };

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

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
