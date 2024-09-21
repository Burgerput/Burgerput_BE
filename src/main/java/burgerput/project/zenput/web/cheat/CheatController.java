package burgerput.project.zenput.web.cheat;

import burgerput.project.zenput.Services.cheatService.CheatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CheatController {

    private final CheatService cheatService;

    @GetMapping("back/cheatFood")
    public Map<String, ArrayList<Map>> showCheatFood() {
        return cheatService.showCheatFood();

    }

    @PostMapping("back/cheatFood")
    public void saveCheatFood(@RequestBody ArrayList<Map> param) {
        cheatService.saveCheatFood(param);
    }

    @GetMapping("back/cheatMachine")
    public Map<String, ArrayList<Map>> showCheatMachine() {
        return cheatService.showCheatMachine();
    }

    @PostMapping("back/cheatMachine")
    public void saveCheatMachine(@RequestBody ArrayList<Map> param) {
        cheatService.saveCheatMachine(param);
    }

}
