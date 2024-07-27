package burgerput.project.zenput.Services.loadData.zenputLoading;


import burgerput.project.zenput.Config;
import burgerput.project.zenput.TestConfiguration;
import burgerput.project.zenput.domain.Machine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

        ArrayList<JSONObject> contents =new ArrayList<>();
        JSONObject tempMap = new JSONObject();

        tempMap.put("id","2");
        tempMap.put("name","온도계(탐침1)" );
        tempMap.put("temp","32");

        JSONObject tempMap2 = new JSONObject();
        tempMap2.put("id","117");
        tempMap2.put("name","워크인프리저");
        tempMap2.put("temp","-3");

        JSONObject tempMap3 = new JSONObject();
        tempMap3.put("id","20");
        tempMap3.put("name","스페셜티프리저1");
        tempMap3.put("temp","-2");

        contents.add(tempMap);
        contents.add(tempMap2);
        contents.add(tempMap3);

        machineMap.put("customMachine", contents.toString());

        machineMap.put("time","PM");

        log.info("map result = {}", machineMap.toString());

        try {
            Map<String, String> machine = machineLoadingAndEnterZenput.sendValueV2(machineMap.toString());
            log.info(machine.toString());
        } catch (Exception e) {
            log.info(e.toString());

        }
    }

}
