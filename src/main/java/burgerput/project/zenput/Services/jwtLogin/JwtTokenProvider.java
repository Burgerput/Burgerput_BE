package burgerput.project.zenput.Services.jwtLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {
    //Application.properties에서 설정한 값을 가져와서 사용한다.
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Value("${security.jwt.token.refresh-expire-length:7200000}") // 2 hours
    private long refreshValidityInMilliseconds = 7200000;


    @PostConstruct
    protected void init() {
        //secretKey의 길이가 32이하인 경우 weakKey Exception이 발생할 수있다.
        if(secretKey.length() < 32){
            //키의 길이가 짧은 경우 새로운 키를 생성한다.
            secretKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        }else{
            //키의 길이가 충분하면 해당  키로 생성한다.
            secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        }

    }

    public String createToken(String username){
        //Claims객체를 생성하고 주어진 username을 주제로 설정한다.
        //Claims는 JWT 페이로드에 저장되는 데이터이다. subSubject 메서도로 클리엠 설정
        Claims claims = Jwts.claims().setSubject(username);
        //현재 시간- 토큰 발급 시간 및 만료시간 계산을 위해서
        Date now = new Date();
        //토큰의 만료시간을 계산해서 Date객체로 만든다.
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        //
        return Jwts.builder().setClaims(claims)//JWT빌더 생성 setClaims으로 페이로드에 claims을 설정한다.
                .setIssuedAt(now) // 현재 발행시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)//토큰에 서명 HS256알고리즘을 사용하고 secretKey를 비밀키로 사용한다.
                .compact();// JWT문자열을 생성하고 반환한다. dot(.)으로 구분 되는 헤더, 페이로드, 시그니처로 나뉨
    }

    //refresh-token을 만드는 과정
    public String createRefreshToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰에서 subject를 추출하여 반환한다.
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey)//JWT파서를 생성하고 서명검증에 사용할 비밀키를 설정한다.
                .parseClaimsJws(token)//주어진 토큰을 파싱하여 claim 추출
                .getBody().getSubject();// 파싱된 JWT페이로드를 가져온다.(getBody) 주제 클레임을 반환ㄴ한다.(getSubject()0

    }
    //만들어진 JWT토큰이 유효한지 검증한다.
    public boolean validateToken(String token){
        try{
            //JWT파서를 생성한후 서명검증에 secretKey를 사용한다.
            // 주어진 토큰을 파싱하여 서명을 검증한다.
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;//유효하면 true를 리턴한다.
        }catch(Exception e){
            //유효하지 않으면 false를 리턴한다.
            return false;
        }
    }

    //refresh-token의 만료 시간을 반환
    public long getRefreshValidityInMilliseconds() {
        return refreshValidityInMilliseconds;
    }

}
