package burgerput.project.zenput.web.loading;

import burgerput.project.zenput.Services.loadingController.LoadingControllerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
