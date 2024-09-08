package burgerput.project.zenput.Services.cheatService;

import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.utils.saveData.SaveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service //Spring bean 등록
@Slf4j//로그확인
@RequiredArgsConstructor
@Transactional //DB사용하니까 트랜잭션 붙여줌
public class CheatServiceV1 implements CheatService {

    //DB사용
    private final PrintData printData;
    private final SaveData saveData;

    @Override
    public Map<String, ArrayList<Map>> showCheatFood() {

        ArrayList<Map> maps = printData.customCheatFood();
        ArrayList<Map> mgrMap = printData.mgrList();

        Map<String, ArrayList<Map>> tempMap = new LinkedHashMap<>();

        tempMap.put("customCheatFood", maps);
        tempMap.put("mgrList", mgrMap);

        return tempMap;
    }

    @Override
    public void saveCheatFood(ArrayList<Map> param) {
        saveData.customCheatFoodDataSave(param);
    }

    @Override
    public Map<String, ArrayList<Map>> showCheatMachine() {

        ArrayList<Map> maps = printData.customCheatMachine();
        ArrayList<Map> mgrMap = printData.mgrList();

        Map<String, ArrayList<Map>> tempMap = new LinkedHashMap<>();

        tempMap.put("customCheatMachine", maps);
        tempMap.put("mgrList", mgrMap);

        return tempMap;
    }
    @Override
    public void saveCheatMachine(ArrayList<Map> param) {
        saveData.customCheatMachineDataSave(param);
    }
}
