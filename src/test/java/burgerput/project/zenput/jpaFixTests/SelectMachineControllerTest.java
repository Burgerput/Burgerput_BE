package burgerput.project.zenput.jpaFixTests;

import burgerput.project.zenput.Services.printDatafromDB.PrintData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
@Slf4j
class SelectMachineControllerTest {

    @Autowired
    PrintData printData;
    @Test
    @DisplayName("MachineList")
    void showMachineList() {
        //모든 데이터 출력
        ArrayList<Map> machineList = printData.zenputMachine();
        log.info(machineList.toString());
    }

    @Test
    @DisplayName("foodList")
    void showFoodList(){
        //모든 food 데이터 출력
        ArrayList<Map> foodList = printData.zenputFood();
        log.info(foodList.toString());

    }

    @Test
    @DisplayName("customMachine")
    void showCustomMachine(){
        ArrayList<Map> customMachine = printData.customMachine();
        log.info(customMachine.toString());
    }

    @Test
    @DisplayName("customFood")
    void showCustomFood(){
        ArrayList<Map> customFood = printData.customFood();
        log.info(customFood.toString());
    }

    @Test
    @DisplayName("customCheatFood")
    void customCheatFood(){
        ArrayList<Map> customCheatFood = printData.customCheatFood();
        log.info(customCheatFood.toString());
    }

    @Test
    @DisplayName("customCheatMachine")
    void customCheatMachine(){

        ArrayList<Map> customCheatMachine = printData.customCheatMachine();
        log.info(customCheatMachine.toString());
    }
}