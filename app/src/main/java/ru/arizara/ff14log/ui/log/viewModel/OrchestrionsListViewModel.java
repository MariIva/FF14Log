package ru.arizara.ff14log.ui.log.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.arizara.ff14log.DB.LogsDB;
import ru.arizara.ff14log.repository.OrchestrionRepo;
import ru.arizara.ff14log.repository.PatchRepo;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.Patch;
import ru.arizara.ff14log.ui.log.entities.subEntities.OrchestrionWithCategory;

public class OrchestrionsListViewModel extends AndroidViewModel {

    private OrchestrionRepo repo;
    private PatchRepo patchRepo;

    private LiveData<List<OrchestrionWithCategory>> rvOrchestrionOrigin;
    private MutableLiveData<List<OrchestrionWithCategory>> rvOrchestrion;

    private LiveData<List<Patch>> patches;
    private MutableLiveData<boolean[]> checkedPatches;


    private byte lengNameItem = 0;

    public OrchestrionsListViewModel(@NonNull Application application) {
        super(application);
        repo = new OrchestrionRepo(application);
        patchRepo = new PatchRepo(application);
        rvOrchestrionOrigin = repo.getAll();
        patches = patchRepo.getAll();
        loadData();
        /*List<Orchestrion> list = rvOrchestrionOrigin.getValue();
        if (list==null){
            rvOrchestrion.setValue(new ArrayList<>());
        }
        else {
            rvOrchestrion.setValue(new ArrayList<>(list));
        }*/
    }


   /* Handler handler = new Handler() {   // создание хэндлера
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rvOrchestrionOrigin.setValue(new ArrayList<>(rvOrchestrion.getValue()));
        }
    };*/

    public LiveData<List<OrchestrionWithCategory>> getDataList() {
        /*if (rvOrchestrion == null) {
            rvOrchestrionOrigin = new MutableLiveData<>();
            rvOrchestrion = new MutableLiveData<>();
            loadData();
        }*/
        return rvOrchestrion;
    }

    public LiveData<List<OrchestrionWithCategory>> getDataListOr() {
        /*if (rvOrchestrion == null) {
            rvOrchestrionOrigin = new MutableLiveData<>();
            rvOrchestrion = new MutableLiveData<>();
            loadData();
        }*/
        return rvOrchestrionOrigin;
    }

    public LiveData<List<Patch>> getDataPatches() {
        // todo get data from base

        /*patches = new MutableLiveData<>();
        *//*patches.setValue(new String[]{
                "endwalker",
                "shadowbringers",
                "stormblood",
                "heavensward",
                "reborn"
        });*//*
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new Runnable() {
            @Override
            public void run() {
                List<Patch> list = LogsDB.getByCheck();
                List<String> strings = new ArrayList<>();
                for (Patch patch : list) {
                    strings.add(patch.getName());
                }
                patches.postValue((String[]) strings.toArray());
            }
        });*/
        return patches;
    }

    public LiveData<boolean[]> getDataCheckedPatches() {
        // todo get data from base
        checkedPatches = new MutableLiveData<>();
        checkedPatches.setValue(new boolean[]{true, true, true, true, true});
        return checkedPatches;
    }

    private void loadData() {
        //todo get data from base
        rvOrchestrion = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<OrchestrionWithCategory> list = LogsDB.getAllOrchestrion();
                rvOrchestrion.postValue(list);
            }
        }).start();
       /* new OrchestrionAPIVolley(
                getApplication().getApplicationContext())
                .getAllOrchestrion(rvOrchestrion);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Orchestrion> list;
                do {
                    list = rvOrchestrion.getValue();
                } while (list == null);
                handler.sendEmptyMessage(0);
            }
        }).start();*/
    }

    public void searchItemByName(String textToSearch, List<String> patchNum) {
        List<OrchestrionWithCategory> list;
        if (lengNameItem < textToSearch.length()) {
        } else {
            searchItemByPatch(patchNum);
        }
        list = new ArrayList<>(rvOrchestrion.getValue());

        lengNameItem = (byte) textToSearch.length();
        if (lengNameItem != 0) {
            textToSearch = textToSearch.toLowerCase(Locale.ROOT);
            for (OrchestrionWithCategory item : rvOrchestrionOrigin.getValue()) {
                String i = item.getOrchestrion().getName().toLowerCase(Locale.ROOT);
                if (!i.contains(textToSearch)) {
                    list.remove(item);
                }
            }
        }

        rvOrchestrion.setValue(list);
        Log.e("searchItem", rvOrchestrion.getValue().size() + "");
        Log.e("searchItem", rvOrchestrionOrigin.getValue().size() + "");
    }

    public void searchItemByPatch(List<String> patchNum) {
        List<OrchestrionWithCategory> list = new ArrayList<>(rvOrchestrionOrigin.getValue());

        l1:for (OrchestrionWithCategory item : rvOrchestrionOrigin.getValue()) {
            String i = item.getOrchestrion().getPatch();
            l2:for (String patch : patchNum) {
                if (i.contains(patch)) {
                    continue l1;
                }
            }
            list.remove(item);
        }

        Log.e("searchItem", list.size() + "");
        Log.e("searchItem", rvOrchestrionOrigin.getValue().size() + "");
        rvOrchestrion.setValue(list);
    }

    public void checkPatches(int i, boolean b){
        checkedPatches.getValue()[i] = b;
        checkedPatches.setValue(checkedPatches.getValue());
    }
}