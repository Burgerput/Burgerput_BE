package burgerput.project.zenput.Services.movePage;

import burgerput.project.zenput.domain.Accounts;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;


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
        WebDriverManager.chromedriver().setup();

        //remove being controlled option information bar
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        //서버에서 돌려서 안돼서 추가한 옵션
        options.addArguments("--headless=new");
        options.addArguments("--single-process");
        options.addArguments("--no-sandbox");

//            options.addArguments("--disable-dev-shm-usage");
//            options.addArguments("--single-process");
//            options.addArguments("--remote-allow-origins=*");
//            options.setBinary("/opt/google/chrome/");

        //서버에서 돌려서 어쩌구 옵션 끝
        WebDriver driver = new ChromeDriver(options);

        try {
//            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
            //chrome driver use

            driver.manage().window().setSize(new Dimension(1024, 6000));
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            //==============================Scrape LOGIC START============================

            log.info("driver get option");

            //GO TO PAGE
            driver.get(zenputPageStart);

            log.info("Zenput driver Start");
            Thread.sleep(1000);

//            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
//            File file = new File("/home/ubuntu/burgerput/ref/zenput.png");
//            FileUtils.copyFile(screenshotAs, file);
            //no thanks button click
            try {
                log.info("Find no thankes button");
                WebElement oiwBtn = driver.findElement(By.xpath("//*[@id=\"oiw_btn\"]"));
                oiwBtn.click();

            } catch (NoSuchElementException e) {
                //then start 회사 이름 누르기
                //회사 이름 누르기 없으면 그냥 넘어가기

            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            try {
                //then start 회사 이름 누르기
                log.info("Enter company Id and click button page");
//                    File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
//                    File file = new File("/home/ubuntu/burgerput/ref/dirverpic.png");
//                    FileUtils.copyFile(screenshotAs, file);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                Thread.sleep(1500);
                boolean loading = true;
                while (loading) {
                    //input field - email 입력 필드
//                        WebElement input = driver.findElement(By.xpath("//*[@id=\"login_form\"]/div[4]/div[1]/input[1]"));
                    WebElement loginSignupFields = driver.findElement(By.className("login_signup_fields"));
                    WebElement input = loginSignupFields.findElement(By.tagName("input"));
                    //zenput 회사 이메일 필요
//                    input.sendKeys("rgm21490@rest.whopper.com");
                    input.sendKeys(ZENPUTID);

                    if (input.getAttribute("value").equals("")) {

                    } else {
                        loading = false;
                        Thread.sleep(1500); //1.5초 대기

                        log.info("button click start");
                        //input 에서 enter

                        //continue 버튼 클릭
                        WebElement loginContinue = driver.findElement(By.id("login_continue"));
                        loginContinue.click();
                    }
                }

            } catch (ElementNotInteractableException e) {
                driver.quit();
                log.info("error = {}", e);
            }

            Thread.sleep(2000); //2seconds

            log.info("continue button clicked time ={}", LocalDateTime.now());
            log.info("okta login page start");

            //rbi 계정 필요
            //rbi username
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            WebElement oktaLogin = driver.findElement(By.id("okta-signin-username"));
//            oktaLogin.sendKeys("다이강000001");

            oktaLogin.sendKeys(RBIID);
            //rbi password
            WebElement oktaPassword = driver.findElement(By.id("okta-signin-password"));
//            oktaPassword.sendKeys("Axjalsjf123456");
            oktaPassword.sendKeys(RBIPW);

            //sign-in button
            WebElement oktaButton = driver.findElement(By.id("okta-signin-submit"));
            oktaButton.click();
            //https://asdf:Axjalsjf123456@rbi.kerberos.okta.com/
            //http://%EB%8B%A4%EC%9D%B4%EA%B0%95000001:Axjalsjf123456%40rbi.kerberos.okta.com/login/sso_iwa
            return driver;


        } catch (StaleElementReferenceException e) {
            driver.quit();
            log.info("noSuchEletmet = {}", e);

        } catch (Exception e) {
            driver.quit();
            log.info("Thread.sleep error [{}]", e);
        }
        return null;
    }

    @Override
    public WebDriver clickAmFood() {
        // BK - 오전 AM 체크리스트를 작성합니다- (제품) - Product Quality Check (AM) - KO_APAC
        String pmFood = "BK - 오전 AM 체크리스트를 작성합니다- (제품) - Product Quality Check (AM) - KO_APAC";
        WebDriver driver = getListClick(pmFood);

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

    private WebDriver getListClick(String listText)  {
        WebDriver driver = gotoListWithLogin();
        try {
            Thread.sleep(2000);

            List<WebElement> listTitles = driver.findElements(By.className("taskitem_title"));
            try {
                for (WebElement listTitle : listTitles) {
                    String listName = listTitle.getAttribute("innerText");
                    log.info(listName);

                    if (listText.equals(listName)) {
                        listTitle.click();
                        log.info("list Title Clicked = {} and sleep 2000", listName);

                        log.info("list clicked pictrue created");
                        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
                        File file = new File("/home/ubuntu/burgerput/img/move/Dirverpic:"+listName+LocalDateTime.now()+".png");
                        FileUtils.copyFile(screenshotAs, file);

                        Thread.sleep(1000);

                        //양식으로 이동
                        WebElement submitForm = driver.findElement(By.id("submit_form"));
                        submitForm.click();

                        Thread.sleep(1000);

                        log.info("Clicked submitForm and take a screenshot");
                        screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
                        file = new File("/home/ubuntu/burgerput/img/move/form:"+listName+LocalDateTime.now()+".png");
                        FileUtils.copyFile(screenshotAs, file);

                        return driver;
                    }
                }
            } catch (Exception e) {
                driver.quit();
                log.info("Get List Click from MovePageServiceV1");
                log.info("Error message " +"\n" + "{}",e.toString());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return driver;

    }


}
