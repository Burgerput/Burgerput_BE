package burgerput.project.zenput.Services.utils.loadData.alertCheck;

import burgerput.project.zenput.Services.utils.saveData.SaveData;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//bean으로 등록
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlertLoadingV2 implements AlertLoading2 {

    private final MachineRepository machineRepository;
    private final FoodRepository foodRepository;
    private final CustomMachineRepository customMachineRepository;
    private final CustomFoodRepository customFoodRepository;
    private final SaveData saveData;

    @Override
    public ArrayList<Map> editMachine(Map<Integer, Machine> zenputMachineData) {
        log.info("editMachine start AlertLoadingV2");
        //Db에서 값 가져오기
        List<Machine> machineDBDatas = machineRepository.findAll();
//        log.info("machineDbData = {}", machineDBDatas);

        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();
        for (Machine machineDBData : machineDBDatas) {
            dbIdStore.add(machineDBData.getId());
        }

        ArrayList editResult = new ArrayList();

        //새롭게 추가된 Edit객체 저장소
        if (!machineDBDatas.isEmpty()) {
            //DB 데이터가 null이 아닌 경우
            //repeat pre's id list
            for (int key : dbIdStore) {
                //find db value
                Machine dbMachine = machineRepository.findById(key).get();
                Machine zenputMachine = zenputMachineData.get(key);

//                log.info("DbMachine ={}", dbMachine);
//                log.info("zenputMachie = {}", zenputMachine);
                //store for edited data
                Map<String, String> tempMap = new LinkedHashMap<>();
                //name check start
//                log.info("key value  = {}", key);
                if (!(zenputMachine == null)) {
                    if (!dbMachine.getName().equals(zenputMachine.getName())) {
                        //if the name value was different?
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbMachine.getName());
                        objects.add(zenputMachine.getName());


                        tempMap.put("id", Integer.toString(zenputMachine.getId()));

                        tempMap.put("name", objects.toString());
                        tempMap.put("min", Integer.toString(zenputMachine.getMin()));
                        tempMap.put("max", Integer.toString(zenputMachine.getMax()));
                        tempMap.put("code", "edit");
                        // NAME : [BEFORE, AFTER]
                    }

                    // min check Start
                    if (!((dbMachine.getMin()) == zenputMachine.getMin())) {
                        log.info("min test");
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbMachine.getMin());
                        objects.add(zenputMachine.getMin());
                        // min : [BEFORE, AFTER]

                        tempMap.put("id", Integer.toString(zenputMachine.getId()));

                        if (!tempMap.containsKey("name")) {
                            tempMap.put("name", zenputMachine.getName());
                        }

                        tempMap.put("min", objects.toString());

                        if (!tempMap.containsKey("max")) {
                            tempMap.put("max", Integer.toString(zenputMachine.getMax()));
                        }

                        tempMap.put("code", "edit");

                    }
                    // max check Start
                    if (!(dbMachine.getMax() == zenputMachine.getMax())) {
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbMachine.getMax());
                        objects.add(zenputMachine.getMax());
                        // max : [BEFORE, AFTER]


                        tempMap.put("id", Integer.toString(zenputMachine.getId()));

                        if (!tempMap.containsKey("name")) {
                            tempMap.put("name", zenputMachine.getName());
                        }

                        if (!tempMap.containsKey("min")) {
                            tempMap.put("min", objects.toString());
                        }

                        tempMap.put("max", objects.toString());

                        tempMap.put("code", "edit");
                    }

                    if (!tempMap.isEmpty()) {

                        editResult.add(tempMap);

                    }
                }
//                addResult.add(tempMap);
            }
        } else {
            //Db data is null then upload all
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("code", "all");

            editResult.add(tempMap);

        }
        return editResult;
    }

    @Override
    public ArrayList<Map> editFood(Map<Integer, Food> zenputFoodData) {

        //Db에서 값 가져오기
        List<Food> foodDBDatas = foodRepository.findAll();
//        log.info("machineDbData = {}", machineDBDatas);

        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();
        for (Food foodDBData : foodDBDatas) {
            dbIdStore.add(foodDBData.getId());
        }

        ArrayList editResult = new ArrayList();
        //새롭게 추가된 Edit객체 저장소
        if (!foodDBDatas.isEmpty()) {
            //DB 데이터가 null이 아닌 경우
            //repeat pre's id list
            for (int key : dbIdStore) {
                //find db value
                Food dbFood = foodRepository.findById(key).get();
                Food zenputFood = zenputFoodData.get(key);

//                log.info("DbMachine ={}", dbMachine);
//                log.info("zenputMachie = {}", zenputMachine);
                //store for edited data
                Map<String, String> tempMap = new LinkedHashMap<>();
                //name check start
//                log.info("key value  = {}", key);
                if (!(zenputFood == null)) {
                    if (!dbFood.getName().equals(zenputFood.getName())) {
                        //if the name value was different?
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbFood.getName());
                        objects.add(zenputFood.getName());

                        tempMap.put("id", Integer.toString(zenputFood.getId()));
                        tempMap.put("name", objects.toString());
                        tempMap.put("min", Integer.toString(zenputFood.getMin()));
                        tempMap.put("max", Integer.toString(zenputFood.getMax()));
                        tempMap.put("code", "edit");
                        // NAME : [BEFORE, AFTER]
                    }

                    // min check Start
                    if (!((dbFood.getMin()) == zenputFood.getMin())) {
                        log.info("min test");
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbFood.getMin());
                        objects.add(zenputFood.getMin());
                        // min : [BEFORE, AFTER]

                        tempMap.put("id", Integer.toString(zenputFood.getId()));

                        if (!tempMap.containsKey("name")) {
                            tempMap.put("name", zenputFood.getName());
                        }

                        tempMap.put("min", objects.toString());

                        if (!tempMap.containsKey("max")) {
                            tempMap.put("max", Integer.toString(zenputFood.getMax()));
                        }

                        tempMap.put("code", "edit");

                    }

                    // max check Start
                    if (!(dbFood.getMax() == zenputFood.getMax())) {
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(dbFood.getMax());
                        objects.add(zenputFood.getMax());
                        // max : [BEFORE, AFTER]


                        tempMap.put("id", Integer.toString(zenputFood.getId()));

                        if (!tempMap.containsKey("name")) {
                            tempMap.put("name", zenputFood.getName());
                        }

                        if (!tempMap.containsKey("min")) {
                            tempMap.put("min", objects.toString());
                        }

                        tempMap.put("max", objects.toString());

                        tempMap.put("code", "edit");
                    }

                    if (!tempMap.isEmpty()) {
                        editResult.add(tempMap);
                    }
                }
//                addResult.add(tempMap);
            }
        } else {
            //Db data is null then upload all
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("code", "all");
            editResult.add(tempMap);

        }
        return editResult;
    }

    @Override
    public ArrayList addMachine(Map<Integer, Machine> zenputMachineData) {
        //Db에서 값 가져오기
        List<Machine> machineDBDatas = machineRepository.findAll();

        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();
        for (Machine machineDBData : machineDBDatas) {
            dbIdStore.add(machineDBData.getId());
        }

        ArrayList addResult = new ArrayList();
        //새롭게 추가된 ADD 객체 저장소
        if (!machineDBDatas.isEmpty()) {
            //DB 데이터가 null이 아닌 경우
            for (Integer zenputMachineDatum : zenputMachineData.keySet()) {
                if (dbIdStore.contains(zenputMachineDatum)) {
                    //DB에 해당 id 값을 갖고 있는 경우
                } else {
                    Map<String, String> tempMap = new HashMap<>();
                    tempAddMachineSetup(zenputMachineData, zenputMachineDatum, tempMap);

                    addResult.add(tempMap);
                    //DB에서 해당 id 값을 갖고 있지 않은 경우
//                    log.info("has diff value ={}", zenputMachineData.get(zenputMachineDatum));
                }
            }

        } else {
            //Db data is null then upload all
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("code", "all");
            addResult.add(tempMap);
        }

        return addResult;
    }

    @Override
    public ArrayList<Map> addFood(Map<Integer, Food> zenputFoodData) {
        //Db에서 값 가져오기
        List<Food> foodDBDatas = foodRepository.findAll();

        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();
        for (Food foodDBData : foodDBDatas) {
            dbIdStore.add(foodDBData.getId());
        }
        ArrayList addResult = new ArrayList();

        if (!foodDBDatas.isEmpty()) {

            //새롭게 추가된 ADD 객체 저장소
            //DB 데이터가 null이 아닌 경우
            for (Integer zenputFoodDatum : zenputFoodData.keySet()) {
                if (!dbIdStore.contains(zenputFoodDatum)) {//DB에서 해당 id 값을 갖고 있지 않은 경우

                    Map<String, String> tempMap = new HashMap<>();
                    tempAddFoodSetup(zenputFoodData, zenputFoodDatum, tempMap);

                    addResult.add(tempMap);
                }
            }
        } else {
            //Db data is null then upload all
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("code", "all");
            addResult.add(tempMap);
        }
        return addResult;
    }

    @Override
    public ArrayList<Map> delMachine(Map<Integer, Machine> zenputMachineData) {
        //Db에서 값 가져오기
        List<Machine> machineDBDatas = machineRepository.findAll();

        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();

        for (Machine machineDBData : machineDBDatas) {
            dbIdStore.add(machineDBData.getId());
        }

        ArrayList delResult = new ArrayList();


        if (!machineDBDatas.isEmpty()) {
            //DB 데이터가 null이 아닌 경우
            //repeat pre's id list
            for (int key : dbIdStore) {
                Map<String, String> tempMap = new LinkedHashMap<>();

                //del check start
                if (zenputMachineData.get(key) == null ? true : false) {
//                    log.info("key value = {}", key);
                    //true -> it's deleted (zenputPage delete the entity)
                    Machine deletedMachine = machineRepository.findById(key).get();
                    tempMap.put("id", Integer.toString(deletedMachine.getId()));
                    tempMap.put("name", deletedMachine.getName());
                    tempMap.put("min", Integer.toString(deletedMachine.getMin()));
                    tempMap.put("max", Integer.toString(deletedMachine.getMax()));
                    tempMap.put("code", "del");


                    delResult.add(tempMap);
                } else {

                }
            }
        }
        return delResult;
    }

    @Override
    public ArrayList<Map> delFood(Map<Integer, Food> zenputFoodData) {
        //Db에서 값 가져오기
        List<Food> foodDBDatas = foodRepository.findAll();


        //Db's id store - 반복 중첩을 줄이기 위해 id를 따로 저장함
        ArrayList<Integer> dbIdStore = new ArrayList<>();
        for (Food foodDBData : foodDBDatas) {
            dbIdStore.add(foodDBData.getId());
        }

        ArrayList delResult = new ArrayList();

        if (!foodDBDatas.isEmpty()) {
            //DB 데이터가 null이 아닌 경우
            //repeat pre's id list
            for (int key : dbIdStore) {
                Map<String, String> tempMap = new LinkedHashMap<>();

                //del check start
                if (zenputFoodData.get(key) == null ? true : false) {
//                    log.info("key value = {}", key);
                    //true -> it's deleted (zenputPage delete the entity)
                    Food delFood = foodRepository.findById(key).get();
                    tempMap.put("id", Integer.toString(delFood.getId()));
                    tempMap.put("name", delFood.getName());
                    tempMap.put("min", Integer.toString(delFood.getMin()));
                    tempMap.put("max", Integer.toString(delFood.getMax()));
                    tempMap.put("code", "del");

                    delResult.add(tempMap);
                } else {

                }

            }
        }
        return delResult;
    }

    private static void tempAddFoodSetup(Map<Integer, Food> zenputFoodeData, Integer
            keyData, Map<String, String> tempMap) {
        tempMap.put("id", Integer.toString(zenputFoodeData.get(keyData).getId()));
        tempMap.put("name", zenputFoodeData.get(keyData).getName());
        tempMap.put("min", Integer.toString(zenputFoodeData.get(keyData).getMin()));
        tempMap.put("max", Integer.toString(zenputFoodeData.get(keyData).getMax()));
        tempMap.put("code", "add");
    }


    @Override
    public void MachineJsonMakerandDBSet(Map<Integer, Machine> info, JSONArray jsonArray) {

        //Machine의 add, del, edti의 정보를  추출한다.
        ArrayList<Map> add = addMachine(info);
        ArrayList<Map> edit = editMachine(info);
        ArrayList<Map> del = delMachine(info);


        //추출한 데이터를 alertInfo를 이용해 통합한다.
        ArrayList<Map<String, Object>> maps = alertInfo(add, edit, del);

        //뽑혀진 데이터List를 전달받은 JSONArray에 저장한다.
        for (Map<String, Object> map : maps) {
            jsonArray.put(map.toString());
        }
        //Custom DB에 del 데이터 적용(del에 있는 값을 똑같이 지운다.)
        for (Map<String, String> map : del) {
            customMachineRepository.deleteById(Integer.parseInt(map.get("id")));
            log.info("deleted Machine data ={}", map);
        }
        //로딩의 값을 Machine DB에 적용 (DB 컨텐츠를 모두 지웠다가 저장한다.)
        saveData.machinezenputdatasave(info);
    }

    @Override
    public void FoodJsonMakerandDBSet(Map<Integer, Food> info, JSONArray jsonArray) {
        //Food의 add, del, edti의 정보를  추출한다.
        ArrayList<Map> add = addFood(info);
        ArrayList<Map> edit = editFood(info);
        ArrayList<Map> del = delFood(info);


        //추출한 데이터를 alertInfo를 이용해 통합한다.
        ArrayList<Map<String, Object>> maps = alertInfo(add, edit, del);

        //뽑혀진 데이터List를 전달받은 JSONArray에 저장한다.
        for (Map<String, Object> map : maps) {
            jsonArray.put(map);
        }

        //Custom DB에 del 데이터 적용(del에 있는 값을 똑같이 지운다.)
        for (Map<String, String> map : del) {
            customFoodRepository.deleteById(Integer.parseInt(map.get("id")));
            log.info("deleted Food data ={}", map);
        }
        //전체 변환 값을 DB에 추가(DB 컨텐츠를 모두 지웠다가 저장한다.)
        saveData.foodZenputDataSave(info);
    }

    @Override
    public void jsonMaker(JSONArray jsonArray, String path) {
        Date now = Calendar.getInstance().getTime();
        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:50:24 KST 2022
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);

        // 포맷팅 현재 날짜/시간 출력
        log.info(formatedNow.toString());

        if(!jsonArray.isEmpty()){
            try {
                FileWriter file = new FileWriter(path + formatedNow + ".json");
                file.write(jsonArray.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //API 형태로 변환해주는 메소드 기존 ProcessAlert기능을 통합했다.
    //현재 ProcessAlert는 사용하지 않아 제거되었다.
    @Override
    public ArrayList<Map<String, Object>> alertInfo
    (ArrayList<Map> addMap, ArrayList<Map> editMap, ArrayList<Map> delMap) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        log.info("addMap Result= {}",addMap.toString());

        //addMap parsing Start
        if (!addMap.isEmpty() && !addMap.get(0).containsValue("all"))  {

            for (Map<String, String> map : addMap) {
                Map<String, Object> tempMap = new LinkedHashMap<>();
                tempMap.put("id", map.get("id").toString());
                tempMap.put("name", map.get("name"));
                tempMap.put("min", map.get("min").toString());
                tempMap.put("max", map.get("max").toString());
                tempMap.put("code", "add");

                result.add(tempMap);
            }

        }

        //editMap parsing start
        if (!editMap.isEmpty() && !editMap.get(0).containsValue("all")) {
            for (Map<String, String> map : editMap) {

                Map<String, Object> tempMap = new LinkedHashMap<>();

                String id = map.get("id").toString();
                String name = map.get("name");
                String min = map.get("min");
                String max = map.get("max");

                tempMap.put("id", id);
                tempMap.put("name", name);
                tempMap.put("code", "edit");

                // map save arrayList
                List<Map<String, List<String>>> diffList = new ArrayList<>();

                //check the name
                if (name.contains("[")) { //arrayList so it has diff value
                    String pid = "name";
                    //기존의 이름을 넣어주는 작업
                    String[] temp = name.substring(1, name.length() - 1).split(",");
                    String beforename = temp[0];
                    tempMap.put("name", beforename);

                    diffList.add(makeArray(name, pid));
                }

                if (min.contains("[")) {
                    String pid = "min";
                    diffList.add(makeArray(min, pid));
                }

                if (max.contains("[")) {
                    String pid = "max";
                    diffList.add(makeArray(max, pid));
                }


                if (!diffList.isEmpty()) {
                    tempMap.put("diff", diffList);
                }

                result.add(tempMap);

            }
        }

        if (!delMap.isEmpty()) {
            // del parsing start
            for (Map<String, String> map : delMap) {
                Map<String, Object> tempMap = new LinkedHashMap<>();
                tempMap.put("id", map.get("id").toString());
                tempMap.put("name", map.get("name"));
                tempMap.put("min", map.get("min").toString());
                tempMap.put("max", map.get("max").toString());
                tempMap.put("code", "del");

                result.add(tempMap);
            }
        }

        return result;
    }

    private static Map<String, List<String>> makeArray(String diffString, String pid) {
        String[] temp = diffString.substring(1, diffString.length() - 1).split(",");
        //temp 0 : before값
        //temp 1 : after값
        //온도를 새롭게 저장할 ArrayList
        List<String> temperList = new ArrayList<>();

        temperList.add(temp[0].trim());
        temperList.add(temp[1].trim());

        // { key : [ temp1 ,temp2  ]} 의 구조에서 가장 바깥 Map
        Map<String, List<String>> tempMap = new LinkedHashMap<>();

        tempMap.put(pid, temperList);

//        log.info("tempMap ={} ", tempMap.toString());

        return tempMap;
    }

    private static void tempAddMachineSetup(Map<Integer, Machine> zenputMachineData, Integer
            keyData, Map<String, String> tempMap) {
        tempMap.put("id", Integer.toString(zenputMachineData.get(keyData).getId()));
        tempMap.put("name", zenputMachineData.get(keyData).getName());
        tempMap.put("min", Integer.toString(zenputMachineData.get(keyData).getMin()));
        tempMap.put("max", Integer.toString(zenputMachineData.get(keyData).getMax()));
        tempMap.put("code", "add");
    }

}
