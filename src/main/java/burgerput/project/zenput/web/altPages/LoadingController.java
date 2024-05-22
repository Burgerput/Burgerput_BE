package burgerput.project.zenput.web.altPages;

import burgerput.project.zenput.Const;
import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.loadData.alertCheck.AlertLoading2;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/*
* /loading
* 1. getInfo를 통해서 data를 가져온다.
* 2. alertLoading을 통해 Macihne/Food의 add,del,edit 데이터를 갖는 JsonArray를 만든다.
* 3. JsonArray의 값을 /burgerput/loading의 경로에 jsonfile형식으로 저장한다.
*
* /loading/result
* 1. /burgerput/loading/result에 getMapping인 경우 file의 정보를 뿌려서 보여준다.
*
* */
@Controller
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="loading", method={RequestMethod.GET, RequestMethod.POST})
public class LoadingController {
    private final AlertLoading2 alertLoading;
    private final MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;
    private final CustomMachineRepository customMachineRepository;
    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final CustomFoodRepository customFoodRepository;
    private final MyJsonParser myJsonParser;

    @GetMapping
    public Map<String,String> loadingV2(){

/*        //서버에서 아침 8:35분에 요청해서 가져오는 값

        //로딩의 결과를 저장할 JSONArray 객체*/
        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");
        JSONArray jsonArray = new JSONArray();

        try{
            //getInfo로 데이터 가져오기
            Map<Integer, Machine> infoMachine = machineLoadingAndEnterZenput.getInfo();

            //반환값을 jsonArray를 인자로 보내 저장
            alertLoading.MachineJsonMakerandDBSet(infoMachine, jsonArray);

        }catch(Exception e){
            log.info("Machine getInfo logic False");
            log.info(e.toString());
            resultMap.put("result", "false");

            return resultMap;
        }

        try{
            //getinfo로 food데이터 가져오기 시작
            Map<Integer, Food> infoFood = foodLoadingAndEnterZenput.getInfo();

            //결과값을 인자로 보낸 jsonArray에 저장
            alertLoading.FoodJsonMakerandDBSet(infoFood,jsonArray);

        }catch(Exception e){
            resultMap.put("result", "false");
            log.info("Food getInfo logic false");
            log.info(e.toString());

            return resultMap;
        }

        //최종 로딩의 결과 파일에 저장되는 값의 결과
        log.info(jsonArray.toString());

        //로딩의 성공 여부
        return resultMap;

    }
    //ZenputPage loading 후에 달라진 값들을 Alert로 넘긴다.
//    @GetMapping
//    public Map<String,String> loading(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        log.info("Loading Logic Start  ={}", LocalDateTime.now());
//        //Start Loading Logic
//        //loading zenput Page's Data first
//        Map<String, String> resultMap = new LinkedHashMap<>();
//        resultMap.put("result", "true");
//
//        try{
//            log.info("MACHINE DATA GET STASRT");
//            Map<Integer, Machine> machineInfo = machineLoadingAndEnterZenput.getInfo();
//            log.info("loaded Machine Map info : {}", machineInfo);
//
//            if (machineInfo.size() == 1) {
//                //page loading fail case
//                //false
//                log.info("Machine Enter Page = false");
//                resultMap.put("result", "false");
//
//                return resultMap;
//            }
//
//            ArrayList<Map> addMap = alertLoading.addMachine(machineInfo);
//            ArrayList<Map> delMap = alertLoading.delMachine(machineInfo);
//            ArrayList<Map> editMap = alertLoading.editMachine(machineInfo);
//
//            log.info("Machine DB set-up ");
//            alertMachineInfoToDb(delMap);
//            saveData.machinezenputdatasave(machineInfo);
//
//        }catch(Exception e){
//            log.info("Machine getInfo logic False");
//            log.info(e.toString());
//            resultMap.put("result", "false");
//
//            return resultMap;
//        }
//
//        try {
//            log.info("FOOD DATA GET STASRT");
//            Map<Integer, Food> foodInfo = foodLoadingAndEnterZenput.getInfo();
//            log.info("Loaded Food Map info : {}", foodInfo);
//
//            if (foodInfo.size() ==1) {
//                //false
//                log.info("Food Enter Page = false");
//                resultMap.put("result", "false");
//
//                return resultMap;
//            }
//
//            ArrayList<Map> addFoodMap = alertLoading.addFood(foodInfo);
//            ArrayList<Map> delFoodMap = alertLoading.delFood(foodInfo);
//            ArrayList<Map> editFoodMap = alertLoading.editFood(foodInfo);
//
//            log.info("Food DB set-up ");
//
//            alertFoodInfoToDb(delFoodMap);
//            saveData.foodZenputDataSave(foodInfo);
//
//        } catch (Exception e) {
//            resultMap.put("result", "false");
//            log.info("Food getInfo logic false");
//            log.info(e.toString());
//
//            return resultMap;
//        }
//
//        log.info("return value ={}", resultMap.get("result"));
//        return resultMap;
//    }
    //@GetMapping("/test")

    @GetMapping("/result")
    @ResponseBody
    public List<Map<String,Object>> readResult(){
        // JSON 파일 읽기
        String path = Const.JSONPATH;
//        String path = "C:/Users/bbubb/Desktop/Burgerput/jsonFiles/";
        Date now = Calendar.getInstance().getTime();
        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:50:24 KST 2022
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);

        String finalPath = path + formatedNow+ ".json";
        String result = "";

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            result = new String(Files.readAllBytes(Paths.get(finalPath)));

        } catch (IOException e) {
            //에러인 경우
            log.info("No file exist");
            return list;
        }
        list = myJsonParser.stringToJSONArray(result);

        log.info("result Arr  = {}", list.toString());
        return list;
    }


    @ResponseBody
    @GetMapping("/test")
    public Map<String,String> loadingTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Map<Integer, Machine> machineInfo = machineLoadingAndEnterZenput.getInfo();
        log.info("LoadingTest Logic Start(only Food)  ={}", LocalDateTime.now());

        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");
//
//        Map<Integer, Food> foodInfo = foodLoadingAndEnterZenput.getInfo();
//        log.info("Loaded Food Map info : {}", foodInfo);
//
//        if ( foodInfo.size() ==1) {
//            //false
//            log.info("FALSE RESULT RETURN = false");
//            resultMap.put("result", "false");
//
//            return resultMap;
//        }
//
        return resultMap;
    }

    private void alertFoodInfoToDb (ArrayList < Map > delMap) {

        for (Map<String, String> map : delMap) {
            customFoodRepository.deleteBymineId(map.get("id"));
            log.info("deleted Food data ={}", map);
        }
    }
    private void alertMachineInfoToDb(ArrayList<Map> delMap) {

        for (Map<String, String> map : delMap) {
            customMachineRepository.deleteBymineId(map.get("id"));
            log.info("deleted Machine data ={}", map);
        }
    }
}
