package burgerput.project.zenput.Services.manager.food;

import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EnterFoodServiceImpl implements EnterFoodService{

    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final PrintData printData;

    @Override
    public Map<String, ArrayList<Map>> showFood() {

        Map<String, ArrayList<Map>> tempMap = new LinkedHashMap<>();

        ArrayList<Map> customFood = printData.customFood();

        tempMap.put("customFood", customFood);

        ArrayList<Map> mgrMap = printData.mgrList();

        tempMap.put("mgrList", mgrMap);

        return tempMap;

    }

    @Override
    public Map<String, String> submitZenputFood(String param) {

        log.info("Food SendValue logic Start");
        Map<String, String> resultMap = foodLoadingAndEnterZenput.sendValueV2(param);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", resultMap.get("result").toString());
        return result;

    }
}
