//package burgerput.project.zenput.web.manager.food;
//
//import burgerput.project.zenput.repository.driverRepository.FoodDriverRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.hibernate.result.Output;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class SubmitFoodController {
//
//    private final FoodDriverRepository foodDriverRepository;
//
//    @GetMapping("back/fsubmit")
//    public void submitFood() throws IOException, InterruptedException {
//        WebDriver driver = foodDriverRepository.getDriver();
//
//        Thread.sleep(1500);
//
////        WebElement button = driver.findElement(By.id("submit_form"));
//        WebElement button = driver.findElement(By.xpath("/html/body/div[7]/div/div[2]/section/div/div[2]/div/div[2]/div/div/div[1]/div[5]"));
//        String attribute = button.getAttribute("innerHTML");
//        // ble
//        log.info("button attribute = {}", attribute);
//
//        Actions actions = new Actions(driver);
//        actions.moveToElement(button).click().perform();
//
////        ((ChromeDriver) driver).executeScript("arguments[0].click();", button);
//
////        button.sendKeys(Keys.ENTER);
//
//        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
//        File file = new File("/home/ubuntu/burgerput/img/zenputFood.png");
////        File file = new File("C:/Users/bbubb/Desktop/Burgerput/burgerput-main/burgerputClone4/BURGERPUT/zenputFood.png");
//
//        FileUtils.copyFile(screenshotAs, file);
//
//        log.info("button Clicked!!!!!!!!!!!!!! - copy full xpath version");
//        log.info("V2 version button 주석처리했듬 사진만 찍어서 서버에 저장");
//
//        log.info("quite the driver");
//
//        driver.quit();
//
//    }
//}
