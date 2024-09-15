package burgerput.project.zenput.web.manager.food;

import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.movePage.MovePageService;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class EnterFoodController {

    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final PrintData printData;

    @GetMapping("/back/enter/foods")
    @ResponseBody
    public Map<String, ArrayList<Map>> enterFood() {

        Map<String, ArrayList<Map>> tempMap = new LinkedHashMap<>();

        ArrayList<Map> customFood = printData.customFood();

        tempMap.put("customFood", customFood);

        ArrayList<Map> mgrMap = printData.mgrList();

        tempMap.put("mgrList", mgrMap);

        return tempMap;
    }

    @PostMapping("/back/enter/foods")
    @ResponseBody
    public Map<String,String> submitZenputFood(@RequestBody String param) {
log.info("Food SendValue logic Start");
        Map<String, String> resultMap = foodLoadingAndEnterZenput.sendValueV2(param);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", resultMap.get("result").toString());
        return result;
    }
}
