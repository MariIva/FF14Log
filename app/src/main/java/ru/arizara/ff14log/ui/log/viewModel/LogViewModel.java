package ru.arizara.ff14log.ui.log.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.R;
import ru.arizara.ff14log.ui.log.LogList;

public class LogViewModel extends AndroidViewModel {


    private  MutableLiveData<List<LogList>> rvLogs;

    public LogViewModel(@NonNull Application application) {
        super(application);
    }


    /* private LogLiveData(Context context) {
         rvLogs = new MutableLiveData<>();
         List<LogList> list = new ArrayList<>();
         list.add(new LogList(context.getString(R.string.orchestrions)));

         rvLogs.setValue(list);
     }

     public LiveData<List<LogList>> getList() {
         return rvLogs;
     }*/
   public LiveData<List<LogList>> getData() {
       if (rvLogs == null) {
           rvLogs = new MutableLiveData<>();
           loadData();
       }
       return rvLogs;
   }

    private void loadData() {
        List<LogList> list = new ArrayList<>();
        list.add(new LogList((byte) 0, getApplication().getString(R.string.orchestrions)));
        rvLogs.setValue(list);
    }

}