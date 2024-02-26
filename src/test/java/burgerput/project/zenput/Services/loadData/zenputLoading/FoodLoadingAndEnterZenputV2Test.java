package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.Services.movePage.MovePageServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
//@SpringBootTest
class FoodLoadingAndEnterZenputV2Test {

    @Autowired
    private MovePageService move;

    @Test
    void sendValueV2() {
        WebDriver driver = move.clickPmFood();

        WebElement button = driver.findElement(By.id("submit_form"));
        button.click();


    }
}