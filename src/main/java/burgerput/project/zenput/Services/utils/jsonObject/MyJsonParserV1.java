package burgerput.project.zenput.Services.utils.jsonObject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    @Override
    public List<Map<String, Object>> stringToJSONArray(String param) {
        JSONArray jsonArr = new JSONArray(param);

        List<Map<String, Object>> resultArr = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < jsonArr.length(); i++) {
            Map<String, Object> map = null;

            JSONObject tempMap = (JSONObject) jsonArr.get(i);
            try {
                map = new ObjectMapper().readValue(tempMap.toString(), Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            resultArr.add(map);
        }
        return resultArr;
    }
}
