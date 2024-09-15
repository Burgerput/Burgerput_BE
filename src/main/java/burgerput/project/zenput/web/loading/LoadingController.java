package burgerput.project.zenput.web.loading;

import burgerput.project.zenput.Const;
import burgerput.project.zenput.Services.LoadingController.LoadingControllerService;
import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.utils.loadData.alertCheck.AlertLoading2;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;



@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/loading", method={RequestMethod.GET, RequestMethod.POST})
public class LoadingController {
    private final LoadingControllerService loadingControllerService;

    @GetMapping
    public Map<String,String> loading(){
        return loadingControllerService.loading();
    }

    @GetMapping("/result")
    @ResponseBody
    public List<Map<String,Object>> readResult(){
        return loadingControllerService.readLoadingResult();
    }
}
