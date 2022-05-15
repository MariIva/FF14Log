package ru.arizara.ff14log.ui.log.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.rest.OrchestrionAPIVolley;

public class OrchestrionsListViewModel extends AndroidViewModel {


    private MutableLiveData<List<Orchestrion>> rvOrchestrion;

    public OrchestrionsListViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Orchestrion>> getData() {
        if (rvOrchestrion == null) {
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

    }

}