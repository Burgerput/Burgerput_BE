package burgerput.project.zenput.Services.utils.loadData.alertCheck;

import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import com.google.gson.JsonArray;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public interface AlertLoading2 {
    public ArrayList<Map> editMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> editFood(Map<Integer, Food> zenputFoodData);

    public ArrayList<Map> addMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> addFood(Map<Integer, Food> zenputFoodData);

    public ArrayList<Map> delMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> delFood(Map<Integer, Food> zenputFoodData);

    //Machine getInfo data를 받아서 jsonArray로 반환
    public void MachineJsonMakerandDBSet(Map<Integer, Machine> info, JSONArray jsonArray);

    //Food getInfo data를 받아서 jsonArray로 반환
    public void FoodJsonMakerandDBSet(Map<Integer, Food> info, JSONArray jsonArray);

    //loading을 통해 추출한 데이터를 Json파일로 만드는 메소드
    public void jsonMaker(JSONArray jsonArray, String path);

    //기존 ProcessAlert로 분리되어 있던 기능을 통합
    //add, del, edit을 추출해서 API형태로 변환해주는 역할
    public ArrayList<Map<String, Object>> alertInfo(ArrayList<Map> addMap, ArrayList<Map> delMap, ArrayList<Map> editMap);


}
