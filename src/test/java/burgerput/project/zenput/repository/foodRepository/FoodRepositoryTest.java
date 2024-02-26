package burgerput.project.zenput.repository.foodRepository;

import burgerput.project.BurgerputProjectApplication;
import burgerput.project.zenput.Config;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.domain.CustomFood;
import burgerput.project.zenput.domain.Food;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@Slf4j
@Import(Config.class)
@ContextConfiguration(classes = BurgerputProjectApplication.class)
class FoodRepositoryTest {
    @Autowired
    private FoodLoadingAndEnterZenput foodLoading;

    @Autowired
    private CustomFoodRepository csFoodRepository;
    @Autowired
    private FoodRepository foodREpository;

    @BeforeEach
    public void initData() {
        Food food = new Food();
        food.setId(1);
        food.setName("Food1");
        food.setMin(32);
        food.setMax(82);

        Food food2 = new Food();
        food2.setId(2);
        food2.setName("Food2");
        food2.setMin(32);
        food2.setMax(82);

        Food food3 = new Food();
        food3.setId(3);
        food3.setName("Food3");
        food3.setMin(32);
        food3.setMax(82);

        Food food4 = new Food();
        food4.setId(4);
        food4.setName("Food4");
        food4.setMin(32);
        food4.setMax(82);


        foodREpository.save(food);
        foodREpository.save(food2);
        foodREpository.save(food3);
        foodREpository.save(food4);
    }


    @Test
    @DisplayName("customFood save logic")
    public void CustomSave() {
        CustomFood csFood1 = new CustomFood();
        csFood1.setId(1);

        CustomFood csFood2 = new CustomFood();
        csFood2.setId(2);

        csFoodRepository.save(csFood1);
        csFoodRepository.save(csFood2);

    }
}