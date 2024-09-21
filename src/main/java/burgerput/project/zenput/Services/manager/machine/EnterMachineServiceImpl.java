package burgerput.project.zenput.Services.manager.machine;

import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EnterMachineServiceImpl implements EnterMachineService{

    private final PrintData printData;
    private final MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;

    @Override
    public Map<String, ArrayList<Map>> enterMachine() {

        ArrayList<Map> customMachine = printData.customMachine();

        ArrayList<Map> mgrMap = printData.mgrList();
        Map<String, ArrayList<Map>> tempMap = new LinkedHashMap<>();

        tempMap.put("customMachine", customMachine);
        tempMap.put("mgrList", mgrMap);
        return tempMap;
    }

    @Override
    public Map<String, String> submitZenputMachine(String param) {
        log.info("Machine Param ={}", param);
        Map<String, String> resultMap = machineLoadingAndEnterZenput.sendValueV2(param);

        Map<String, String> result = new LinkedHashMap<>();

        result.put("result", resultMap.get("result").toString());

        return result;
    }
}
