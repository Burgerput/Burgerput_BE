package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.driverRepository.MachineDriverRepository;
import burgerput.project.zenput.repository.driverRepository.MachineDriverRepositoryV1;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//Service
public class MachineLoadingAndEnterZenputV2 implements MachineLoadingAndEnterZenput {

//Optimize version!
    //Using saved html file data

    private final MovePageService movePageService;
    private final MyJsonParser myJsonParser;
    private final MachineRepository machineRepository;
    private final MachineDriverRepository machineDriverRepository;


    //get info 는 무조건 AM 으로만 받아온다.
    // Only am list
    @Override
    public Map<Integer, Machine> getInfo() {
        log.info("Food Get Info Logic Start f rom MachineLoadingAndEnterZenputV2");

        Map<Integer, Machine> result = new LinkedHashMap<>();

        System.setProperty("java.awt.headless", "false");

        try {
            //test를 위해 pm으로 변경한다.
            WebDriver driver = movePageService.clickAmMachine();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //li class group
            List<WebElement> section = driver.findElements(By.className("form_container_wrapper"));

            if (section.size() == 0) {
                Machine machine = new Machine();
                machine.setId(-1);
                machine.setName("no");
                machine.setMin(0);
                machine.setMax(0);
                result.put(machine.getId(), machine);
            } else {

                for (WebElement fields : section) {
                    List<WebElement> elements = fields.findElements(By.className("form-field"));
                    log.info("SECTION START");

                    for (WebElement field : elements) {
                        String id = field.getAttribute("id");

                        if (id.equals("field_0") | id.equals("field_1") | id.equals("field_84")) {
//                        skip it
                        } else {
                            Machine contents = extractIdTitle(field);
                            if (contents.getName() == null) {
                                //if map is empty then not save the data
                            } else {
                                result.put(contents.getId(), contents);
                            }
                        }
                    }
                }
            }

            log.info("quit the Machine getInfo driver");

            //End process
            driver.close();
            driver.quit();

        } catch (Exception e) {
            log.info("Machine GetInfo Error occurred !");
            log.info(e.toString());
        }
        return result;
    }

