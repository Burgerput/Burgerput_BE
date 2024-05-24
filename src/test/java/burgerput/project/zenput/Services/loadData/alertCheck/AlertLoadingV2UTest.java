package burgerput.project.zenput.Services.loadData.alertCheck;

import burgerput.project.zenput.ConstT;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
/*
* 기능 단위 테스트
* AlertLoading unit Test
*
* */
public class AlertLoadingV2UTest {

    @Test
    @DisplayName("file save test")
    public void fileJsonSaveTest() {
        String path = ConstT.JSONPATH;
        Date now = Calendar.getInstance().getTime();
        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:50:24 KST 2022
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);

        // 포맷팅 현재 날짜/시간 출력
        log.info(formatedNow.toString());

        JSONArray jsonArray = new JSONArray();
        if(!jsonArray.isEmpty()){
            try {
                FileWriter file = new FileWriter(path + formatedNow + ".json");
                file.write(jsonArray.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Test
    @DisplayName("this and That test")
    public void thisAndThat(){
        List<Map<String, String>> addMap = new ArrayList<>();
        log.info(String.valueOf(addMap.isEmpty()));

    }
}
