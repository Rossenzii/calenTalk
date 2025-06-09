package mj.calenTalk.global.security.jwt;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.global.security.PrincipalDetails;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${JWT_SECRET}")
    private String secretKey;
    private Key key;
    private final UsersRepository usersRepository;
    private static final long accessTokenExpTime = 7 * 24 * 60 * 60 * 1000L; //1주
    private static final long refreshTokenExpTime = 30 * 24 * 60 * 60 * 1000L; //1달

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * user email로 토큰 생성
     * @param users
     * @return jwt token
     */
    public String generateAccessToken(Users users){
        Claims claims = Jwts.claims().setSubject(users.getEmail());;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(Users users){
        Claims claims = Jwts.claims().setSubject(users.getEmail());;
        Date now = new Date();
        String refreshToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return refreshToken;
    }

    /**
     * 토큰 유효성 검증
     * @param token
     * @return t/f
     */
    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.INVALID_JWT_EXCEPTION);
        }
    }


    /**
     * UserDetailsService 없이 db 조회
     * @param token
     * @return Authentication 객체(SecurityContextHolder 보관 역할)
     */
    public Authentication getAuthentication(String token){
        String email = getEmail(token);
        System.out.println("추출한 email"+email);
        Users users = usersRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
        PrincipalDetails principalDetails = new PrincipalDetails(users);
        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }




}
