package burgerput.project.zenput.web.cheat;

import burgerput.project.zenput.Services.cheatService.CheatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@AutoConfigureMockMvc // MockMvc 자동 설정
class CheatControllerTest {

    //가짜데이터를 만들어서 수행함
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheatService cheatService;

    @org.junit.jupiter.api.Test
    void showCheatFood() {

    }

    @org.junit.jupiter.api.Test
    void saveCheatFood() {
    }

    @org.junit.jupiter.api.Test
    void showCheatMachine() {
    }

    @org.junit.jupiter.api.Test
    void saveCheatMachine() {
    }
}