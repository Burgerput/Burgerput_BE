package burgerput.project.zenput.Services.movePage;

import burgerput.project.zenput.Config;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Map;

@SpringBootTest
@Import(Config.class)
@Slf4j
class MovePageServiceV1Test {

    @Autowired
    private ZenputAccountRepository zenputAccountRepository;
    @Autowired
    private FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    @Autowired
    private MachineLoadingAndEnterZenput machineLoadingAndZenput;

    @Autowired
    private MovePageServiceV1 move;


    @Test
    @DisplayName("goto List")
    void gotoList() {
        try{
            // 시작 시간 측정
            long startTime = System.nanoTime();

            move.gotoListWithLogin();

            long endTime = System.nanoTime();

            long executionTime = endTime - startTime;

            log.info("Execution time in milliseconds: " + (executionTime / 1000000));

        }catch(Exception e){
            log.info("Excpetion ={}", e);

        }

    }

    @Test
    @DisplayName("[PM] foodList click")
    void pmFoodlistClick() {
        long startTime = System.nanoTime();

        move.clickPmFood();
        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        log.info("Execution time in milliseconds: " + (executionTime / 1000000));

    }
    @Test
    @DisplayName("food and machine loading")
    void loadingTest(){
        try {
            Map<Integer, Food> info = foodLoadingAndEnterZenput.getInfo();
            log.info("food info = {}", info.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Map<Integer, Machine> info1 = machineLoadingAndZenput.getInfo();
            log.info("machine info = {}", info1.toString());
        } catch (Exception e) {

            throw new RuntimeException(e);
        }



    }

    @Test
    @DisplayName("[AM] foodList click")
    void amFoodlistClick() {
        try{
            move.clickAmFood();
        }catch(Exception e){
            log.info(e.toString());
        }

    }
    @Test
    @DisplayName("[PM] MachineList click")
    void pmMachinelistClick() {
        move.clickPmMachine();

    }

    @Test
    @DisplayName("[AM] MachineList click")
    void amMachinelistClick() {
        move.clickPmMachine();

    }

}