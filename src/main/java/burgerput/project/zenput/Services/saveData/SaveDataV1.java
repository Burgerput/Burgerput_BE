package burgerput.project.zenput.Services.saveData;

import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.CustomFood;
import burgerput.project.zenput.domain.CustomMachine;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class SaveDataV1 implements SaveData {
    private final MachineRepository machineRepository;
    private final FoodRepository foodRepository;
    private final CustomFoodRepository customFoodRepository;
    private final CustomMachineRepository customMachineRepository;

    //Zenput Page Food Machine Loading and
    //save data to Database
    @Override
    public Map<Integer, Machine> machinezenputdatasave(Map<Integer, Machine> machineInfo) {
        //임시로 다지우고 시작 -> 변경해야하는 로직
        machineRepository.deleteAllMIne();

        for (Integer key : machineInfo.keySet()) {
            Machine machine = machineInfo.get(key);

            machineRepository.save(machine);
        }


        return machineInfo;
    }

    @Override
    public Map<Integer, Food> foodZenputDataSave( Map<Integer, Food> foodinfo) {
        //임시로 다지우고 시작 -> 변경해야하는 로직
        foodRepository.deleteAllMIne();

        for (Integer key : foodinfo.keySet()) {
            Food food = foodinfo.get(key);

            foodRepository.save(food);
        }
        return foodinfo;
    }

    @Override
    public void customMachineDataSave(ArrayList<Map> param) {
        //table의 내용을 전부 지웠다가 다시 저장 -> 달라진 내용만 업데이트 하는 방향 필요
        customMachineRepository.deleteAllMine();
        // num 변수 재 설정

        for (Map<String, String> map : param) {

            log.info("customMachine Data Save () ={}", map);
            CustomMachine selectedMachine = new CustomMachine();
            selectedMachine.setId(Integer.parseInt(map.get("id")));
            selectedMachine.setMin(Integer.parseInt(map.get("min")));
            selectedMachine.setMax(Integer.parseInt(map.get("max")));
            //log.info("what is id={},", map.get("id"));
            customMachineRepository.save(selectedMachine);

        }
    }

    @Override
    public void customFoodDataSave(ArrayList<Map> param) {
        //임시로 다지우고 시작 -> 변경해야하는 로직
        // 해당 아이디를 찾아서 min , max 값 업로드하는 Modifying Query 제작
        customFoodRepository.deleteAllMine();

        for (Map<String, String> map : param) {
            CustomFood selectedFood = new CustomFood();
            selectedFood.setId(Integer.parseInt(map.get("id")));
            selectedFood.setMin(Integer.parseInt(map.get("min")));
            selectedFood.setMax(Integer.parseInt(map.get("max")));
            customFoodRepository.save(selectedFood);
        }
    }

    @Override
    public void customCheatFoodDataSave(ArrayList<Map> param) {

        for (Map<String,String> map : param) {
            log.info("customCheatFoodDaetaSvae() = {}", map);
            customFoodRepository.updateMy(Integer.parseInt(String.valueOf(map.get("id"))),
                    Integer.parseInt(String.valueOf(map.get("min"))),
                    Integer.parseInt(String.valueOf(map.get("max"))));
        }

        //새로운 인자를 만들어서 생성하는 코드 나는 수정을 원하는 건데 이러면 문제가 발생한다.
//        for (Map<String, String> map : param) {
//            CustomFood cheatFood = new CustomFood();
//            cheatFood.setId(Integer.parseInt(map.get("id")));
//            cheatFood.setMin(Integer.parseInt(map.get("min")));
//            cheatFood.setMax(Integer.parseInt(map.get("max")));
//
//            customFoodRepository.save(cheatFood);
//        }
    }

    @Override
    public void customCheatMachineDataSave(ArrayList<Map> param) {

        for (Map<String,String> map : param) {

            log.info("CustomCheatMachineData Svae() = {}", map);

            customMachineRepository.updateMy(Integer.parseInt(String.valueOf(map.get("id"))),
                    Integer.parseInt(String.valueOf(map.get("min"))),
                    Integer.parseInt(String.valueOf(map.get("max"))));
        }

    }


}
