package ru.arizara.ff14log.ui.log.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
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
    private String nameItem = "";
    private byte choose = 0;
    private List<Patch> patchNum;

    public OrchestrionsListViewModel(@NonNull Application application) {
        super(application);
        repo = new OrchestrionRepo(application);
        patchRepo = new PatchRepo(application);

        patches = patchRepo.getAll();
        rvOrchestrionOrigin = repo.getAll();


        rvOrchestrion = new MutableLiveData<>();
        //rvOrchestrionOrigin = new MutableLiveData<>();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<OrchestrionWithCategory> list = LogsDB.getAllOrchestrion();
                rvOrchestrion.postValue(list);
                // rvOrchestrionOrigin.postValue(new ArrayList<>(list));
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

    public void searchItemByName(String textToSearch/*, List<Patch> patchNum*/) {
        List<OrchestrionWithCategory> list;
        if (lengNameItem < textToSearch.length()) {
        } else {
            if (this.patchNum == null) this.patchNum=new ArrayList<>(patches.getValue());
            searchItemByCheck2(new ArrayList<>(rvOrchestrionOrigin.getValue()));
            searchItemByPatch2(new ArrayList<>(rvOrchestrion.getValue()));
        }
        list = new ArrayList<>(rvOrchestrion.getValue());

        lengNameItem = (byte) textToSearch.length();
        nameItem = textToSearch.toLowerCase(Locale.ROOT);
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
        Log.e("searchItemByName", rvOrchestrion.getValue().size() + "");
        Log.e("searchItemByName", rvOrchestrionOrigin.getValue().size() + "");
    }



    private void searchItemByName2( List<OrchestrionWithCategory> list) {
        if (lengNameItem != 0) {
            //List<OrchestrionWithCategory> list = new ArrayList<>(rvOrchestrionOrigin.getValue());
            for (OrchestrionWithCategory item : rvOrchestrionOrigin.getValue()) {
                String i = item.getOrchestrion().getName().toLowerCase(Locale.ROOT);
                if (!i.contains(nameItem)) {
                    list.remove(item);
                }
            }
        }
        rvOrchestrion.setValue(list);
        Log.e("searchItemByName2", rvOrchestrion.getValue().size() + "");
        Log.e("searchItemByName2", rvOrchestrionOrigin.getValue().size() + "");

    }

    public void searchItemByPatch(List<Patch> patchNum) {

        searchItemByCheck2(new ArrayList<>(rvOrchestrionOrigin.getValue()));
        searchItemByName2(new ArrayList<>(rvOrchestrion.getValue()));

        List<OrchestrionWithCategory> list = new ArrayList<>(rvOrchestrion.getValue());
        this.patchNum = patchNum;


        l1:
        for (OrchestrionWithCategory item : rvOrchestrionOrigin.getValue()) {
            String i = item.getOrchestrion().getPatch();
            l2:
            for (Patch patch : patchNum) {
                if (i.contains(patch.getId() + ".")) {
                    continue l1;
                }
            }
            list.remove(item);
        }

        Log.e("searchItemByPatch1", list.size() + "");
        Log.e("searchItemByPatch1", rvOrchestrionOrigin.getValue().size() + "");
        rvOrchestrion.setValue(list);
    }

    private void searchItemByPatch2(List<OrchestrionWithCategory> list) {
        //List<OrchestrionWithCategory> list = new ArrayList<>(rvOrchestrion.getValue());

        l1:
        for (OrchestrionWithCategory item : rvOrchestrionOrigin.getValue()) {
            String i = item.getOrchestrion().getPatch();
            l2:
            for (Patch patch : patchNum) {
                if (i.contains(patch.getId() + ".")) {
                    continue l1;
                }
            }
            list.remove(item);
        }

        Log.e("searchItemByPatch2", list.size() + "");
        Log.e("searchItemByPatch2", rvOrchestrionOrigin.getValue().size() + "");
        rvOrchestrion.setValue(list);
    }

    public void searchItemByCheck(int choose) {
        List<OrchestrionWithCategory> list;
        if (this.choose != (byte) choose) {
            searchItemByName2(new ArrayList<>(rvOrchestrionOrigin.getValue()));
            searchItemByPatch2(new ArrayList<>(rvOrchestrion.getValue()));
        }
        list = new ArrayList<>(rvOrchestrion.getValue());

        this.choose = (byte) choose;
        switch (this.choose) {
            case 0:
                break;
            case 1:
                Iterator<OrchestrionWithCategory> iter1 = list.iterator();
                while (iter1.hasNext()) {
                    OrchestrionWithCategory orchestrion = iter1.next();
                    if (!orchestrion.getOrchestrion().isCheck()) {
                        iter1.remove();
                    }
                }
                break;
            case 2:
                Iterator<OrchestrionWithCategory> iter2 = list.iterator();
                while (iter2.hasNext()) {
                    OrchestrionWithCategory orchestrion = iter2.next();
                    if (orchestrion.getOrchestrion().isCheck()) {
                        iter2.remove();
                    }
                }
                break;
        }
        Log.e("searchItemByCheck", list.size() + "");
        Log.e("searchItemByCheck", rvOrchestrionOrigin.getValue().size() + "");
        rvOrchestrion.setValue(list);
    }

    private void searchItemByCheck2( List<OrchestrionWithCategory> list) {
        //List<OrchestrionWithCategory> list = new ArrayList<>(rvOrchestrionOrigin.getValue());
        switch (this.choose) {
            case 0:
                break;
            case 1:
                Iterator<OrchestrionWithCategory> iter1 = list.iterator();
                while (iter1.hasNext()) {
                    OrchestrionWithCategory orchestrion = iter1.next();
                    if (!orchestrion.getOrchestrion().isCheck()) {
                        iter1.remove();
                    }
                }
                break;
            case 2:
                Iterator<OrchestrionWithCategory> iter2 = list.iterator();
                while (iter2.hasNext()) {
                    OrchestrionWithCategory orchestrion = iter2.next();
                    if (orchestrion.getOrchestrion().isCheck()) {
                        iter2.remove();
                    }
                }
                break;
        }
        Log.e("searchItemByCheck2", list.size() + "");
        Log.e("searchItemByCheck2", rvOrchestrionOrigin.getValue().size() + "");
        rvOrchestrion.setValue(list);
    }

    public void checkPatches(int i, boolean b) {
        checkedPatches.getValue()[i] = b;
        checkedPatches.setValue(checkedPatches.getValue());
    }
}