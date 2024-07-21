package burgerput.project.zenput.Services.movePage;

import burgerput.project.zenput.domain.Accounts;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static burgerput.project.zenput.Const.*;


@Slf4j
@RequiredArgsConstructor
public class MovePageServiceV1 implements MovePageService {

    private final ZenputAccountRepository zenputAccountRepository;

    private static String ZENPUTID;
    private static String RBIID;
    private static String RBIPW;

    private void zenputAccountSetting() {

        Accounts accounts = zenputAccountRepository.findAll().stream().findFirst().get();
        ZENPUTID = accounts.getZenputId();
        RBIID = accounts.getRbiId();
        RBIPW = accounts.getRbiPw();

    }

    @Override
    public WebDriver sampleMachine() {

        System.setProperty("java.awt.headless", "false");

        try {
            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
            //chrome driver use

            //remove being controlled option information bar
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            WebDriver driver = new ChromeDriver(options);

            driver.get(MACHINEURL);

            return driver;
        } catch (Exception e) {
            log.info("error log ={}", e.toString());
        }

        return null;
    }

    public WebDriver sampleFood() {
        //NOT CLICK LIST JUST COPY AND PASTE LOGIC
        System.setProperty("java.awt.headless", "false");
        try {
            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
            //chrome driver use

            //remove being controlled option information bar
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            WebDriver driver = new ChromeDriver(options);

            //==============================Scrape LOGIC START============================
            //GO TO PAGE
            driver.get(FOODURL);

            return driver;
        } catch (Exception e) {
            log.info("error log ={}", e.toString());
        }
        return null;
    }


    public WebDriver gotoList() {
        System.setProperty("java.awt.headless", "false");
        try {
            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
            //chrome driver use

            //remove being controlled option information bar
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            WebDriver driver = new ChromeDriver(options);

            //==============================Scrape LOGIC START============================
            //GO TO PAGE
            driver.get(zenputPageStart);

            WebElement oiwBtn = driver.findElement(By.id("oiw_btn"));
            oiwBtn.click();

            log.info("CLICKED!");

        } catch (Exception e) {
            log.info("error log ={}", e.toString());
        }
        return null;

    }
    //페이지 로그인 까지 하는 것


