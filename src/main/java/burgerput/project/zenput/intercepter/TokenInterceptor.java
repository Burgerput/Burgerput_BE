package burgerput.project.zenput.intercepter;

import burgerput.project.zenput.Services.jwtLogin.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Component// Spring bean 주입을
public class TokenInterceptor implements HandlerInterceptor {

    //스프링 빈 수동 주입
    private final JwtTokenProvider jwt;

    @Autowired
    public TokenInterceptor(JwtTokenProvider jwt) {
        this.jwt = jwt;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //헤더에서 String 값 가져오기 이거는
        String requestUri = request.getRequestURI();
        String authorizationHeader = request.getHeader("Authorization");

        log.info("Request URI: " + requestUri); // 경로 디버깅을 위해 로그 추가
        if (requestUri.startsWith("/signin") || requestUri.startsWith("/refresh-token")) {
            return true; // 특정 경로는 제외
        }
        // Bearer는 OAuth2.0 및 인증 스키마에서 사용하는 인증 토큰 유형을 나타낸다.
        //우리는 JWT토큰이라서 OAuth를 사용함
        //Bearer키워드는 토큰의 유형을 지정하고 이 키워드 뒤에 오는 문자열은 실제 인증 토큰임
        //Bearer토큰은 액세스 토큰의 한 유형이다.

        //토큰이 먼저 존재하는 지확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            //이후의 토큰을 가져온다. 즉 AccessToken부분을 가져온다.
            String token = authorizationHeader.substring(7);
            if (jwt.validateToken(token)) {
                //token이 유효한경우
                if (!jwt.isTokenExpired(token)) {
                    //토큰이 만료되지 않은 경우
                    //API수행
                    return true;
                } else {
                    //Token의 만료시간이 지난경우
                    log.info("Interceptor : 토큰 만료시간 지남");
                    sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "TokenExpired");
                    return false;
                }
            } else {
                //token이 유효하지 않은 경우
                log.info("Interceptor : 토큰 유효하지 않음");
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "InvalidToken");
                return false;
            }
        } else {
            //no AccessToken
            log.info("Interceptor : 토큰 없음");
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "InvalidToken");
            return false;
        }

    }

    private void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwt.validateToken(token)) {
                if (!jwt.isTokenExpired(token)) {
                    log.info("JWT Token is valid and not expired: " + token);
                } else {
                    log.warn("JWT Token is expired: " + token);
                }
            } else {
                log.warn("JWT Token is invalid: " + token);
            }
        } else {
            log.warn("No JWT Token found in request headers.");
        }

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
