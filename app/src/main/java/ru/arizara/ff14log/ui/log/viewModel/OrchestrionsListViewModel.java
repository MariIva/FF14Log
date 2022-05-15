package ru.arizara.ff14log.ui.log.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.rest.OrchestrionAPIVolley;

public class OrchestrionsListViewModel extends AndroidViewModel {


    private MutableLiveData<List<Orchestrion>> rvOrchestrionOrigin;
    private MutableLiveData<List<Orchestrion>> rvOrchestrion;
    private byte lengNameItem = 0;

    public OrchestrionsListViewModel(@NonNull Application application) {
        super(application);
    }


    Handler handler = new Handler() {   // создание хэндлера
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rvOrchestrionOrigin.setValue(new ArrayList<>(rvOrchestrion.getValue()));
        }
    };

    public LiveData<List<Orchestrion>> getData() {
        if (rvOrchestrion == null) {
            rvOrchestrionOrigin = new MutableLiveData<>();
            rvOrchestrion = new MutableLiveData<>();
            loadData();
        }
        return rvOrchestrion;
    }

    private void loadData() {
        //todo get data from base
        new OrchestrionAPIVolley(
                getApplication().getApplicationContext())
                .getAllOrchestrion(rvOrchestrion);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Orchestrion> list;
                do {
                    list = rvOrchestrion.getValue();
                }while(list == null);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public void searchItem(String textToSearch){
        List<Orchestrion> list;
        if (lengNameItem < textToSearch.length()) {
            list = rvOrchestrion.getValue();
        } else {
            list = new ArrayList<>(rvOrchestrionOrigin.getValue());
        }

        lengNameItem = (byte) textToSearch.length();
        if (lengNameItem != 0) {
            textToSearch = textToSearch.toLowerCase(Locale.ROOT);
            for (Orchestrion item : rvOrchestrionOrigin.getValue()) {
                String i = item.getName().toLowerCase(Locale.ROOT);
                if (!i.contains(textToSearch)) {
                    list.remove(item);
                }
            }
        }

        rvOrchestrion.setValue(list);
        Log.e("searchItem",rvOrchestrion.getValue().size()+"");
        Log.e("searchItem",rvOrchestrionOrigin.getValue().size()+"");
    }
}