    @Override
    public WebDriver gotoListWithLogin() {

        //zenputAccoutns setup
        zenputAccountSetting();

        System.setProperty("java.awt.headless", "false");

//        WebDriverManager.chromedriver().setup();

        //remove being controlled option information bar
//        WebDriverManager.chromedriver().config().setLogLevel(io.github.bonigarcia.wdm.config.LogLevel.DEBUG);
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        //서버에서 돌려서 안돼서 추가한 옵션
        options.addArguments("--headless=new");
        options.addArguments("--single-process");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

//            options.addArguments("--single-process");
//            options.addArguments("--remote-allow-origins=*");
//            options.setBinary("/opt/google/chrome/");

        //서버에서 돌려서 어쩌구 옵션 끝
        WebDriver driver = new ChromeDriver(options);

//        try {
//            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
        //chrome driver use

        driver.manage().window().setSize(new Dimension(1024, 4000));

//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //==============================Scrape LOGIC START============================

        log.info("driver get option");

        //GO TO PAGE
        driver.get(zenputPageStart);

        log.info("Zenput driver Start");
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // JavaScript 로드 완료 대기
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
//            Thread.sleep(3000);
//            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
//            File file = new File("/home/ubuntu/burgerput/ref/zenput.png");
//            FileUtils.copyFile(screenshotAs, file);

        logBrowserConsoleLogs(driver);
//        //no thanks button click
//        try {
//            //then start 회사 이름 누르기
//            WebElement oiwBtn = driver.findElement(By.xpath("//*[@id=\"oiw_btn\"]"));
//            oiwBtn.click();
//            log.info("no thankes button Clicked");
//
//        } catch (NoSuchElementException e) {
//            //회사 이름 누르기 없으면 그냥 넘어가기
//        }
        //then start 회사 이름 누르기
        log.info("Enter company Id and click button page");

        logBrowserConsoleLogs(driver);

        try {
//            takeScreenshot(driver, "C:\\Users\\bbubb\\Desktop\\Burgerput\\testssl\\loaded.png");
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login_signup_fields")))
                    .findElement(By.cssSelector("input[type='email']"));

            log.info("'login_signup_fields' element found");
            input.sendKeys(ZENPUTID);

//            takeScreenshot(driver, "C:\\Users\\bbubb\\Desktop\\Burgerput\\testssl\\sendKeys.png");
            log.info("'login_continue' element found");
            WebElement loginbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_continue")));
            loginbtn.click();
//            takeScreenshot(driver, "C:\\Users\\bbubb\\Desktop\\Burgerput\\testssl\\buttonclick.png");
            // 추가 작업 수행
        } catch (NoSuchElementException e) {
            log.warn("'login_signup_fields' element not found", e);
//            takeScreenshot(driver, "C:\\Users\\bbubb\\Desktop\\Burgerput\\testssl\\element_not_found.png");
        }

        log.info("continue button clicked time ={}", LocalDateTime.now());
        log.info("okta login page start");
        //wait
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
        logBrowserConsoleLogs(driver);

        //rbi 계정 필요
        //rbi username
//        takeScreenshot(driver, "C:\\Users\\bbubb\\Desktop\\Burgerput\\testssl\\element_not_found.png");
        log.info("okta- signin username ");
        WebElement oktaLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
        oktaLogin.sendKeys(RBIID);

        log.info("okta- signin password ");
        WebElement oktaPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-password")));
        oktaPassword.sendKeys(RBIPW);

        log.info("okta-signin click the button ");
        WebElement oktaButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("okta-signin-submit")));
        oktaButton.click();
        //https://asdf:Axjalsjf123456@rbi.kerberos.okta.com/
        //http://%EB%8B%A4%EC%9D%B4%EA%B0%95000001:Axjalsjf123456%40rbi.kerberos.okta.com/login/sso_iwaZ
        log.info("complete login!");

        return driver;
    }

    @Override
    public WebDriver clickAmFood() {
        // BK - 오전 AM 체크리스트를 작성합니다- (제품) - Product Quality Check (AM) - KO_APAC
        String amFood = "BK - 오전 AM 체크리스트를 작성합니다- (제품) - Product Quality Check (AM) - KO_APAC";
        WebDriver driver = getListClick(amFood);

        return driver;
    }

    @Override
    public WebDriver clickPmFood() {
//오후 PM 체크리스트를 작성합니다- (제품) - Product Quality Check (PM) - KO_APAC,
        String pmFood = "BK - 오후 PM 체크리스트를 작성합니다- (제품) - Product Quality Check (PM) - KO_APAC";
        WebDriver driver = getListClick(pmFood);

        return driver;
    }

    @Override
    public WebDriver clickAmMachine() {
        //BK - 오전 AM 체크리스트를 작성합니다- (기기장비) - Equipment Quality Check (AM) - KO_APAC

        String pmFood = "BK - 오전 AM 체크리스트를 작성합니다- (기기장비) - Equipment Quality Check (AM) - KO_APAC";
        WebDriver driver = getListClick(pmFood);

        return driver;
    }


    @Override
    public WebDriver clickPmMachine() {
        //오후 PM 체크리스트를 작성합니다- (기기장비) - Equipment Quality Check (PM) - KO_APAC
        String pmMachine = "BK - 오후 PM 체크리스트를 작성합니다- (기기장비) - Equipment Quality Check (PM) - KO_APAC";
        WebDriver driver = getListClick(pmMachine);

        return driver;
    }

    private WebDriver getListClick(String listText) {
        WebDriver driver = gotoListWithLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 페이지 로드 완료 대기
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
//            Thread.sleep(10000);
        List<WebElement> listTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("taskitem_title")));
        if (listTitles.isEmpty()) {
            log.info("is empty but why?...");
        } else {
                for (WebElement listTitle : listTitles) {
                    String listName = listTitle.getAttribute("innerText");
                    log.info(listName);
                    if (listText.equals(listName)) {
                        listTitle.click();
                        log.info("list Title Clicked = {} and sleep 2000", listName);

                        //page 이동
                        WebDriverWait waitEnter = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));
                        JavascriptExecutor jsEnter = (JavascriptExecutor) driver;
                        waitEnter.until(webDriver -> jsEnter.executeScript("return document.readyState").equals("complete"));

                        //양식으로 이동
                        WebElement submitForm = waitEnter.until(ExpectedConditions.visibilityOfElementLocated((By.id("submit_form"))));
                        submitForm.click();

                        return driver;
                    }
                }

        }

        return driver;

    }

    //web log확인을 위한 코드

    private void logBrowserConsoleLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            log.info("BROWSER LOG: " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    //for Test
//    public static void takeScreenshot(WebDriver driver, String filePath) {
//        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        try {
//            FileHandler.copy(screenshot, new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
