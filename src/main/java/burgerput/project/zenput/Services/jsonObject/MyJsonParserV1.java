package burgerput.project.zenput.Services.jsonObject;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class MyJsonParserV1 implements MyJsonParser {
    @Override
    public ArrayList<Map> jsonStringToArrayList(String param) {

        JSONObject paramO = new JSONObject(param);
        JSONArray customMap;

        try {
             customMap = (JSONArray) paramO.get("customFood");
        } catch (JSONException e) {
            customMap = (JSONArray) paramO.get("customMachine");
        }


        ArrayList<Map> result = new ArrayList<>();

        for (int i = 0; i < customMap.length(); i++) {

            JSONObject o = (JSONObject) customMap.get(i);

            Map<String, String> tempMap = new LinkedHashMap<>();
            tempMap.put("id", o.getString("id"));
            tempMap.put("name", o.getString("name"));
            tempMap.put("temp", Integer.toString(o.getInt("temp")));

            result.add(tempMap);
        }

        return result;

    }
}
