package burgerput.project.zenput.Services.utils.saveData;

import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;

import java.util.ArrayList;
import java.util.Map;

//DB에 데이터를 저장할 때 사용하는 서비스 계층
public interface SaveData {
    public Map<Integer, Machine> machinezenputdatasave(Map<Integer, Machine> machineInfo);

    public Map<Integer, Food> foodZenputDataSave(Map<Integer, Food> foodinfo);

    public void customMachineDataSave(ArrayList<Map> param);

    public void customFoodDataSave(ArrayList<Map> param);

    public void customCheatFoodDataSave(ArrayList<Map> param);

    public void customCheatMachineDataSave(ArrayList<Map> param);
}
