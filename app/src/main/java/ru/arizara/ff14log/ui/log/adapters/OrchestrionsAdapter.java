package ru.arizara.ff14log.ui.log.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.fragments.DescriptionDialog;

public class OrchestrionsAdapter extends RecyclerView.Adapter<OrchestrionsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Orchestrion> list;
    Context context;

    /**
     *
     */
    public OrchestrionsAdapter(Context context, List<Orchestrion> list) {
        this.context = context ;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    /**
     *
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_orchestrion_item, parent, false);
        return new OrchestrionsAdapter.ViewHolder(view);
    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orchestrion orchestrion = list.get(position);
        //todo
        holder.tvName.setText(orchestrion.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescriptionDialog.display(
                        ((AppCompatActivity)context).getSupportFragmentManager(),
                        orchestrion);
            }
        });
    }

    /**
     *
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     *
     */
    public void addOrchestrion(List<Orchestrion> list){

        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
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
