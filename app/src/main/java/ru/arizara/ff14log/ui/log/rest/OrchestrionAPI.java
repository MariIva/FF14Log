package ru.arizara.ff14log.ui.log.rest;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;

/**
 * Список запросов к серверу
 */
public interface OrchestrionAPI {



     void getAllOrchestrion(MutableLiveData<List<Orchestrion>> list);
     void getAllOrchestrion(Handler handler, List<Orchestrion> list);

     void getOrchestrionByPatch(MutableLiveData<List<Orchestrion>> list);

}
