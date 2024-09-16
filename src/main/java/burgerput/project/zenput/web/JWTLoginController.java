package burgerput.project.zenput.web;

import burgerput.project.zenput.Services.utils.jwtLogin.JwtTokenProvider;
import burgerput.project.zenput.domain.MasterAccount;
import burgerput.project.zenput.repository.masterAccountRepository.MasterAccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Spring 2.대는 javax를 사용하지만 현재  Spring3.0에는 jakarta를 사용한다.
import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

import static burgerput.project.zenput.Const.REFRESH_TOKEN_COOKIE_NAME;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JWTLoginController {
    private final MasterAccountRepository masterAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> jwtLogin(@RequestBody User user, HttpServletResponse response) {

        Optional<MasterAccount> foundAccount = masterAccountRepository.findById(user.getId());
        try {
            // ID 검사
            if (foundAccount.isEmpty()) { //Account의 값을 찾을 수 없는 경우(인증 실패)
                //Account 계정이 없는경우
                throw new AuthenticationException("Account not found");
            }
            MasterAccount master = foundAccount.get();
            //password 검사

            if (!master.getMaster_pw().equals(user.getPassword())) {
                throw new AuthenticationException("Invalid username/password supplied") {
                };
            }
            //JWT토큰 생성 및 반환 로직
            String accessToken = jwtTokenProvider.createToken(master.getMasterId());
            // refresh-token 생성하기
            String refreshToken = jwtTokenProvider.createRefreshToken(master.getMasterId());
            int refreshTokenValidityInSeconds = (int) (jwtTokenProvider.getRefreshValidityInMilliseconds() / 1000);

            //설정시에만 Srping에서 제공하는 ResponseCookie사용
            ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                    .path("/")
                    .sameSite("None")
                    .httpOnly(true).secure(true).maxAge(refreshTokenValidityInSeconds)
                    .domain(".burback.shop")
                    .build();

            response.addHeader("Set-Cookie", refreshTokenCookie.toString());

            return ResponseEntity.ok(new TokenResponse(accessToken));

        } catch (AuthenticationException e) {
            return handleAuthenticationException(e);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        //쿠키 목록에서 쿠키가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {//쿠키에 값이 있는 경우 수행하기
            for (Cookie cookie : cookies) { //쿠키들의 값 꺼내기
                if ("refreshToken".equals(cookie.getName())) { //refreshToken인경우 로직 수행
                    String refreshToken = cookie.getValue();//value를 가져온다 토큰의 값을 가져오는 것
                    if (jwtTokenProvider.validateToken(refreshToken)) {//유효한 토큰인지 확인한다.
                        //토큰에서 유저의 정보를 뽑아낸다.
                        String username = jwtTokenProvider.getUsername(refreshToken);
                        //해당 이름으로 다시 accessToken을 만든다.
                        String newAccessToken = jwtTokenProvider.createToken(username);
                        //토큰을 반환하고 상태를 200으로 반환한다.
                        return ResponseEntity.ok(new TokenResponse(newAccessToken));
                    }
                }
            }
        }
        log.info("Cookie value null");
        //refresh-token으로 요청이 왔으나 refresh-token이 없는 경우에는 401에러메세지를 출력한다.
        return new ResponseEntity<>("Invalid refresh token", HttpStatus.UNAUTHORIZED);
    }


    @Data
    private static class User {
        private String id;
        private String password;

    }

    //JSON 형태로 return값을 반환하기 위해 만든 객체
    //유지보수성 가독성을 위해서 JSON객체 형태로 응답한다.
    @Data
    private static class TokenResponse {
        private final String accessToken;

        public TokenResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        log.info("Error message = {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}