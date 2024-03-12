package burgerput.project.zenput.Services.loadData.LoadingTest;

import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV2T;
import burgerput.project.zenput.domain.Food;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Map;

@Slf4j
@DataJpaTest
class FoodLoadingAndEnterZenputV2TTest {

    private FoodLoadingAndEnterZenputV2T food = new FoodLoadingAndEnterZenputV2T();
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