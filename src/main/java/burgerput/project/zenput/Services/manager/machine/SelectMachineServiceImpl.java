package burgerput.project.zenput.Services.manager.machine;

import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.utils.saveData.SaveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SelectMachineServiceImpl implements SelectMachineService{

    private final PrintData printData;
    private final SaveData saveData;

    @Override
    public ArrayList<Map> showMachine() {
        //show MachineList from the Machine DB
        //[id, JSON(MAP)] 으로 리턴
        ArrayList<Map> machineList = printData.zenputMachine();

        log.info("machineList ={}", machineList);

        return machineList;
    }

    @Override
    public void saveSelected(ArrayList<Map> param) {
//        ArrayList<Map> result = printData.zenputMachine();

        log.info("Selected Machine param ={}", param.toString());
        //임시로 다지우고 시작 -> 변경해야하는 로직
        saveData.customMachineDataSave(param);
    }
}
