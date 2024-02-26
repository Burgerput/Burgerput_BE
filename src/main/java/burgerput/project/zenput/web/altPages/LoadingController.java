package burgerput.project.zenput.web.altPages;

import burgerput.project.zenput.Services.loadData.alertCheck.AlertLoading;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.Services.saveData.SaveData;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static burgerput.project.zenput.Const.BURGERPUTSITE;

@Controller
@Slf4j
@RequiredArgsConstructor
@RestController
//@Transactional
@RequestMapping(value="loading", method={RequestMethod.GET, RequestMethod.POST})
public class LoadingController {
    private final SaveData saveData;
    private final AlertLoading alertLoading;
    private final MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;
    private final CustomMachineRepository customMachineRepository;
    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final CustomFoodRepository customFoodRepository;

    //ZenputPage loading 후에 달라진 값들을 Alert로 넘긴다.
    @GetMapping
    public Map<String,String> loading(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("Loading Logic Start  ={}", LocalDateTime.now());
        //Start Loading Logic
        //loading zenput Page's Data first
        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");
        try {
            log.info("MACHINE DATA GET STASRT");
            Map<Integer, Machine> machineInfo = machineLoadingAndEnterZenput.getInfo();
            log.info("loaded Machine Map info : {}", machineInfo);

            log.info("FOOD DATA GET STASRT");
            Map<Integer, Food> foodInfo = foodLoadingAndEnterZenput.getInfo();
            log.info("Loaded Food Map info : {}", foodInfo);

            if (machineInfo.size() == 1 || foodInfo.size() ==1) {
                //false
                log.info("FALSE RESULT RETURN = false");
                resultMap.put("result", "false");

                return resultMap;
            }
            //====================loading logic================================
//        addMachine Logic=================================================
            ArrayList<Map> addMap = alertLoading.addMachine(machineInfo);
//
//        //del Machine logic
            ArrayList<Map> delMap = alertLoading.delMachine(machineInfo);
//
//        //editMachine logic=====================================
            ArrayList<Map> editMap = alertLoading.editMachine(machineInfo);
//
            //machine data를 로딩한 것으로 변경함
//      saveData.machinezenputdatasave(machineInfo);

            //====================Food logic start===========================
            ArrayList<Map> addFoodMap = alertLoading.addFood(foodInfo);
            ArrayList<Map> delFoodMap = alertLoading.delFood(foodInfo);
            ArrayList<Map> editFoodMap = alertLoading.editFood(foodInfo);

            ArrayList<Map> foodMaps = alertInfo(addFoodMap, delFoodMap, editFoodMap);

//=============save result To DB
//apply to DB -//only execute deleteMap(delete from customMachine and save whole machine data
            alertFoodInfoToDb(delFoodMap);
            alertMachineInfoToDb(delMap);

            saveData.machinezenputdatasave(machineInfo);
            saveData.foodZenputDataSave(foodInfo);

            log.info("Db Set-up END ");
//            response.sendRedirect(BURGERPUTSITE);

        } catch (Exception e) {
            resultMap.put("result", "false");
            log.info("Error from loading Controller!!! ");
            log.info(e.toString());
        }

        log.info("return value ={}", resultMap.get("result"));
        return resultMap;
    }
    //@GetMapping("/test")
    @ResponseBody
    @GetMapping("/test")
    public Map<String,String> loadingTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Map<Integer, Machine> machineInfo = machineLoadingAndEnterZenput.getInfo();
        log.info("LoadingTest Logic Start(only Food)  ={}", LocalDateTime.now());

        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");

        Map<Integer, Food> foodInfo = foodLoadingAndEnterZenput.getInfo();
        log.info("Loaded Food Map info : {}", foodInfo);

        if ( foodInfo.size() ==1) {
            //false
            log.info("FALSE RESULT RETURN = false");
            resultMap.put("result", "false");

            return resultMap;
        }

//
//////        addMachine Logic=================================================
//        ArrayList<Map> addMap = alertLoading.addMachine(machineInfo);
//
//        //del Machine logic
//        ArrayList<Map> delMap = alertLoading.delMachine(machineInfo);
//
//        //editMachine logic=====================================
//        ArrayList<Map> editMap = alertLoading.editMachine(machineInfo);
//
//        //machine data를 로딩한 것으로 변경함
////        saveData.machinezenputdatasave(machineInfo);
//
//        ArrayList<Map> maps = alertInfo(addMap, delMap, editMap);
//
//        log.info("machine alert info list ={}", maps);
//
////        //to Machine DB
//        alertMachineInfoToDb(delMap);
//
//        //machine data를 로딩한 것으로 변경함
////      saveData.machinezenputdatasave(machineInfo);
//
//        //====================Food logic start===========================
//        ArrayList<Map> addFoodMap = alertLoading.addFood(foodInfo);
//        ArrayList<Map> delFoodMap = alertLoading.delFood(foodInfo);
//        ArrayList<Map> editFoodMap = alertLoading.editFood(foodInfo);
//
//        ArrayList<Map> foodMaps = alertInfo(addFoodMap, delFoodMap, editFoodMap);
//
//        alertFoodInfoToDb(delFoodMap);
//
//        if (!addMap.isEmpty()) {
//            saveData.machinezenputdatasave(machineInfo);
//        }
//        if (!addFoodMap.isEmpty()) {
//            saveData.foodZenputDataSave(foodInfo);
//        }
//
        return resultMap;
    }

    private ArrayList<Map> alertInfo(ArrayList<Map> addMap, ArrayList<Map> delMap, ArrayList<Map> editMap) {

        //addMap logic
        ArrayList<Map> result = new ArrayList<>();
        if (!addMap.isEmpty() && !addMap.get(0).get("code").equals("all")) {

            for (Map map : addMap) {
                result.add(map);
            }

        }

        //edtiMap Logic
        if (!editMap.isEmpty()) {
            for (Map map : editMap) {
                result.add(map);
            }
        }


        //delMap logic
        if (!delMap.isEmpty()) {
            for (Map map : delMap) {
                result.add(map);
            }
        }
        return result;
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
