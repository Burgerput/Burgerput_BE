package burgerput.project.zenput.web.manager.machine;

import burgerput.project.zenput.Services.manager.machine.SelectMachineService;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.utils.saveData.SaveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SelectMachineController {

    private final SelectMachineService selectmachineService;

    @GetMapping("back/select/machines") //기기 목록 출력
    @ResponseBody
    public ArrayList<Map> showMachineList() {

        return selectmachineService.showMachine();
    }

    @PostMapping("back/select/machines")//선택한 기기의 값
    @ResponseBody
    public void selected(@RequestBody ArrayList<Map> param) {
        selectmachineService.saveSelected(param);
    }
}

