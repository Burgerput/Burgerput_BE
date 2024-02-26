package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.driverRepository.FoodDriverRepository;
import burgerput.project.zenput.repository.driverRepository.FoodDriverRepositoryV1;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static burgerput.project.zenput.Const.FOODURL;

//Optimize version!
@Slf4j
@RequiredArgsConstructor

public class FoodLoadingAndEnterZenputV2Test implements FoodLoadingAndEnterZenput {


    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final FoodRepository foodRepository;
    private final FoodDriverRepository foodDriverRepository;

    @Override
    public Map<Integer, Food> getInfo() {//get from am info
        Map<Integer, Food> result = new LinkedHashMap<>();

        System.setProperty("java.awt.headless", "false");

        try {
//            System.setProperty("webdriver.chrome.driver", DRIVERLOCATION);
            //chrome driver use
            //automatic web driver management through webdrivermanager

            WebDriverManager.chromedriver().setup();

            //remove being controlled option information bar
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            options.addArguments("--no-sandbox");
            options.addArguments("--headless=new");

            WebDriver driver = new ChromeDriver(options);

            driver.get(FOODURL);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            //==============================Scrape LOGIC START============================

            //li class group
            List<WebElement> section = driver.findElements(By.className("form_container_wrapper"));

            if (section.isEmpty()) {
                Food food = new Food();
                food.setId(-1);
                food.setName("no");
                food.setMin(0);
                food.setMax(0);
                result.put(food.getId(), food);

            } else {

                for (WebElement fields : section) {
                    List<WebElement> elements = fields.findElements(By.className("form-field"));
                    log.info("SECTION START");

                    for (WebElement field : elements) {

                        String id = field.getAttribute("id");
                        if (id.equals("field_295")) {
                            break;
                        } else {
                            Food contents = extractIdTitle(field);
                            if (!(contents.getName() == null)) {
                                //if map is empty then not save the data

                                log.info("contents ={}", contents);
                                result.put(contents.getId(), contents);

                            }
                        }
                    }
                }
            }
            log.info("dirver quit");
            //End process
            driver.close();
            driver.quit();

        } catch (Exception e) {
            log.info(e.toString());
        }
//         log.info("result = {}", result);
        return result;
    }

    // ================ send Data to Zenput======================
    @Override
    public void sendValue(String param) {
    }

    //send result true false value and driver
    @Override
    public Map<String,String> sendValueV2(String param) {

        log.info("param = {}", param);
        Map<String, String> result = new LinkedHashMap<>();
        //choose am/pm list Start ==============================
        WebDriver driver= null;

        //selenium enter logic start ========================================

        try {
            //selenium enter logic start ========================================
            System.setProperty("java.awt.headless", "false");

            //chrome driver use

            WebDriverManager.chromedriver().setup();


            //remove being controlled option information bar
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.addArguments("--headless=new");

            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1024, 9999));


            //==============================Scrape LOGIC START============================

            //GO TO PAGE
            driver.get(FOODURL);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 1. Enter Manager Name
            //a. getManager info from jsonf
            JSONObject paramO = new JSONObject(param);
            String mgrName = paramO.get("mgrname").toString();


            //b. Enter the manager textbox
            WebElement managerField = driver.findElement(By.id("field_18"));
            WebElement textarea = managerField.findElement(By.tagName("textarea"));

            textarea.click();
            textarea.sendKeys(mgrName);
            Thread.sleep(30);

            //dummyStore setup start ===============================
            ArrayList<Map<String, String>> dummyStore = dummyStoreMaker();

            //dummyMap changed
            ArrayList<Map> customFoodMap = myJsonParser.jsonStringToArrayList(param);

            for (Map<String, String> customMap : customFoodMap) {
                //id와 이름이 똑같으면 이거로 temp값을 변경하기 dummyStore의 temp값을 변경한다.
                for (Map<String, String> dummyMap : dummyStore) {
                    if (dummyMap.get("id").equals(customMap.get("id"))) {

                        dummyMap.put("temp", customMap.get("temp"));

                    }
                }
            }
            //===================dummy setup END =======================

            log.info("dummyMap final ={}", dummyStore);

            List<WebElement> section = driver.findElements(By.className("form_container_wrapper"));