    @Override
    public Map<String, String> sendValueV2(String param) {
        Map<String, String> result = new LinkedHashMap<>();

        //choose am/pm list Start ==============================
        WebDriver driver = null;
        //selenium enter logic start ========================================
        System.setProperty("java.awt.headless", "false");
        try {
            // Enter the value
            // 1. Enter Manager Name
            //a. getManager info from json
            JSONObject paramO = new JSONObject(param);
            String mgrName = paramO.get("mgrname").toString();

            String time = paramO.get("time").toString();

            if (time.equals("AM")) {
                driver = movePageService.clickAmMachine();

            } else if (time.equals("PM")) {
                driver = movePageService.clickPmMachine();
                log.info("ENTER PM Machine");

            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //b. Enter the manager textbox
            WebElement managerField = driver.findElement(By.id("field_1"));

            WebElement textarea = managerField.findElement(By.tagName("textarea"));

            textarea.click();
            textarea.sendKeys(mgrName);
            Thread.sleep(30);

            //dummyStore
            ArrayList<Map<String, String>> dummyStore = dummyStoreMaker();
            log.info("dummyStore result ={}", dummyStore);

            //dummyMap changed
            ArrayList<Map> customMachineMap = myJsonParser.jsonStringToArrayList(param);


            ArrayList<Map<String, String>> maps = finalMapMaker(customMachineMap, dummyStore);

            log.info("dummyMap final ={}", maps);
            //li class group
            //Enter customValueStart ===============================
            List<WebElement> section = driver.findElements(By.className("form_container_wrapper"));

            for (WebElement fields : section) {
                List<WebElement> elements = fields.findElements(By.className("form-field"));

                for (WebElement field : elements) {
                    //extract vaild id list logic
                    String id = field.getAttribute("id");
                    if (id.equals("field_0") | id.equals("field_1") | id.equals("field_84")) {

                    }else{
                        enterValue(field, dummyStore,result);
                    }

                }
            }

            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs((OutputType.FILE));
            File file = new File("/home/ubuntu/burgerput/img/zenputMachine"+ LocalDate.now()+".png");
            FileUtils.copyFile(screenshotAs, file);

            WebElement button = driver.findElement(By.xpath("//*[@id=\"submit_form\"]"));
            button.click();

            log.info("quit the Driver ()");
            driver.quit();

            //성공했을 시에 driver 값 같이 리턴
            result.put("result", "true");

            //MachineDriverRepository에 저장
//            machineDriverRepository.setDriver(driver);

        }  catch (ElementNotInteractableException e) {
            //에러나면 false 리턴
            log.info("ElementNotInteractableExcpetion Occured form MachineLoadingAndEnterZenputV2");
            log.info(e.toString());

            //에러난 드라이버 종료
            driver.quit();
            return result;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("making img File exception from MachineLoadingAndEnterZenputV2");
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void sendValue(String param) {
    }

    private ArrayList<Map<String,String>> finalMapMaker(ArrayList<Map> customMachineMap, ArrayList<Map<String, String>> dummyStore) {
        //implant customMap value to dummyStore

        ArrayList<Map> deletekey = new ArrayList<>();
        for (Map<String, String> customMap : customMachineMap) {
            //id와 이름이 똑같으면 이거로 temp값을 변경하기 dummyStore의 temp값을 변경한다.
            String temp = customMap.get("temp");
            String id = customMap.get("id");
            if ((id.equals("2") || id.equals("54") || id.equals("56")) && !temp.equals("999")) {

                Map<String, String> tmpMap = new LinkedHashMap<>();
                tmpMap.put("id", Integer.toString(Integer.parseInt(id) + 1));
                tmpMap.put("temp", "999");
                tmpMap.put("name", customMap.get("name"));

                deletekey.add(tmpMap);
            }

            for (Map<String, String> dummyMap : dummyStore) {
                if (dummyMap.get("id").equals(customMap.get("id"))) {
                    log.info("customMap  ={}", customMap);
                    dummyMap.put("temp", customMap.get("temp"));
                }
            }
//delete object from dummystore with delete key map values
            for (Map map : deletekey) {
                dummyStore.remove(map);
            }
        }
        return dummyStore;
    }

    private ArrayList<Map<String, String>> dummyStoreMaker() {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        List<Machine> allMachine = machineRepository.findAll();

        for (Machine machine : allMachine) {
            Map<String, String> tempMap = new LinkedHashMap<>();

            if (machine.getId() == 2 | machine.getId() == 54 | machine.getId() == 56) {
                tempMap.put("id", Integer.toString(machine.getId()));
                tempMap.put("temp", "999");
                tempMap.put("name", machine.getName());

                result.add(tempMap);

                switch (machine.getId()) {
                    case 2:
                        Map<String, String> tempMap2 = new LinkedHashMap<>();

                        tempMap2.put("id", Integer.toString(3));
                        tempMap2.put("temp", "999");
                        tempMap2.put("name", machine.getName());
                        result.add(tempMap2);

                        break;

                    case 54:
                        Map<String, String> tempMap55 = new LinkedHashMap<>();
                        tempMap55.put("id", Integer.toString(55));
                        tempMap55.put("temp", "999");
                        tempMap55.put("name", machine.getName());
                        result.add(tempMap55);

                        break;

                    case 56:
                        Map<String, String> tempMap57 = new LinkedHashMap<>();
                        tempMap57.put("id", Integer.toString(57));
                        tempMap57.put("temp", "999");
                        tempMap57.put("name", machine.getName());
                        result.add(tempMap57);

                        break;
                }
            } else {
                tempMap.put("id", Integer.toString(machine.getId()));
                tempMap.put("temp", "999");
                tempMap.put("name", machine.getName());

                result.add(tempMap);
            }

        }
        return result;
    }

    private void enterValue(WebElement field, ArrayList<Map<String, String>> machineMap, Map<String,String> resultMap) {

        try {

            //extract vaild id field
            WebElement input = field.findElement(By.tagName("input"));

            String id = input.getAttribute("field_id");

            for (int i = 0; i < machineMap.size(); i++) {
                Map<String, String> customMap = machineMap.get(i);
                try {
                    if (id.equals(customMap.get("id"))) {

                        log.info("enter Map info {}", customMap);

                        input.sendKeys(customMap.get("temp"));
                        input.sendKeys(Keys.TAB);
                        Thread.sleep(250);
                        machineMap.remove(i);

                        break;
                    }
                } catch (NullPointerException e) {
                    log.info("error message ={}", e);
                    //do nothing
                } catch (ElementNotInteractableException e) {
                    log.info("ElementNotinteratable Excpetion error");
                    log.info(e.toString());

                    resultMap.put("result", "false");

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
