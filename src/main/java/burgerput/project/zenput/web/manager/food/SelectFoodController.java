package burgerput.project.zenput.web.manager.food;

import burgerput.project.zenput.Services.manager.food.SelectFoodService;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.utils.saveData.SaveData;
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
    private final SelectFoodService selectFoodService;

    @GetMapping("/back/select/foods") //식품 목록 출력
    @ResponseBody
    public ArrayList<Map> showFoodList() {
        return selectFoodService.showFood();
    }

    @PostMapping("/back/select/foods")//선택한 식품의 값
    @ResponseBody
    public void selected(@RequestBody ArrayList<Map> param) {
        selectFoodService.saveSelected(param);
    }

}
