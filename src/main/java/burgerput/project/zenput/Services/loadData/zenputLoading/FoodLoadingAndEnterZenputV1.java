package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static burgerput.project.zenput.Const.DRIVERLOCATION;
import static burgerput.project.zenput.Const.FOODURL;

@Slf4j
@RequiredArgsConstructor
//@Service
public class FoodLoadingAndEnterZenputV1 implements FoodLoadingAndEnterZenput {
    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final FoodRepository foodRepository;

    @Override
    public Map<Integer, Food> getInfo() {//get from am info
        Map<Integer, Food> result = new LinkedHashMap<>();

        System.setProperty("java.awt.headless", "false");

        try {
            //test를 위해 pm으로 변경한다.
            WebDriver driver = movePageService.clickPmFood();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //li class group
            List<WebElement> fields = driver.findElements(By.className("form-field"));

            if (fields.size() == 0) {
                Food food = new Food();
                food.setId(-1);
                food.setName("no");
                food.setMin(0);
                food.setMax(0);
                result.put(food.getId(), food);
            } else {

                for (WebElement field : fields) {
                    Food contents = extractIdTitle(field);
                    if (contents.getName() == null) {
                        //if map is empty then not save the data
                    } else {
                        //log.info("contents =?", contents);
                        result.put(contents.getId(), contents);
                    }

                }
            }
            //End process
            driver.close();
            driver.quit();

        } catch (Exception e) {
            log.info(e.toString());
        }
        // log.info("result = {}", result);
        return result;
    }

    // ================ send Data to Zenput======================
    @Override
    public void sendValue(String param) {

        //choose am/pm list Start ==============================

        //selenium enter logic start ========================================
        System.setProperty("java.awt.headless", "false");

        WebDriver driver = null;

        log.info("param ={}", param);
        try {
            JSONObject paramO = new JSONObject(param);

            if (paramO.get("time").toString().equals("AM")) {
                driver = movePageService.clickAmFood();

            } else if(paramO.get("time").toString().equals("PM")){
                driver = movePageService.clickPmFood();

            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            // Enter the value

            // 1. Enter Manager Name
            //a. getManager info from json
            String mgrName = paramO.get("mgrname").toString();

            log.info("manager name = {}", mgrName);
            //b. Enter the manager textbox
            WebElement managerField = driver.findElement(By.id("field_18"));

            WebElement textarea = managerField.findElement(By.tagName("textarea"));

            textarea.click();
            textarea.sendKeys(mgrName);
            Thread.sleep(30);


            //li class group
            List<WebElement> fields = driver.findElements(By.className("form-field"));

            //dummyStore
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

            log.info("dummyMap final ={}", dummyStore);

            //Enter customValueStart ===============================
            for (WebElement field : fields) {
                //extract vaild id list logic
                enterValue(field, dummyStore);
            }

        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    @Override
    public Map<String, String> sendValueV2(String param) {
        return null;
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

    private void enterValue(WebElement field, ArrayList<Map<String, String>> foodMap) {

        try {
            //extract vaild id field
            WebElement input = field.findElement(By.tagName("input"));

            String id = input.getAttribute("field_id");

            for (Map<String, String> customMap : foodMap) {
                try {
                    if (id.equals(customMap.get("id"))) {

                        input.sendKeys(customMap.get("temp"));

                        Thread.sleep(500);
                        customMap.remove(id);
                        break;
                    }
                } catch (NullPointerException e) {
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

        } catch (Exception e) {
            log.info("Error LoadFood={}", e.toString());
        }
        return food;
    }

    private Food extractTitle(String title) {

        Food food = new Food();

        if (!title.equals("")) {
            //title is not null
            if (title.contains("-")) {

                String[] cutEx = title.split("-");

                if (cutEx.length == 3) {
                    // NAME
                    String name = (cutEx[0] + cutEx[1]).replaceAll(" ", "");
                    food.setName(name);

                    //TEMPERTURE
                    String[] temps = cutEx[2].split("~");

                    if (temps.length == 1) {
                        String minS = temps[0].replaceAll("[a-zA-Z가-힣* ]", "");
                        food.setMin(Integer.parseInt(minS));
                        //max value
                        food.setMax(185);

                    } else if (temps.length == 2) {
                        int i = 0;
                        for (String temp : temps) {
                            temp = temp.replaceAll("[a-zA-Z가-힣* ]", "");
                            if (i == 0) {
                                food.setMin(Integer.parseInt(temp));
                            } else if (i == 1) {
                                food.setMax(Integer.parseInt(temp));
                            }
                            i++;

                        }
                    }
                } else {
                    // NAME
                    String name = cutEx[0].replaceAll(" ", "");
                    food.setName(name);

                    //TEMPERTURE
                    String[] temps = cutEx[1].split("~");

                    if (temps.length == 1) {
                        String minS = temps[0].replaceAll("[a-zA-Z가-힣* ]", "");
                        food.setMin(Integer.parseInt(minS));
                        //max value
                        food.setMax(185);

                    } else if (temps.length == 2) {
                        int i = 0;
                        for (String temp : temps) {
                            temp = temp.replaceAll("[a-zA-Z가-힣* ]", "");
                            if (i == 0) {
                                food.setMin(Integer.parseInt(temp));
                            } else if (i == 1) {
                                food.setMax(Integer.parseInt(temp));
                            }
                            i++;
                        }
                    }
                }

            } else if (title.contains(":")) {
                String[] cutEx = title.split(":");
                // NAME
                String name = cutEx[0].replaceAll(" ", "");
                food.setName(name);

                //TEMPERTURE
                String[] temps = cutEx[1].split("~");

                if (temps.length == 1) {
                    String minS = temps[0].replaceAll("[^0-9]", "");
                    food.setMin(Integer.parseInt(minS));
                    //max value
                    food.setMax(185);

                } else if (temps.length == 2) {
                    int i = 0;
                    for (String temp : temps) {
                        temp = temp.replaceAll("[a-zA-Z가-힣* ]", "");
                        if (i == 0) {
                            food.setMin(Integer.parseInt(temp));
                        } else if (i == 1) {
                            food.setMax(Integer.parseInt(temp));
                        }
                        i++;
                    }
                }
            }

        } else {
            ///nothing
            //if title="" then nothing
        }
        return food;
    }

}
