package burgerput.project.zenput.Services.utils.printDatafromDB;

import burgerput.project.zenput.domain.*;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import burgerput.project.zenput.repository.mgrList.MgrListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/*
* DB에서 데이터를 가져와 뿌려주는 서비스 계층
* */
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrintDataV2 implements PrintData{
    private final MachineRepository machineRepository;
    private final CustomMachineRepository customMachineRepository;
    private final FoodRepository foodRepository;
    private final CustomFoodRepository customFoodRepository;
    private final MgrListRepository mgrListRepository;

    @Override
    public ArrayList<Map> zenputMachine() {
        log.info("[PrintDataV2] ZenputMachine method start");

        ArrayList<Map> result = new ArrayList<>();

        //1. Machine Data List를 가져오기
        List<Machine> machineList = machineRepository.findAll();

        //2. Custommachine List 가져오기
        List<CustomMachine> customMachineList = customMachineRepository.findAll();

        //3. is checked 확인을 위해 for문으로 확인 n(logn)
        for (Machine machine : machineList) {
            //result에 값을 넣을 임시 맵 생성
            // Id, name, min ,max, ischecked값을 가진다.
            Map<String, String> machineMap = new LinkedHashMap<>();

            machineMap.put("id", String.valueOf(machine.getId()));
            machineMap.put("name", machine.getName());
            machineMap.put("min", String.valueOf(machine.getMin()));
            machineMap.put("max", String.valueOf(machine.getMax()));
            machineMap.put("isChecked", "false");

            //is 이중 for문의 시작 customMachine에 해당 machine객체가 있으면 true로 표시한다.
            //
            for (Iterator<CustomMachine> iterator = customMachineList.iterator(); iterator.hasNext();) {
                int idCustom = iterator.next().getId();
                int idMachine = machine.getId();

//                log.info("idCusomt's id ={}   |  idMachine 's id ={}", idCustom, idMachine);
                if (idMachine == idCustom) {
                    machineMap.put("isChecked", "true");
                    iterator.remove();
                }
            }
            result.add(machineMap);
        }

        return result;
    }

    @Override
    public ArrayList<Map> zenputFood() {
        log.info("[PrintDataV2] zenputFood method start");

        //1. 결과를 저장할 result생성하기
        ArrayList<Map> result = new ArrayList<>();

        //food list와 customFoodList를 가져온다.
        List<Food> foodList = foodRepository.findAll();

        List<CustomFood> customFoodList = customFoodRepository.findAll();

        for (Food food : foodList) {
            Map<String, String> foodMap = new LinkedHashMap<>();
            foodMap.put("id", String.valueOf(food.getId()));
            foodMap.put("name", food.getName());
            foodMap.put("min", String.valueOf(food.getMin()));
            foodMap.put("max", String.valueOf(food.getMax()));
            foodMap.put("isChecked", "false'");

            for (Iterator<CustomFood> iterator = customFoodList.iterator(); iterator.hasNext();) {
                int idCustom = iterator.next().getId();
                int idFood = food.getId();

                if (idFood == idCustom) {
                    foodMap.put("isChecked", "true");
                }
            }
            result.add(foodMap);
        }


        return result;
    }

    @Override
    public ArrayList<Map> customMachine() {
        log.info("[PrintDataV2] customMachine method start");
        //DTO @Id를 변경하고 새로 작성한 로직
        ArrayList<Map> result = new ArrayList<>();

        //Get the customId
        List<CustomMachine> customMachineList= customMachineRepository.findAll();

        for(CustomMachine customMachine : customMachineList){
            // @id를 기준으로 Macihne 찾기
            Machine foundMachine = machineRepository.findById(customMachine.getId()).get();

            //찾은 결과를 임시저장소에 저장하고 출력
            Map<String, String> customMachineMap = new LinkedHashMap<>();
            customMachineMap.put("id", Integer.toString(foundMachine.getId()));
            customMachineMap.put("name", foundMachine.getName());
            customMachineMap.put("min", String.valueOf(foundMachine.getMin()));
            customMachineMap.put("max", String.valueOf(foundMachine.getMax()));

            result.add(customMachineMap);
        }

        return result;
    }

    @Override
    public ArrayList<Map> customFood() {
        log.info("[PrintDataV2] customFood method start");
        //for Saving Result
        ArrayList<Map> result = new ArrayList<>();

        //Get the customId
        List<CustomFood> customFoodList = customFoodRepository.findAll();

        for(CustomFood customFood : customFoodList){
            Food foundFood = foodRepository.findById(customFood.getId()).get();

            Map<String, String> customFoodMap = new LinkedHashMap<>();
            customFoodMap.put("id", Integer.toString(foundFood.getId()));
            customFoodMap.put("name", foundFood.getName());
            customFoodMap.put("min", String.valueOf(foundFood.getMin()));
            customFoodMap.put("max", String.valueOf(foundFood.getMax()));

            result.add(customFoodMap);

        }

        return result;
    }

    @Override
    public ArrayList<Map> mgrList() {
        ArrayList<Map> result = new ArrayList<>();

        List<MgrList> mgrList = mgrListRepository.findAll();
        log.info("mgrList ???= {}", mgrList);
        for (MgrList list : mgrList) {
            Map<String, String> mgrListMap = new LinkedHashMap<>();
            mgrListMap.put("id", Integer.toString(list.getId()));
            mgrListMap.put("mgrname", list.getMgrName());

            result.add(mgrListMap);
        }

        return result;
    }


    @Override
    public ArrayList<Map> customCheatMachine() {
        log.info("[PrintDataV2] customCheatMachine method start");

        //1. 결과를 저장할 배열 생성
        ArrayList<Map> result = new ArrayList<>();

        // customMachineRepository에 List를 가져온다.
        List<CustomMachine> customMachineList = customMachineRepository.findAll();

        for(CustomMachine customCheatMachine : customMachineList){
            //machine Repository에서 조인해서 가져오기
            Machine machine = machineRepository.findById(customCheatMachine.getId()).get();

            Map<String, String> customCheatMachineMap = new LinkedHashMap<>();

            customCheatMachineMap.put("id", Integer.toString(machine.getId()));
            customCheatMachineMap.put("name", machine.getName());
            customCheatMachineMap.put("min", Integer.toString(customCheatMachine.getMin()));
            customCheatMachineMap.put("max", Integer.toString(customCheatMachine.getMax()));
            customCheatMachineMap.put("initMin", Integer.toString(machine.getMin()));
            customCheatMachineMap.put("initMax", Integer.toString(machine.getMax()));

            result.add(customCheatMachineMap);

        }

        return result;
    }

    @Override
    public ArrayList<Map> customCheatFood() {
        log.info("[PrintDataV2] customCheatFood method start");

        ArrayList<Map> result = new ArrayList<>();

        List<CustomFood> customFoodList = customFoodRepository.findAll();

        for(CustomFood customCheatFood : customFoodList){
            Food foundCheatFood = foodRepository.findById(customCheatFood.getId()).get();
            Map<String, String> customCheatFoodMap = new LinkedHashMap<>();

            customCheatFoodMap.put("id", Integer.toString(foundCheatFood.getId()));
            customCheatFoodMap.put("name", foundCheatFood.getName());
            customCheatFoodMap.put("min", Integer.toString(customCheatFood.getMin()));
            customCheatFoodMap.put("max", Integer.toString(customCheatFood.getMax()));
            customCheatFoodMap.put("initMin", Integer.toString(foundCheatFood.getMin()));
            customCheatFoodMap.put("initMax", Integer.toString(foundCheatFood.getMax()));

            result.add(customCheatFoodMap);

        }

        return result;
    }
}
