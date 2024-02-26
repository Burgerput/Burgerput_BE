//package burgerput.project.zenput.web.manager.machine;
//
//import burgerput.project.zenput.repository.driverRepository.MachineDriverRepository;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class SubmitMachineController {
//
//    private final MachineDriverRepository machineDriverRepository;
//
//    @GetMapping("back/msubmit")
//    public void submitMachine() throws IOException {
//        WebDriver driver = machineDriverRepository.getDriver();
//
////        WebElement mgrName = driver.findElement(By.xpath("//*[@id=\"field_1\"]/div[2]/textarea"));
////        mgrName.sendKeys("성공했다");
//
//        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
//        File file = new File("/home/ubuntu/burgerput/img/zenputMachine.png");
//        FileUtils.copyFile(screenshotAs, file);
//
//        WebElement button = driver.findElement(By.xpath("//*[@id=\"submit_form\"]"));
//        button.click();
//
//        log.info("button Clicked!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        //close the webPage
//
////        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
////        File file = new File("C:/Users/bbubb/Desktop/Burgerput/zenput.png");
////        FileUtils.copyFile(screenshotAs, file);
//        log.info("quite the driver");
//        driver.quit();
//
//
//
//
//    }
//}
