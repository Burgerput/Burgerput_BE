package burgerput.project.zenput.Services.manager.food;

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
public class SelectFoodServiceImpl implements SelectFoodService{

    private final SaveData saveData;
    private final PrintData printData;

    @Override
    public ArrayList<Map> showFood() {
        ArrayList<Map> result = printData.zenputFood();
        return result;

    }

    @Override
    public void saveSelected(ArrayList<Map> param) {
        //log.info("what is id={},", map.get("id"));
        //table의 내용을 전부 지웠다가 다시 저장
        log.info("Selected Food param ={}", param.toString());
        saveData.customFoodDataSave(param);
    }
}
