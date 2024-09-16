package burgerput.project.zenput.web.manager.mgrList;

import burgerput.project.zenput.Services.manager.mgrList.MgrListService;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
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
    private final MgrListService mgrListService;

    //mgrlist print
    @GetMapping("back/managers") //관리자 목록 출력
    @ResponseBody
    public ArrayList<Map> showMgrList() {
        return mgrListService.showMgrList();
    }

    //delete mgr
    @PostMapping("back/manager") //[{"mgrname":"김뚝딱"}] 으로 들어온 정보를 받아서 삭제함
    @ResponseBody
    public void deleteMgrlist(@RequestBody ArrayList<Map> param) {
        mgrListService.deleteMgrList(param);
    }

    @PostMapping("back/managers") //[{"mgrname":"김뚝딱"}] 으로 들어온 정보를 저장함
    @ResponseBody
    public void addMgrList(@RequestBody ArrayList<Map> param) {
        mgrListService.addMgrList(param);
    }

}
