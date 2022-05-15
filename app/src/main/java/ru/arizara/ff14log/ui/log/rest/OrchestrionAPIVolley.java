package ru.arizara.ff14log.ui.log.rest;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ru.arizara.ff14log.ui.log.entities.MyResponse;
import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.mapper.MyResponseMapper;
import ru.arizara.ff14log.ui.log.entities.mapper.OrchestrionMapper;

public class OrchestrionAPIVolley implements OrchestrionAPI{

    private final Context context;
    public static final String BASE_URL = "http://192.168.0.105:8081";
    //public static final String BASE_URL = "https://ffxivcollect.com/api";

    String url = BASE_URL + "/orchestrion";

    public OrchestrionAPIVolley(Context context) {
        this.context = context;
    }

    @Override
    public void getAllOrchestrion(MutableLiveData<List<Orchestrion>> list) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
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

}
