package ru.arizara.ff14log.ui.log.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;

public class OrchestrionsAdapter extends RecyclerView.Adapter<OrchestrionsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Orchestrion> list;

    public OrchestrionsAdapter(Context context, List<Orchestrion> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_orchestrion_item, parent, false);
        return new OrchestrionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orchestrion orchestrion = list.get(position);
        holder.tvName.setText(orchestrion.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //final ConstraintLayout cL;
        final ImageView imgOrc;
        final ImageView imgLocation;
        final TextView tvName;
        final Switch twCheck;
        ViewHolder(View view){
            super(view);
            imgOrc = (ImageView)view.findViewById(R.id.img_orc);
            imgLocation = (ImageView)view.findViewById(R.id.img_location);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            twCheck = (Switch) view.findViewById(R.id.sw_check);
        }
    }
}
