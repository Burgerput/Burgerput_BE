package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
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
import static burgerput.project.zenput.Const.MACHINEURL;

@Slf4j
@RequiredArgsConstructor
//Service
public class MachineLoadingAndEnterZenputV1 implements MachineLoadingAndEnterZenput {
    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final MachineRepository machineRepository;

    @Override
    public Map<Integer, Machine> getInfo() {

        Map<Integer, Machine> result = new LinkedHashMap<>();

        System.setProperty("java.awt.headless", "false");

        try {
            //test를 위해 PM으롭 변경한다. 원래 AM임
            WebDriver driver = movePageService.clickPmMachine();

            //==============================Scrape LOGIC START============================
            //li class group
            List<WebElement> fields = driver.findElements(By.className("form-field"));


            if (fields.size() == 0) {
                Machine machine = new Machine();
                machine.setId(-1);
                machine.setName("no");
                machine.setMin(0);
                machine.setMax(0);
                result.put(machine.getId(), machine);
            } else {

                for (WebElement field : fields) {
                    Machine contents = extractIdTitle(field);
                    if (contents.getName() == null) {
                        //if map is empty then not save the data
                    } else {
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
        return result;
    }


    @Override
    public void sendValue(String param) {

        //choose am/pm list Start ==============================

        //selenium enter logic start ========================================
        System.setProperty("java.awt.headless", "false");

        WebDriver driver = null;

        try {
            JSONObject paramO = new JSONObject(param);

            if (paramO.get("time").toString().equals("AM")) {
                driver = movePageService.clickAmMachine();

            } else if(paramO.get("time").toString().equals("PM")){
                driver = movePageService.clickPmMachine();

            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            // Enter the value

            // 1. Enter Manager Name
            //a. getManager info from json
            String mgrName = paramO.get("mgrname").toString();

            log.info("manager name = {}", mgrName);
            //b. Enter the manager textbox
            WebElement managerField = driver.findElement(By.id("field_1"));

            WebElement textarea = managerField.findElement(By.tagName("textarea"));

            textarea.click();
            textarea.sendKeys(mgrName);
            Thread.sleep(30);


            //li class group
            List<WebElement> fields = driver.findElements(By.className("form-field"));

            //dummyStore
            ArrayList<Map<String, String>> dummyStore = dummyStoreMaker();
            log.info("dummyStore result ={}", dummyStore);

            //dummyMap changed
            ArrayList<Map> customMachineMap = myJsonParser.jsonStringToArrayList(param);

            for (Map<String, String> customMap : customMachineMap) {
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
        List<Machine> allMachine = machineRepository.findAll();

        for (Machine machine : allMachine) {
            Map<String, String> tempMap = new LinkedHashMap<>();
            tempMap.put("id", Integer.toString(machine.getId()));
            tempMap.put("temp", "999");
            tempMap.put("name", machine.getName());

            result.add(tempMap);
        }
        return result;
    }

    private void enterValue(WebElement field, ArrayList<Map<String, String>> machineMap) {

        try {
            //extract vaild id field
            WebElement input = field.findElement(By.tagName("input"));

            String id = input.getAttribute("field_id");

            //탐침 표면온도계의 경우 없을때 999를 두 번 입력해야됨 id로 한번 wfd-id로 한 번
            for (Map<String, String> customMap : machineMap) {
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
    public Machine extractIdTitle(WebElement field) {
        Machine machine = new Machine();
        try {
            WebElement input = field.findElement(By.tagName("input"));
            WebElement fieldTitle = field.findElement(By.className("field_title"));

            String id = input.getAttribute("field_id");

            if (Integer.parseInt(id) == 0) {
                return new Machine();
            } else {
                //if getText not works use this "innerText"
                String title = fieldTitle.getAttribute("innerText");

                if (title.contains("F")) {
                    machine = extractTitle(title);
                    machine.setId(Integer.parseInt(id));
                }
            }

        } catch (Exception e) {
            log.info("Error LoadMachine={}", e.toString());
        }
        return machine;
    }

    private Machine extractTitle(String title) {

        //setName and Temperture
        Machine machine = new Machine();


        if (!title.equals("")) {
            String[] cutEx = title.split(":");
            // NAME
            String name = cutEx[0].replaceAll(" ", "");
            machine.setName(name);

            //TEMPERTURE
            String[] temps = cutEx[1].split("~");

            if (temps.length == 1) {
                String minS = temps[0].replaceAll("[a-zA-Z가-힣* ]", "");
                machine.setMin(Integer.parseInt(minS));
                //max value

                //핸드싱크, 3조 싱크칸 max온도는 135로 지정
                switch (name) {
                    case "3조싱크칸(세척온수)":
                        machine.setMax(135);
                        break;

                    case "핸드싱크(온수)":
                        machine.setMax(135);
                        break;

                    default:
                        machine.setMax(185);
                        break;
                }
//                log.info("name ={}", machine);


            } else if (temps.length == 2) {
                int i = 0;
                for (String temp : temps) {
                    //정규표현식 - 영문 한글 * 을모두 ""로 변경한다.
                    temp = temp.replaceAll("[a-zA-Z가-힣* ]", "");
                    if (i == 0) {
                        machine.setMin(Integer.parseInt(temp));
                    } else if (i == 1) {
                        machine.setMax(Integer.parseInt(temp));
                    }
                    i++;
                }
            }
        } else {
            ///nothing
            //if title="" then nothing
        }
        return machine;
    }

}
