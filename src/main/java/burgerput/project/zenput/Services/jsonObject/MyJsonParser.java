package burgerput.project.zenput.Services.jsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MyJsonParser {
    public ArrayList<Map> jsonStringToArrayList(String param);

    //added 2024/05/21
    public List<Map<String,Object>> stringToJSONArray(String param);
}
