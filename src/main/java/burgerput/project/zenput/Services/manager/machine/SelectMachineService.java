package burgerput.project.zenput.Services.manager.machine;

import java.util.ArrayList;
import java.util.Map;

public interface SelectMachineService {
    public ArrayList<Map> showMachine();

    void saveSelected( ArrayList<Map> param);

}
