package burgerput.project.zenput.web.manager.mgrList;

import burgerput.project.zenput.Services.printData.PrintData;
import burgerput.project.zenput.domain.MgrList;
import burgerput.project.zenput.repository.mgrList.MgrListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MgrListController {
    private final PrintData printData;

    private final MgrListRepository mgrListRepository;

    //mgrlist print
    @GetMapping("back/managers") //관리자 목록 출력
    @ResponseBody
    public ArrayList<Map> showMgrList() {
        //show MachineList from the Machine DB
        //[id, JSON(MAP)] 으로 리턴
        ArrayList<Map> maps = printData.mgrList();
        return maps;
    }


    
    //delete mgr
    @PostMapping("back/manager") //[{"mgrname":"김뚝딱"}] map in the ArrayList
    @ResponseBody
    public void deleteMgrlist(@RequestBody ArrayList<Map> param) {
        for (Map<String, String> mgrMap : param) {
            MgrList mgrList = new MgrList();
            mgrList.setId(Integer.parseInt(mgrMap.get("id")));
            mgrList.setMgrName(mgrMap.get("mgrname"));

            mgrListRepository.delete(mgrList);
        }

    }

    @PostMapping("back/managers") //[{"mgrname":"김뚝딱"}] map in the ArrayList
    @ResponseBody
    public void addMgrList(@RequestBody ArrayList<Map> param) {
        for (Map<String, String> mgrMap : param) {
            MgrList mgrList = new MgrList();

            mgrList.setMgrName(mgrMap.get("mgrname"));

            mgrListRepository.save(mgrList);
        }
    }

}