            for (WebElement fields : section) {
                List<WebElement> elements = fields.findElements(By.className("form-field"));
                log.info("SECTION START");
                for (WebElement field : elements) {
                    //Enter customValueStart ===============================
                    String id = field.getAttribute("id");
                    if(!(id.equals("field_295") | id.equals("field_19") | id.equals("field_18"))){
//                        log.info("where's id?'{}", id);

                        enterValue(field, dummyStore, result);
                    }
                    if (result.containsValue("false")) {
                        ElementNotInteractableException e =  new ElementNotInteractableException("No such Element Exception Occured 에러에러에러에ㅓㄹ");
                        throw e;
                    }

                }

            }
            //성공했을 시에 result에 true 값 저장
            result.put("result", "true");
            //FoodDriverREpository memeroy repository에 해당 값 저장
            foodDriverRepository.setDriver(driver);
//            saveButtonClick(driver);

        } catch (ElementNotInteractableException e) {
            //에러나면 false 리턴
            log.info("errore errororororrorororororororororororororororororororororo");
            log.info(e.toString());
            //에러가 난 selenium driver 는 종료
            driver.quit();
            return result;

        } catch (InterruptedException e) {
            log.info("runTime eXcpetion ");
            log.info(e.toString());
            throw new RuntimeException(e);
        }
        return result;
    }

    private ArrayList<Map<String, String>> dummyStoreMaker() {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        List<Food> allFood = foodRepository.findAll();

        for (Food food : allFood) {
            Map<String, String> tempMap = new LinkedHashMap<>();
            tempMap.put("id", Integer.toString(food.getId()));
            tempMap.put("temp", "999");
            tempMap.put("name", food.getName());

            result.add(tempMap);
        }
        return result;
    }

    private void enterValue(WebElement field, ArrayList<Map<String, String>> foodMap, Map<String,String> resultMap) {

        try {
            //extract vaild id field
            WebElement input = field.findElement(By.tagName("input"));

            String id = input.getAttribute("field_id");

            for (Map<String, String> customMap : foodMap) {
                try {
                    if (id.equals(customMap.get("id"))) {
                        log.info("custom Map info put this result={}", customMap);
                        input.sendKeys(customMap.get("temp"));


                        Thread.sleep(500);
                        customMap.remove(id);
                        break;
                    }
                } catch (ElementNotInteractableException e) {
                    log.info("Exception Occured in the entervalue Method!!");
                    log.info(e.toString());

                    resultMap.put("result", "false");
                    //do nothing
                }
            }

        } catch (Exception e) {
            log.info("Error LoadFood={}", e.toString());
        }

    }

    @Override
    public Food extractIdTitle(WebElement field) {
        Food food = new Food();
        try {
            WebElement input = field.findElement(By.tagName("input"));
            WebElement fieldTitle = field.findElement(By.className("field_title"));

            String id = input.getAttribute("field_id");
            //if getText not works use this "innerText"
            String title = fieldTitle.getAttribute("innerText");


            if (title.contains("F")) {
                food = extractTitle(title);
                food.setId(Integer.parseInt(id));
            }
        } catch (NoSuchElementException e) {
            log.info("Error LoadFood={}", e.toString());
        }
        return food;
    }

    private Food extractTitle(String title) {

        Food food = new Food();

        if (title.contains("-")) {
            String[] split = title.split("-");
//            String[] split = title.split("&#41;");

            String sample = split[0];


            String name = sample.contains(")") ? (sample + "") : (sample + ")");
            food.setName(name);

            //temp setup
            String s = split[split.length - 1].replaceAll("[a-zA-Z가-힣* ]", "");

            tempLogic(s, food);

//            log.info(minS);
        } else if (title.contains(":")) {
            String[] split = title.split(":");

            String sample = split[0];
            food.setName(sample);
            String s = split[1].replaceAll("[a-zA-Z가-힣* ]", "");

            tempLogic(s, food);
        }

        return food;
    }

    private void tempLogic(String s, Food food) {

        if (s.contains("~")) {
            String[] split = s.split("~");
            food.setMax(Integer.parseInt(split[1]));
            food.setMin(Integer.parseInt(split[0]));
        }else{
            food.setMin(Integer.parseInt(s));
            food.setMax(185);
        }


    }

}
