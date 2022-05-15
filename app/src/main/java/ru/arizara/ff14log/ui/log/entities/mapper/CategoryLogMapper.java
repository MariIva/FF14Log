package ru.arizara.ff14log.ui.log.entities.mapper;

import org.json.JSONException;
import org.json.JSONObject;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;
import ru.arizara.ff14log.ui.log.entities.subEntities.CategoryLog;

public class CategoryLogMapper {
    public static CategoryLog CategoryLogFromJSON(JSONObject jsonObject) {

        CategoryLog log = null;

        try {
            log = new CategoryLog(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return log;
    }
}
