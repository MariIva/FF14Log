package ru.arizara.ff14log.ui.log.rest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.mapper.OrchestrionMapper;

/**
 * Реализация запросов к серверу
 */
public class OrchestrionAPIVolley implements OrchestrionAPI{

    private final Context context;
    public static final String BASE_URL = "http://192.168.0.105:8081";
    //public static final String BASE_URL = "https://ffxivcollect.com/api";

    private final String urlAll = BASE_URL + "/orchestrion";


    public OrchestrionAPIVolley(Context context) {
        this.context = context;
    }

    @Override
    public void getAllOrchestrion(MutableLiveData<List<Orchestrion>> list) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                urlAll,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Orchestrion> orchestrionList = OrchestrionMapper.orchestrionFromJSONArray(response);
                        list.setValue(orchestrionList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse",error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

    @Override
    public void getAllOrchestrion(Handler handler, List<Orchestrion> list){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                urlAll,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        list.addAll(OrchestrionMapper.orchestrionFromJSONArray(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse",error.toString());
                        /*Snackbar.make(,
                                "R.string.text_label",
                                Snackbar.LENGTH_LONG)
                                .show();*/
                        Toast.makeText(context,
                                "context.getString(R.string.error_network_timeout)",
                                Toast.LENGTH_LONG).show();
                        handler.sendEmptyMessage(0);

                    }
                }
        );
        requestQueue.add(request);
    }
    @Override
    public void getOrchestrionByPatch(MutableLiveData<List<Orchestrion>> list) {

    }

}
