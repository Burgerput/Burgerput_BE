package burgerput.project.zenput.Services.loadData.zenputLoading;


import burgerput.project.zenput.Config;
import burgerput.project.zenput.TestConfiguration;
import burgerput.project.zenput.domain.Machine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Import(TestConfiguration.class)
@Slf4j
public class LoadingAndEnterMethodTest {

    @Autowired
    MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;

    @Autowired
    FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;


    @Test
    @DisplayName("Machine Enter")
    public void machineLoading(){

        JSONObject machineMap = new JSONObject();
        machineMap.put("mgrname", "신호준");

        JSONArray contents =new JSONArray();
        JSONObject tempMap = new JSONObject();

        tempMap.put("id","2");
        tempMap.put("name","온도계(탐침1)" );
        tempMap.put("temp","32");

        JSONObject tempMap4 = new JSONObject();
        tempMap4.put("id","54");
        tempMap4.put("name","온도계(탐침2)" );
        tempMap4.put("temp","31");

        JSONObject tempMap5 = new JSONObject();
        tempMap5.put("id","56");
        tempMap5.put("name","온도계(표면1)" );
        tempMap5.put("temp","33");


        JSONObject tempMap2 = new JSONObject();
        tempMap2.put("id","117");
        tempMap2.put("name","워크인프리저");
        tempMap2.put("temp","-3");

        JSONObject tempMap3 = new JSONObject();
        tempMap3.put("id","20");
        tempMap3.put("name","스페셜티프리저1");
        tempMap3.put("temp","-2");

        contents.put(tempMap);
        contents.put(tempMap4);
        contents.put(tempMap5);
        contents.put(tempMap2);
        contents.put(tempMap3);

        machineMap.put("customMachine", contents);

        machineMap.put("time","PM");

        log.info("map result = {}", machineMap.toString(2));

        try {
            Map<String, String> machine = machineLoadingAndEnterZenput.sendValueV2(machineMap.toString());
            log.info(machine.toString());
        } catch (Exception e) {
            log.info(e.toString());
        }
    }


    @Test
    @DisplayName("Food Loading")
    public void foodLoaaing(){

        JSONObject foodMap = new JSONObject();
        foodMap.put("mgrname", "신호준");

        JSONArray contents =new JSONArray();
        JSONObject tempMap = new JSONObject();

        tempMap.put("id","2");
        tempMap.put("name","온도계(탐침1)" );
        tempMap.put("temp","32");

        JSONObject tempMap4 = new JSONObject();
        tempMap4.put("id","54");
        tempMap4.put("name","온도계(탐침2)" );
        tempMap4.put("temp","31");

        JSONObject tempMap5 = new JSONObject();
        tempMap5.put("id","56");
        tempMap5.put("name","온도계(표면1)" );
        tempMap5.put("temp","33");


        JSONObject tempMap2 = new JSONObject();
        tempMap2.put("id","117");
        tempMap2.put("name","워크인프리저");
        tempMap2.put("temp","-3");

        JSONObject tempMap3 = new JSONObject();
        tempMap3.put("id","20");
        tempMap3.put("name","스페셜티프리저1");
        tempMap3.put("temp","-2");

        contents.put(tempMap);
        contents.put(tempMap4);
        contents.put(tempMap5);
        contents.put(tempMap2);
        contents.put(tempMap3);

        foodMap.put("customMachine", contents);

        foodMap.put("time","PM");

        log.info("map result = {}", foodMap.toString(2));

        try {
            Map<String, String> machine = foodLoadingAndEnterZenput.sendValueV2(foodMap.toString());
            log.info(machine.toString());
        } catch (Exception e) {
            log.info(e.toString());
        }


    }
}
