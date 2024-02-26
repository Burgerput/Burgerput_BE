package burgerput.project.zenput.intercepter.checkSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.time.LocalDate;

import static burgerput.project.zenput.Const.BURGERPUTSITE;


@Slf4j
@RequiredArgsConstructor
public class CheckSessionInterceptor implements HandlerInterceptor {
    //post Aftercomplete - do nothing
    private CreateSession session = new CreateSession();
//    SaveData saveData;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==========PreHandle Interceptor Start==========");
        log.info("[request URL ={}]", request.getRequestURL());
        log.info("if getSession Start ******************");
        if (session.getSession(request) == null) {
//                //if it's not have a sessions then create it -> iT MEANS visited first time in this website
            // log.info("[preHandle getSession Result ] ={}", session.getSession(request));

            log.info("createSession START********************");
            session.createSessionV2(LocalDate.now(), response);
//            session.createSessionV2(LocalDate.now(), response);

            response.sendRedirect(BURGERPUTSITE + "loading");

//                //페이지 로딩 화면으로 이동하면 될 듯?
//                //loading페이지에서 로딩하도록 한다. 로딩이 다 완료되면 원래 페이지로 메인페이지로 이동

        } else {
            //check the date is same as today
            //it not same then delete the previous session and create new one and Data loading
            log.info("Not NULLLLLL");
            checkDate((LocalDate) session.getSession(request), request, response);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("======================AFTER================================");

    }

    private void checkDate(LocalDate sessionDate, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (sessionDate.equals(LocalDate.now())) {
            //Do nothing
            log.info("Today's information!  [{}]", LocalDate.now());
        } else {
            //if it is not Today's info
            //Delete Cookies
            session.expire(request, response);
            //create new Session Cookie
            session.createSession(LocalDate.now(), response);
            //loading페이지에서 로딩하도록 한다. 로딩이 다 완료되면 원래 페이지로 메인페이지로 이동
            response.sendRedirect(BURGERPUTSITE + "/loading");
        }
    }
}
