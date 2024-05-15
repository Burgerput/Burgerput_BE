package burgerput.project.zenput.Services.jsonObject;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*Json Data를 전달 받았을 때 사용한다.
* frontend단에서 zenput의 데이터를 전달받았을 때 String형식으로 받아오기 때문에 JSON 형식으로
* Parsing해서 사용한다.
* */
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
