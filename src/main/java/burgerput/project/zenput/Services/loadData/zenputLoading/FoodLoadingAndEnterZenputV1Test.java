package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.*;

import static burgerput.project.zenput.Const.*;

@Slf4j
@RequiredArgsConstructor
//@Service
public class FoodLoadingAndEnterZenputV1Test implements FoodLoadingAndEnterZenput {
    //Using saved html file data

    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final FoodRepository foodRepository;

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
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            //==============================Scrape LOGIC START============================

            //GO TO PAGE
            driver.get(FOODURL);

            //select elements
            // ul> li > div.field_title + div> div> input
            //field_title 이랑 input의 field_id 필요
            //li class form-field 에서 text랑 input 필드 id값 가져오기

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
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


            // Enter the value

            // 1. Enter Manager Name
            //a. getManager info from json
            JSONObject paramO = new JSONObject(param);
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
