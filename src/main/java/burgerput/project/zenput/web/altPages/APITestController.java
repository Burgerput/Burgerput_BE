package burgerput.project.zenput.web.altPages;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class APITestController {

    @GetMapping("/true")
    public Map<String, String> trueResult() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", "true");

        return result;
    }

    @GetMapping("/false")
    public Map<String, String> falseResult() {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", "false");

        return result;
    }

    @GetMapping("")
    public boolean booleanTest() {
        return true;
    }






    @GetMapping("/response")
    public ResponseEntity<Void> returnCode() {

        return new ResponseEntity<>(HttpStatus.OK); //200 RETURN
    }

}
