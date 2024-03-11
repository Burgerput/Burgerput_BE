package burgerput.project.zenput.web.altPages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
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
    void loading() throws IOException {
        Map<String, String> loading = loadingController.loading(request, response);
        log.info(loading.toString());
    }

}