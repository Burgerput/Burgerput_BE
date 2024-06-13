package burgerput.project.zenput.web;

//burgerput 첫 페이지진입시 API를 주고받는 컨트롤러

import burgerput.project.zenput.Services.jwtLogin.JwtTokenProvider;
import burgerput.project.zenput.domain.MasterAccount;
import burgerput.project.zenput.repository.masterAccountRepository.MasterAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MasterLoginController {

    private final MasterAccountRepository masterAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/signin")
    public ResponseEntity<String> jwtLogin(@RequestParam String id, @RequestParam String password) {

        Optional<MasterAccount> foundAccount = masterAccountRepository.findById(id);

        try{
            // ID 검사
            if(foundAccount.isEmpty()){ //Account의 값을 찾을 수 없는 경우(인증 실패)
                //Account 계정이 없는경우
                throw new AuthenticationException("Account not found");
            }
            MasterAccount master = foundAccount.get();
            //password 검사
            if(!master.getMaster_pw().equals(password)){
                throw new AuthenticationException("Invalid username/password supplied") {};
            }

            //JWT토큰 생성 및 반환 로직
            String token = jwtTokenProvider.createToken(master.getMasterId());
            return new ResponseEntity<>(token, HttpStatus.OK);

        }catch(AuthenticationException e){
            return handleAuthenticationException(e);
        }

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex){
        log.info("Exception authentication");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
