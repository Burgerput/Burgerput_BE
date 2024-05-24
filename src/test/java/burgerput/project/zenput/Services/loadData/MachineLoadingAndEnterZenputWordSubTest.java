package burgerput.project.zenput.Services.loadData;

import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
class MachineLoadingAndEnterZenputWordSubTest {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ZenputAccountRepository zenputAccountRepository;


//    MachineLoadingAndEnterZenput machineLoadingAndEnterZenput = new MachineLoadingAndEnterZenputV2Test(movePageService,myJsonParser, machineRepository);
    @Test
    @DisplayName("정규표현식")
    public void regix() {
        String str = "최소 -140F 이상 *";
        String s = str.replaceAll("[a-zA-Z가-힣* ]", "");

        log.info("s={}", s);
    }

    @Test
    @DisplayName("getInfoOptimized")
    public void opti() {
//        Map<Integer, Machine> info = machineLoadingAndEnterZenput.getInfo();

//        log.info(info.toString());
    }
}