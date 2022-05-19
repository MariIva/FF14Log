package ru.arizara.ff14log.ui.log.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.entities.LogList;

/**
 * Адаптер для списка логов
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<LogList> logList;

    /**
     *
     */
    public LogAdapter(Context context, List<LogList> logList) {
        this.inflater = LayoutInflater.from(context);
        this.logList = logList;
    }


    /**
     *
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_log_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogList log = logList.get(position);
        //todo
        //holder.imgLog.setImageResource(log.getFlagResource());
        holder.tvLogName.setText(log.getName());
        holder. tvLogProg.setText(log.getCurrent()+"/"+ log.getCount());

        // слушатель на клик на кнопку в карте
        holder.cL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo сравнение по названию tvLogName
                switch (log.getId()){
                    case 1:
                        Navigation.findNavController(v).navigate(R.id.action_nav_log_to_orchestrionsListFragment);
                        break;
                }
            }
        });
    }

    /**
     *
     */
    public void addLogs(List<LogList> list){

        logList.clear();
        logList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     *
     */
    @Override
    public int getItemCount() {
        return logList.size();
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout cL;
        final ImageView imgLog;
        final TextView tvLogName, tvLogProg;
        ViewHolder(View view){
            super(view);
            cL = (ConstraintLayout)view.findViewById(R.id.c_l);
            imgLog = (ImageView)view.findViewById(R.id.img_log);
            tvLogName = (TextView) view.findViewById(R.id.tv_log_name);
            tvLogProg = (TextView) view.findViewById(R.id.tv_log_prog);
        }
    }

}


