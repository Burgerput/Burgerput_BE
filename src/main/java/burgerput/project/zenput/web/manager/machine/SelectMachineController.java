package burgerput.project.zenput.web.manager.machine;

import burgerput.project.zenput.Services.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.saveData.SaveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins="https://localhost:3000")
public class SelectMachineController {

    private final PrintData printData;
    private final SaveData saveData;

//    SaveData saveData;

    @GetMapping("back/select/machines") //기기 목록 출력
    @ResponseBody
    public ArrayList<Map> showMachineList() {
        //show MachineList from the Machine DB
        //[id, JSON(MAP)] 으로 리턴
        ArrayList<Map> machineList = printData.zenputMachine();

        log.info("machineList ={}", machineList);

        return machineList;
    }

    @PostMapping("back/select/machines")//선택한 기기의 값
    @ResponseBody
    public void selected(@RequestBody ArrayList<Map> param) {
//        ArrayList<Map> result = printData.zenputMachine();

        log.info("Selected Machine param ={}", param.toString());
        //임시로 다지우고 시작 -> 변경해야하는 로직
        saveData.customMachineDataSave(param);
    }
}

