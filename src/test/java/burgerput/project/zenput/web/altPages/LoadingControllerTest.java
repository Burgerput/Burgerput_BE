package burgerput.project.zenput.web.altPages;

import burgerput.project.zenput.ConstT;
import com.github.dockerjava.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc//이거뺴니까 mockMVC bean없다고 헀음
//@DataJpaTest
class LoadingControllerTest {

    @Autowired
    private LoadingController loadingController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("loading/result Get Test")
    public void getTest() throws Exception {

        mockMvc.perform(
                get("/loading/result"))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(print()); // 응답값 print

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
            log.info("No file exist");
        }
    }

}