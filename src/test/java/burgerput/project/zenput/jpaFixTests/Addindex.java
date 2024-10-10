package burgerput.project.zenput.jpaFixTests;


import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j

public class Addindex {

    @Autowired
    private MachineRepository machineRepository;
    @Test
    @Transactional
    public void addIndexTest(){
        Machine machine1 = new Machine();
        machine1.setIndexValue(1);
        machine1.setId(1000);
        machine1.setName("A");

        Machine machine2 = new Machine();
        machine2.setIndexValue(2);
        machine2.setId(2000);
        machine2.setName("B");

        Machine machine3 = new Machine();
        machine3.setIndexValue(3);
        machine3.setId(3000);
        machine3.setName("C");

        Machine machine4 = new Machine();
        machine4.setIndexValue(4);
        machine4.setId(4000);
        machine4.setName("D");

        Machine machine5 = new Machine();
        machine5.setIndexValue(5);
        machine5.setId(5000);
        machine5.setName("E");


        //1 5 3 4 2 순으로 뽑음 이제 이걸 index순으로 정ㄹ려해서 다시 가져와보자
        machineRepository.save(machine1);
        machineRepository.save(machine5);
        machineRepository.save(machine3);
        machineRepository.save(machine4);
        machineRepository.save(machine2);

        //index순으로 정렬해서 다시 가져와보기

//        List<Machine> foundMachines = machineRepository.findAllByOrderByIndexValueAsc();
//        for(Machine machine : foundMachines){
//            log.info(machine.getName());
//        }


    }


}
