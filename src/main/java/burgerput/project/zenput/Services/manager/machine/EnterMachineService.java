package burgerput.project.zenput.Services.manager.machine;

import java.util.ArrayList;
import java.util.Map;

public interface EnterMachineService {
    public Map<String, ArrayList<Map>> enterMachine();

    public Map<String, String> submitZenputMachine(String param);



}
