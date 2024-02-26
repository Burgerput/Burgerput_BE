package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.jsonObject.MyJsonParserV1;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.Services.movePage.MovePageServiceV1;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class FoodLoadingAndEnterZenputV2TestTest {

    MyJsonParser myJsonParser = new MyJsonParserV1();

    @Autowired
    ZenputAccountRepository zenputAccountRepository;
    MovePageService movePageService = new MovePageServiceV1(zenputAccountRepository);


    @Autowired
    private FoodRepository foodRepository;

//    FoodLoadingAndEnterZenput FoodLoading = new FoodLoadingAndEnterZenputV2Test(movePageService,myJsonParser,foodRepository);

    @Test
    @DisplayName("getInfoTest")
    public void getInfoTest() {

//        Map<Integer, Food> info = FoodLoading.getInfo();
//       log.info(info.toString());
    }

}