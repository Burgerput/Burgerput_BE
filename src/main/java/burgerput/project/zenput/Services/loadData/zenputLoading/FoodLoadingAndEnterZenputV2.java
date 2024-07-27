package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.driverRepository.FoodDriverRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

//Optimize version!
@Slf4j
@RequiredArgsConstructor

public class FoodLoadingAndEnterZenputV2 implements FoodLoadingAndEnterZenput {


    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final FoodRepository foodRepository;
    private final FoodDriverRepository foodDriverRepository;

    @Override
    public Map<Integer, Food> getInfo() {//get from am info
        log.info("Food Get Info Logic Start f rom FoodLoadingAndEnterZenputV2");

        Map<Integer, Food> result = new LinkedHashMap<>();

        System.setProperty("java.awt.headless", "false");
        WebDriver driver = movePageService.clickAmFood();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // JavaScript 로드 완료 대기
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

        try {
            Thread.sleep(3000);
            log.info("food site entered and rest 3000");
            //li class groups
            List<WebElement> section = driver.findElements(By.className("form_container_wrapper"));

            if (section.isEmpty()) {
                throw new NoSuchElementException("Can't enter the zenput Food list page");
//                Food food = new Food();
//                food.setId(-1);
//                food.setName("no");
//                food.setMin(0);
//                food.setMax(0);
//                result.put(food.getId(), food);

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

            log.info("quit the Food getInfo driver");
            //End process
            driver.close();
            driver.quit();

        } catch (Exception e) {
            driver.close();
            driver.quit();
            log.info("Food GetInfo Error occurred !");
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

        Map<String, String> result = new LinkedHashMap<>();
        //choose am/pm list Start ==============================
        WebDriver driver= null;

        //selenium enter logic start ========================================
        System.setProperty("java.awt.headless", "false");

        try {
            // 1. Enter Manager Name
            //a. getManager info from jsonf
            JSONObject paramO = new JSONObject(param);
            String mgrName = paramO.get("mgrname").toString();

            String time = paramO.get("time").toString();
            if (time.equals("AM")) {
                driver = movePageService.clickAmFood();

            } else if (time.equals("PM")) {
                driver = movePageService.clickPmFood();
                log.info("ENTER PM FOOD");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // JavaScript 로드 완료 대기
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            log.info("enver Food and rest 3000");
            Thread.sleep(3000);

            //b. Enter the manager textbox
            WebElement managerField = driver.findElement(By.id("field_18"));
            WebElement textarea = managerField.findElement(By.tagName("textarea"));

            textarea.click();
            textarea.sendKeys(mgrName);
//
//            //dummyStore setup start ===============================
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
//            log.info("dummyMap final ={}", dummyStore);
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
                }
            }

            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
            File file = new File("/home/ubuntu/burgerput/img/zenputFood"+ LocalDate.now()+ LocalTime.now() +".png");
            FileUtils.copyFile(screenshotAs, file);

            //  //*[@id="submit_form"]

            WebElement submitForm = wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("submit_form"))));
            submitForm.click();

            log.info("Food submit clicked in the SendValue()");
            log.info("quit the Driver ()");
            driver.quit();

//          성공했을 시에 result에 true 값 저장
            result.put("result", "true");
            //FoodDriverREpository memeroy repository에 해당 값 저장
//            foodDriverRepository.setDriver(driver);

        } catch (ElementNotInteractableException e) {
            //에러나면 false 리턴
            log.info("Food sendValueV2 ElementNOtInteractableException");
            log.info(e.toString());
            //에러가 난 selenium driver 는 종료
            driver.quit();
            return result;

        }
        catch (InterruptedException e) {

            log.info("runTime eXcpetion ");
            log.info(e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("making img File exception");
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
                        log.info("enter Map info {}", customMap);

                        input.sendKeys(customMap.get("temp"));
                        input.sendKeys(Keys.TAB);

                        Thread.sleep(250);
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
//            log.info("Error LoadFood={}", e.toString());
        }

    }

    public Food extractIdTitle(WebElement field) throws Exception {
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
        } catch (Exception e) {
            log.info("Error LoadFood={}", e.toString());
            throw new Exception(e);
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

    private void tempLogic(String temp, Food food) {

        if (temp.contains("~")) {
            String[] split = temp.split("~");
            food.setMax(Integer.parseInt(split[1]));
            food.setMin(Integer.parseInt(split[0]));
        }else{
            temp = temp.trim();
            food.setMin(Integer.parseInt(temp));
            food.setMax(185);
        }


    }

}
