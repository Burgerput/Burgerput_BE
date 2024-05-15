package burgerput.project.zenput.Services.loadData.LoadingTest;

import burgerput.project.zenput.Services.loadData.alertCheck.AlertLoading;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV2T;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenputV2T;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@SpringBootTest
class FoodLoadingAndEnterZenputV2TTest {

    private FoodLoadingAndEnterZenputV2T food = new FoodLoadingAndEnterZenputV2T();
    private MachineLoadingAndEnterZenputV2T machine = new MachineLoadingAndEnterZenputV2T();

    @Autowired
    private AlertLoading alertLoading;

    @Autowired
    private CustomMachineRepository customMachineRepository;

    @Autowired
    CustomFoodRepository customFoodRepository;


    @Test
    @DisplayName("getInfoTest")
    public void getInfoTest() {
        try {
            Map<Integer, Food> info = food.getInfo();
            log.info(info.toString());
        } catch (Exception e) {
            log.info(e.toString());
        }

//        Map<Integer, Food> info = FoodLoading.getInfo();
//       log.info(info.toString());
    }




}