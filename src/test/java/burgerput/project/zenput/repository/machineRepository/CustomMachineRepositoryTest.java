package burgerput.project.zenput.repository.machineRepository;

import burgerput.project.BurgerputProjectApplication;
import burgerput.project.zenput.Config;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.CustomMachine;
import burgerput.project.zenput.domain.Machine;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
@Import(Config.class)
@ContextConfiguration(classes = BurgerputProjectApplication.class)
class CustomMachineRepositoryTest {

    @Autowired
    private MachineLoadingAndEnterZenput machineLoadiing;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private CustomMachineRepository csMachineRepository;

    @BeforeEach
    @Transactional
    public void initData() {
        //================= save to main Machine
        //init data
        Machine machine1 = new Machine();
        machine1.setId(1);
        machine1.setName("TEST1");
        machine1.setMax(132);
        machine1.setMin(23);

        //init data
        Machine machine2 = new Machine();
        machine2.setId(2);
        machine2.setName("TEST2");
        machine2.setMax(233);
        machine2.setMin(12);

        Machine machine3 = new Machine();
        machine3.setId(3);
        machine3.setName("TEST3");
        machine3.setMax(165);
        machine3.setMin(12);

        Machine machine4 = new Machine();
        machine4.setId(3);
        machine4.setName("TEST4");
        machine4.setMax(172);
        machine4.setMin(64);


        machineRepository.save(machine1);
        machineRepository.save(machine2);
        machineRepository.save(machine3);
        machineRepository.save(machine4);

    }

    @Test
    @DisplayName("Machine save ")
    @Transactional
    public void machineSave() {
        //given
        Map<Integer, Machine> machineList = machineLoadiing.getInfo();
        //ArrayList<Map> foodList = foodLoading.getInfo();

        //SAVE IT
        for (Integer integer : machineList.keySet()) {
            machineRepository.save(machineList.get(integer));
        }

        Iterable<Machine> all = machineRepository.findAll();
        for (Machine machine : all) {
            log.info("machine into ={}", machine);

        }

    }


    @Test
    @DisplayName("update machine")
    @Transactional
    public void update() {
        Optional<Machine> opMachine = machineRepository.findById(1);
        Machine updateParam = opMachine.get();
        updateParam.setId(1);
        updateParam.setName("updated1");
        updateParam.setMax(22);
        updateParam.setMin(11);
        machineRepository.save(updateParam);

        log.info("Repository ={}", machineRepository.findAll());

    }

    //joined table
    @Test
    @DisplayName("Save to customMachine")
    @Transactional
    public void customMachineSave() {
        CustomMachine csMachine1 = new CustomMachine();
        CustomMachine csMachine2 = new CustomMachine();

        csMachine1.setId(1);
        csMachine2.setId(2);

        csMachineRepository.save(csMachine1);
        csMachineRepository.save(csMachine2);

        log.info("csMachine Repository ={}", csMachineRepository.findById(1));

        //CustomMachine DB에 저장된 id를 MachineRepository에 검색해서 결과를 보여줌
        Optional<Machine> byId = machineRepository.findById(csMachine1.getId());
        Machine machine = byId.get();
        Assertions.assertThat(machine.getName()).isEqualTo("TEST1");
    }



}