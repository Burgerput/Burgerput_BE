package burgerput.project.zenput.Services.manager.mgrList;

import java.util.ArrayList;
import java.util.Map;

public interface MgrListService {
    public ArrayList<Map> showMgrList();

    void deleteMgrList(ArrayList<Map>  param);

    void addMgrList(ArrayList<Map> param);

}
