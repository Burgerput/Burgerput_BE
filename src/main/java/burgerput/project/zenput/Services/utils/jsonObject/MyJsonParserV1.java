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

    @Override
    public List<Map<String, Object>> stringToJSONArray(String param) {
        //param의 값을 JAONArray로 불러온다.
        JSONArray jsonArr = new JSONArray(param);

        //결과를 담을 resultArr를 생성
        List<Map<String, Object>> resultArr = new ArrayList<>();
        //Object를 Map으로 변경하기 위해 선언
        ObjectMapper objectMapper = new ObjectMapper();

        //param을 통해서 받은 JSONArray를 한 차례씩 돌아준다.
        for (int i = 0; i < jsonArr.length(); i++) {
            Map<String, Object> map = null;

            JSONObject tempMap = (JSONObject) jsonArr.get(i);
            try {
                map = new ObjectMapper().readValue(tempMap.toString(), Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //JSONArray에서 꺼낸 객체를 뽑아서 map으로 변경해준다.
            resultArr.add(map);
        }
        return resultArr;
    }
}
