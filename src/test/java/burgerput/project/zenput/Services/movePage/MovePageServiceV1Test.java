package burgerput.project.zenput.Services.movePage;

import burgerput.project.zenput.Config;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(Config.class)
@Slf4j
class MovePageServiceV1Test {

    @Autowired
    private ZenputAccountRepository zenputAccountRepository;

    @Autowired
    private MovePageServiceV1 move;


    @Test
    @DisplayName("goto List")
    void gotoList() {
        try{
            move.gotoListWithLogin();
        }catch(Exception e){
            log.info("Excpetion ={}", e);

        }

    }

    @Test
    @DisplayName("[PM] foodList click")
    void pmFoodlistClick() {
        move.clickPmFood();

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