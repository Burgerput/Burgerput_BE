package burgerput.project.zenput.web.manager.machine;

import burgerput.project.zenput.Services.manager.machine.EnterMachineService;
import burgerput.project.zenput.Services.manager.machine.EnterMachineServiceImpl;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class EnterMachineController {
    private final EnterMachineService enterMachineService;

    @GetMapping("/back/enter/machines")
    @ResponseBody
    public Map<String, ArrayList<Map>> enterMachine() {
        return enterMachineService.enterMachine();
    }

    @PostMapping("/back/enter/machines")
    public Map<String, String> submitZenputMachine(@RequestBody String param) {
        return enterMachineService.submitZenputMachine(param);
    }

}
