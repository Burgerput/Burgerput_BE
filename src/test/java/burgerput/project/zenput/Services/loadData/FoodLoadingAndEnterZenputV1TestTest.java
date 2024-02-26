package burgerput.project.zenput.Services.loadData;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.jsonObject.MyJsonParserV1;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.Services.movePage.MovePageServiceV1;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static burgerput.project.zenput.Const.DRIVERLOCATION;
import static burgerput.project.zenput.Const.FOODURL;

@Slf4j
//@ExtendWith(SpringExtension.class)
@DataJpaTest
class FoodLoadingAndEnterZenputV1TestTest {

    @Autowired
    private ZenputAccountRepository zenputAccountRepository;
    private final MovePageService movePageService = new MovePageServiceV1(zenputAccountRepository);
    private final MyJsonParser myJsonParser = new MyJsonParserV1();

    @Autowired
    private FoodRepository foodRepository;
    private burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV1Test food = new burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV1Test(movePageService, myJsonParser, foodRepository);

    @Test
    @DisplayName("LOADING FOOD DATA")
    public void loadingFood() {
        Map<Integer, Food> info = food.getInfo();

        for (Integer integer : info.keySet()) {

            log.info("data  ={}", info.get(integer));
        }

    }

    @Test
    @DisplayName("Sasve the Food to DB")
    public void saveFood() {
        Map<Integer, Food> info = food.getInfo();

    }

    @Test
    @DisplayName("paste the Data to Db")
    public void enterlogic() {

        String machineParma = "{\"customMachine\":[{\"id\":\"2\",\"name\":\"온도계(탐침1)\",\"temp\":\"32\"},{\"id\":\"56\",\"name\":\"온도계(표면1)\",\"temp\":\"32\"},{\"id\":\"18\",\"name\":\"미트프리저\",\"temp\":\"-2\"},{\"id\":\"20\",\"name\":\"스페셜티프리저1\",\"temp\":\"-2\"},{\"id\":\"22\",\"name\":\"스페셜티프리저2\",\"temp\":\"-3\"},{\"id\":\"58\",\"name\":\"냉장유닛(콜라냉장고)\",\"temp\":\"36\"},{\"id\":\"60\",\"name\":\"아이스크림(호퍼)\",\"temp\":\"38\"},{\"id\":\"26\",\"name\":\"메인프라이어1\",\"temp\":\"352\"},{\"id\":\"32\",\"name\":\"멀티프라이어1\",\"temp\":\"362\"},{\"id\":\"62\",\"name\":\"멀티프라이어2\",\"temp\":\"364\"},{\"id\":\"39\",\"name\":\"3조싱크칸(세척온수)\",\"temp\":\"121\"},{\"id\":\"66\",\"name\":\"3조싱크칸(소독수온수)\",\"temp\":\"100\"},{\"id\":\"64\",\"name\":\"핸드싱크(온수)\",\"temp\":\"101\"},{\"id\":\"44\",\"name\":\"PHU내제품온도1\",\"temp\":\"150\"},{\"id\":\"46\",\"name\":\"PHU내제품온도2\",\"temp\":\"172\"},{\"id\":\"48\",\"name\":\"PHU내제품온도3\",\"temp\":\"180\"},{\"id\":\"76\",\"name\":\"리세스힛슈트(구형)1\",\"temp\":\"171\"},{\"id\":\"72\",\"name\":\"딜리버리픽업스테이션1\",\"temp\":\"171\"},{\"id\":\"74\",\"name\":\"딜리버리픽업스테이션2\",\"temp\":\"172\"}],\"mgrname\":\"김똥똥\",\"time\":\"AM\"}";
        String foodParam = "{\"customFood\":[{\"id\":\"351\",\"name\":\"너겟킹(NuggetKingFULLYCOOKED)\",\"temp\":\"180\"},{\"id\":\"34\",\"name\":\"롱치킨패티(LongChickenpattyFULLYCOOKED)\",\"temp\":\"180\"},{\"id\":\"140\",\"name\":\"새우패티(Shrimppatty)\",\"temp\":\"180\"},{\"id\":\"1077\",\"name\":\"바삭킹(BasakKing)\",\"temp\":\"180\"},{\"id\":\"1140\",\"name\":\"코울슬로(COLESLAW)\",\"temp\":\"36\"},{\"id\":\"1133\",\"name\":\"양상추(LETTUCE)\",\"temp\":\"80\"},{\"id\":\"1126\",\"name\":\"토마토(TOMATOES)\",\"temp\":\"72\"},{\"id\":\"1119\",\"name\":\"양파(ONIONS)\",\"temp\":\"68\"},{\"id\":\"1168\",\"name\":\"탄산음료(SOFTDRINK)\",\"temp\":\"38\"}],\"mgrname\":\"김뿡뿡\",\"time\":\"AM\"}";


        sendValue(foodParam);
        sendValue(machineParma);

    }



    //Logic
    public Map<Integer, Food> getInfo() {//get from am info
        Map<Integer, Food> result = new LinkedHashMap<>();

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