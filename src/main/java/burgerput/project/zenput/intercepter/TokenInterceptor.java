package burgerput.project.zenput.intercepter;

import burgerput.project.zenput.Services.jwtLogin.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static burgerput.project.zenput.Const.REFRESH_TOKEN_COOKIE_NAME;

@Slf4j
//@Component// Spring bean 주입을
public class TokenInterceptor implements HandlerInterceptor {

    //스프링 빈 수동 주입
    private final JwtTokenProvider jwt;

    @Autowired
    public TokenInterceptor(JwtTokenProvider jwt) {
        this.jwt = jwt;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            //이건 뜯어야 하는 옵션 인터셉트나 필터를 하나 설정해서 지정하도록 하자

            //헤더에서 String 값 가져오기 이거는
            String requestUri = request.getRequestURI();
            String authorizationHeader = request.getHeader("Authorization");

            log.info("Request URI: " + requestUri); // 경로 디버깅을 위해 로그 추가
            if (requestUri.startsWith("/signin") || requestUri.startsWith("/refresh-token") || requestUri.startsWith("/loading")) {
                return true; // 특정 경로는 제외
            }
            // Bearer는 OAuth2.0 및 인증 스키마에서 사용하는 인증 토큰 유형을 나타낸다.
            //우리는 JWT토큰이라서 OAuth를 사용함
            //Bearer키워드는 토큰의 유형을 지정하고 이 키워드 뒤에 오는 문자열은 실제 인증 토큰임
            //Bearer토큰은 액세스 토큰의 한 유형이다.
            log.info("TOKEN CONTENTS = {}", authorizationHeader == null? "NULL" : authorizationHeader);
            //토큰이 먼저 존재하는 지확인
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                //이후의 토큰을 가져온다. 즉 AccessToken부분을 가져온다.
                String token = authorizationHeader.substring(7);

                if(jwt.isTokenExpired(token)){//토큰만료
                    //Token의 만료시간이 지난경우
                    log.info("Interceptor : 액세스 토큰 > 토큰 만료시간 지남");
                    sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "TokenExpired");
                    return false;
                }

                if (jwt.validateToken(token)) {
                    //token이 유효한경우
                    return true;

                } else {
                    //token이 유효하지 않은 경우 // refresh-token도 함께 삭제
                    log.info("Interceptor :액세스 토큰 > 토큰 유효하지 않음");
                    //토큰이 유효하지 않을때에는 지워버려야 함

                    ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                            .path("/")
                            .sameSite("None")
                            .httpOnly(true)
                            .secure(true)
                            .maxAge(0) // 쿠키의 만료 시간 설정 (0으로 설정하여 즉시 삭제)
                            .domain(".burback.shop")
                            .build();
                    response.addHeader("Set-Cookie", refreshTokenCookie.toString()); // 응답에 쿠키 추가

                    sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "InvalidToken");
                    return false;
                }
            } else {
                //no AccessToken
                log.info("Interceptor : 액세스 토큰 > 토큰 없음");
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
