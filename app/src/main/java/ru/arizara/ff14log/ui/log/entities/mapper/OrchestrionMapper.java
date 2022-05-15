package ru.arizara.ff14log.ui.log.entities.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;

public class OrchestrionMapper {
    public static Orchestrion orchestrionFromJSON(JSONObject jsonObject) {

        Orchestrion orchestrion = null;

        try {
            orchestrion = new Orchestrion(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("patch"),
                    /*jsonObject.getInt("item_id"),
                    jsonObject.getString("owned"),
                    jsonObject.getString("number"),
                    jsonObject.getString("icon"),*/
                    CategoryLogMapper.CategoryLogFromJSON(jsonObject.getJSONObject("category"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orchestrion;
    }

    public static List<Orchestrion> orchestrionFromJSONArray(JSONArray array) {
        List<Orchestrion> list = new ArrayList<>();
        for (int i=0; i<array.length(); i++) {
            Orchestrion orchestrion = null;
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                orchestrion = OrchestrionMapper.orchestrionFromJSON(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (orchestrion != null) list.add(orchestrion);
        }
        return list;
    }


}
