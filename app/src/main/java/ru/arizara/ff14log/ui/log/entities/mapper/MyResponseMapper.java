package ru.arizara.ff14log.ui.log.entities.mapper;

import org.json.JSONException;
import org.json.JSONObject;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.MyResponse;

public class MyResponseMapper {

    public static MyResponse<Orchestrion> myResponseFromJSON(JSONObject jsonObject) {

        MyResponse<Orchestrion> myResponse = null;

        try {
            myResponse = new MyResponse<Orchestrion>(
                    null,
                    jsonObject.getInt("count"),
                    OrchestrionMapper.orchestrionFromJSONArray(jsonObject.getJSONArray("results"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myResponse;
    }
}
