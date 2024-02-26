package burgerput.project.zenput.web.manager.food;

import burgerput.project.zenput.Services.printData.PrintData;
import burgerput.project.zenput.Services.saveData.SaveData;
import burgerput.project.zenput.domain.CustomFood;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SelectFoodController {
    private final FoodRepository foodRepository;
    private final CustomFoodRepository customFoodRepository;
    private final SaveData saveData;
    private final PrintData printData;

    @GetMapping("/back/select/foods") //식품 목록 출력
    @ResponseBody
    public ArrayList<Map> showFoodList() {
        ArrayList<Map> result = printData.zenputFood();
        return result;
    }

    @PostMapping("/back/select/foods")//선택한 식품의 값
    @ResponseBody
    public void selected(@RequestBody ArrayList<Map> param) {
        //log.info("what is id={},", map.get("id"));
        //table의 내용을 전부 지웠다가 다시 저장
        log.info("Selected Food param ={}", param.toString());
        saveData.customFoodDataSave(param);
    }

}
