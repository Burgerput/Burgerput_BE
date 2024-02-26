package burgerput.project.zenput.intercepter;

import burgerput.project.zenput.intercepter.checkSession.CreateSession;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Slf4j
@Component
class CreateSessionTest {

    CreateSession session = new CreateSession();

    @Test
    @DisplayName("Session create ,save , search")
    public void createCookie() {
        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        LocalDate date = LocalDate.now();
        session.createSession(date,response);
        log.info("CreateCookie {}", date);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회
        Object session1 = session.getSession(request);
        log.info("Session get result {}", session1);
        Assertions.assertThat(session1).isEqualTo(LocalDate.now());

        //세션 만료
//        session.expire(request);
//        Object session2 = session.getSession(request);
//        Assertions.assertThat(session2).isNull();

    }
}