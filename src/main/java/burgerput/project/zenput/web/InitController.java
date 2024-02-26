package burgerput.project.zenput.web;

import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.intercepter.checkSession.CreateSession;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class InitController {
    private final MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;
    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;

    private final MachineRepository machineRepository;
//    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final FoodRepository foodRepository;

    //save Data -> Machine and Food
    //임시로 init 하는 컨트롤러 main의 로직은 아직 짜지 않음
    @GetMapping("/initData")
    public void saveMachineAndFood() {

        //임시로 다지우고 시작 -> 변경해야하는 로직
        machineRepository.deleteAllMIne();


        Map<Integer, Machine> machineInfo = machineLoadingAndEnterZenput.getInfo();
        for (Integer key : machineInfo.keySet()) {
            Machine machine = machineInfo.get(key);

            machineRepository.save(machine);
        }

        //임시로 다지우고 시작 -> 변경해야하는 로직
        foodRepository.deleteAllMIne();

        Map<Integer, Food> foodinfo = foodLoadingAndEnterZenput.getInfo();
        for (Integer key : foodinfo.keySet()) {
            Food food = foodinfo.get(key);

            foodRepository.save(food);
        }
    }

    @GetMapping("delCookie")
    public void delCookie(HttpServletRequest request, HttpServletResponse response) {
        CreateSession session = new CreateSession();
        session.expire(request, response);

    }
}
