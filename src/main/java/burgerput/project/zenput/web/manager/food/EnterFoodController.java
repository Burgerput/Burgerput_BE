package burgerput.project.zenput.web.manager.food;

import burgerput.project.zenput.Services.manager.food.EnterFoodService;
import burgerput.project.zenput.Services.manager.food.EnterFoodServiceImpl;
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

    private final EnterFoodService enterFoodService;

    @GetMapping("/back/enter/foods")
    @ResponseBody
    public Map<String, ArrayList<Map>> showFood() {
        return enterFoodService.showFood();
    }

    @PostMapping("/back/enter/foods")
    @ResponseBody
    public Map<String,String> submitZenputFood(@RequestBody String param) {
        return enterFoodService.submitZenputFood(param);
    }
}
