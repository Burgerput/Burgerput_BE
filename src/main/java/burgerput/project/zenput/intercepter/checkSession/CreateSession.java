package burgerput.project.zenput.intercepter.checkSession;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//cookie - UUID  | Date
//Spring Bean으로 동록하지 않은 이유는 어짜피 로딩될때 처음에만 생성하는 클래스 즉 하루에 1 번만 사용하는 거라서 굳이
//Singleton으로 등록해서 공유하지 않기로 함
//만약에 공유하게 된다면 SessionStore에 여러 매장의 값이 들어가게되고 이를 분류하는 작업 필요

@Slf4j
public class CreateSession {
    //싱글톤으로 관리되면 안되는 로직
    //상태를 공유하게 된다.
//    private String MY_SESSION_COOKIE_NAME = "BurgerSessionID";
    private String MY_SESSION_COOKIE_NAME = "WTFCOOKIE";

    //session system store
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();



    //세션 생성
    public void createSession(LocalDate date, HttpServletResponse response) {
        String sessionID =UUID.randomUUID().toString();
        if (sessionStore.isEmpty()) {
                //세션을 새로 만들어야 하는 경우
                sessionStore.put(sessionID, date);
                //쿠키 생성
                //응답에 쿠키 담아서 보냄
                log.info("create Session test session id ={} AND cOOKIE ID ={}", sessionID, MY_SESSION_COOKIE_NAME);
                Cookie mySessionCookie = new Cookie(MY_SESSION_COOKIE_NAME, sessionID);
                response.addCookie(mySessionCookie);
        }
    }


    //SameSite issue
    public void createSessionV2(LocalDate date, HttpServletResponse response) {
        String sessionID =UUID.randomUUID().toString();
        if (sessionStore.isEmpty()) {
                //세션을 새로 만들어야 하는 경우
                sessionStore.put(sessionID, date);
                //쿠키 생성
            ResponseCookie cookie = ResponseCookie.from(MY_SESSION_COOKIE_NAME,sessionID)
                    .secure(true)
                            .sameSite("None")
                                    .path("/")
                                            .httpOnly(false)
                                                    .build();

            //응답에 쿠키 담아서 보냄
                log.info("[V2]create Session test session id ={} AND cOOKIE ID ={}", sessionID, MY_SESSION_COOKIE_NAME);
                response.addHeader("Set-Cookie",cookie.toString());
        }
    }

    //세션 조회하기
    //UUID에 해당하는 value값 가져오기 이떄 확장성을 위해 Object로 설정했음
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, MY_SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            log.info("[Get Session cookie null result]");
            return null;
        }else{
            log.info("[Get Session] SessionCookie value = {} and path = {}", sessionCookie.getValue(), sessionCookie.getPath());
            log.info("Session store info = {}", sessionStore.toString());

            return sessionStore.get(sessionCookie.getValue());
        }

    }

    //세션 만료
    public void expire(HttpServletRequest request, HttpServletResponse response) {
        Cookie sessionCookie = findCookie(request, MY_SESSION_COOKIE_NAME);
        sessionCookie.setMaxAge(0);
        sessionStore.remove(sessionCookie.getValue());
        log.info("[EXPIRED] sessionCookie value name [{}] [{}] sessionStore [{}]", sessionCookie.getName(), sessionCookie.getValue(), sessionStore);
        response.addCookie(sessionCookie);

        //모든 쿠키 삭제
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals(MY_SESSION_COOKIE_NAME)) {
//                //cookie이름이 Burgerput인것 삭제
//                cookie.setMaxAge(0);
//                //세션 스토어 삭제
//                sessionStore.remove(cookie.getValue());
//            }
//        }
    }

    private Cookie findCookie(HttpServletRequest request, String mySessionCookieName) {
        
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
            if (cookie.getName().equals(mySessionCookieName)) {
                log.info("[find Cookie result]     NAME= {},      VALUE = {}", cookie.getName(), cookie.getValue());
                return cookie;
            }
        } 
    }
        return null;
    }

    //쿠키 삭제
    void deleteAllCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                sessionStore.remove(cookie.getValue());
            }
            sessionStore.clear();

        } catch (NullPointerException e) {

        }
    }
}
