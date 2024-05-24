package burgerput.project.zenput.Services.loadData.zenputLoading;


import burgerput.project.zenput.domain.Machine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class LoadingAndEnterMethodTest {
    MachineLoadingAndEnterZenput machineLoadingAndEnterZenput = new MachineLoadingAndEnterZenputV2T();
    FoodLoadingAndEnterZenput foodLoadingAndEnterZenput = new FoodLoadingAndEnterZenputV2T();

    @Test
    @DisplayName("machineLoading")
    public void machineLoading(){
        try {
            Map<Integer, Machine> machine = machineLoadingAndEnterZenput.getInfo();
            log.info(machine.toString());
        } catch (Exception e) {


        }


    }

}
