package burgerput.project.zenput.Services.movePage;

import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MovePageServiceV1Test {

    @Autowired
    private ZenputAccountRepository zenputAccountRepository;

    MovePageServiceV1 move = new MovePageServiceV1(zenputAccountRepository);

    @Test
    @DisplayName("goto List")
    void gotoList() {
        move.gotoListWithLogin();

    }

    @Test
    @DisplayName("[PM] foodList click")
    void pmFoodlistClick() {
        move.clickPmFood();

    }

    @Test
    @DisplayName("[AM] foodList click")
    void amFoodlistClick() {
        move.clickAmFood();
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