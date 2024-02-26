package burgerput.project.zenput.repository.zenputPage;

import lombok.extern.slf4j.Slf4j;
import burgerput.project.zenput.domain.Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//@Repository
@Slf4j
public class MemoryMachineRepositoryV1 implements MemoryMachineRepository {
    //순서대로 넣고싶을 때
    public Map<Integer, Machine> store = new LinkedHashMap<>();

    @Override
    public void save(ArrayList<Map> data) {
        Machine machine = new Machine();

        for (Map param : data) {
            Integer id = Integer.valueOf(param.get("id").toString());
            String name = (String) param.get("name");
            Integer min = Integer.valueOf(param.get("min").toString());
            Integer max = Integer.valueOf(param.get("max").toString());

            //put data into Machine Entity
            machine.setId(id);
            machine.setName(name);
            machine.setMin(min);
            machine.setMax(max);

            //save in the memory store
            store.put(machine.getId(), machine);
            machine = new Machine();
        }
        log.info("stored Dat ={}", store);
    }

    @Override
    public ArrayList<Object> getList() {
        //save Machine Entity into ArrayList
        log.info("stroed ={}", store);

        return parseingData(store);
    }

    //PARSING DATA MACHINE TO MAP
    private  ArrayList<Object> parseingData(Map<Integer, Machine> store) {
        ArrayList<Object> result = new ArrayList<>();

        for (Integer key : store.keySet()) {
            Map<String, String> data = new HashMap<>();

            Machine machine = store.get(key);

            data.put("id", String.valueOf(key));
            data.put("name", machine.getName().toString());
            data.put("min", String.valueOf(machine.getMin()));
            data.put("max", String.valueOf(machine.getMax()));


            result.add(data);
        }

        return result;

    }
}
