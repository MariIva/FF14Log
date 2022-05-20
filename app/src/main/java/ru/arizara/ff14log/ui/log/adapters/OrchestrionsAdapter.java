package ru.arizara.ff14log.ui.log.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.LogList;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;
import ru.arizara.ff14log.ui.log.fragments.DescriptionDialog;

public class OrchestrionsAdapter extends RecyclerView.Adapter<OrchestrionsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<OrchestrionWithCategory> list;
    private LogList log;
    private Context context;
    private Bitmap bitmap;

    /**
     *
     */
    public OrchestrionsAdapter(Context context, List<OrchestrionWithCategory> list) {
        this.context = context ;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        new Thread(new Runnable() {
            @Override
            public void run() {
                log = LogsDB.getLogByName(context.getResources().getString(R.string.orchestrions));
            }
        }).start();
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
        OrchestrionWithCategory orchestrion = list.get(position);
        if (bitmap==null){
            try {
                FileInputStream fileInputStream = context.openFileInput(orchestrion.getOrchestrion().getIcon());
                bitmap = BitmapFactory.decodeStream(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //todo
        holder.tvName.setText(orchestrion.getOrchestrion().getName());
        holder.twCheck.setChecked(orchestrion.getOrchestrion().isCheck());
        holder.imgOrc.setImageBitmap(bitmap);

        holder.twCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                orchestrion.getOrchestrion().setCheck(isChecked);
                if(isChecked){
                    log.updateCurrent(1);
                }else {
                    log.updateCurrent(-1);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogsDB.updateOrchestrion(orchestrion.getOrchestrion());
                        LogsDB.updateLog(log);
                    }
                }).start();
            }
        });


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
    public void addOrchestrion(List<OrchestrionWithCategory> list){

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
