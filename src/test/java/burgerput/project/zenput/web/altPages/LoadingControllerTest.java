package burgerput.project.zenput.web.altPages;

import burgerput.project.zenput.ConstT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
//@DataJpaTest
class LoadingControllerTest {

    @Autowired
    private LoadingController loadingController;

    public void load(LoadingController loadingController){
        this.loadingController = loadingController;
    }

    static MockHttpServletRequest request;
     static MockHttpServletResponse response;

    @BeforeAll
    public static void mockSetup() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("json readingTest")
    public void jsonReading(){
        // JSON 파일 읽기
        String path = ConstT.JSONPATH;
        Date now = Calendar.getInstance().getTime();
        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:50:24 KST 2022
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);

        String finalPath = path + formatedNow + ".json";
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(finalPath)));

        } catch (IOException e) {
            //파일이 없경우
            log.info(result);
        }
    }

}