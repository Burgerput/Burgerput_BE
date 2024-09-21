package burgerput.project.zenput.Services.manager.mgrList;

import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.domain.MgrList;
import burgerput.project.zenput.repository.mgrList.MgrListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MgrListServiceImpl implements MgrListService{

    private final PrintData printData;
    private final MgrListRepository mgrListRepository;

    @Override
    public ArrayList<Map> showMgrList() {
        //show MachineList from the Machine DB
        //[id, JSON(MAP)] 으로 리턴
        ArrayList<Map> maps = printData.mgrList();
        return maps;
    }

    @Override
    public void deleteMgrList(ArrayList<Map> param) {
        for (Map<String, String> mgrMap : param) {
            MgrList mgrList = new MgrList();
            mgrList.setId(Integer.parseInt(mgrMap.get("id")));
            mgrList.setMgrName(mgrMap.get("mgrname"));

            mgrListRepository.delete(mgrList);
        }
    }

    @Override
    public void addMgrList(ArrayList<Map> param) {
        for (Map<String, String> mgrMap : param) {
            MgrList mgrList = new MgrList();

            mgrList.setMgrName(mgrMap.get("mgrname"));

            mgrListRepository.save(mgrList);
        }
    }
}
