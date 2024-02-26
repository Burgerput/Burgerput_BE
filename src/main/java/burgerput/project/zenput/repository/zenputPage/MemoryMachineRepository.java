package burgerput.project.zenput.repository.zenputPage;

import java.util.ArrayList;
import java.util.Map;

public interface MemoryMachineRepository {
    public void save(ArrayList<Map> data);

    //It's will be implmented Machine Entity and Food Entity
    public ArrayList<Object> getList();
}
