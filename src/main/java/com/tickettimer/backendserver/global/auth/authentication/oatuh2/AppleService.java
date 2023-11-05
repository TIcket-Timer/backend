package com.tickettimer.backendserver.global.auth.authentication.oatuh2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.tickettimer.backendserver.global.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleService {
    private final AppleOpenFeign appleOpenFeign;
    private final ObjectMapper objectMapper;
    @Value("${oauth2.apple.issuer}")
    private String iss;
    @Value(("${oauth2.apple.clientId}"))
    private String clientId;
//    @Value(("${oauth2.apple.nonce}"))
//    private final String nonce;

    /**
     * 1. apple로 부터 공개키 3개 가져옴
     * 2. 내가 클라에서 가져온 token String과 비교해서 써야할 공개키 확인 (kid,alg 값 같은 것)
     * 3. 그 공개키 재료들로 공개키 만들고, 이 공개키로 JWT토큰 부분의 바디 부분의 decode하면 유저 정보
     * 코드 출처 : https://hello-gg.tistory.com/65
     */


    private ApplePublicKey getApplePublicKey(String kid, String alg) {
        // id 토큰에 매칭되는 key 가져옴
        ApplePublicKeys keys = appleOpenFeign.getKeys();
        List<ApplePublicKey> appleKeys = keys.getKeys();
        ApplePublicKey findKey = appleKeys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst()
                .orElseThrow(() -> new InvalidTokenException());
        return findKey;
    }

    public PublicKey generatePublicKey(String idToken) {
        // identity token의 kid와 alg를 가져옴
        // header 값만 필요하고 jwt는 header.payload.signature로 구성되어 있기 때문에 .으로 나눈 후 첫 번째만 가져옴
        String encodedHeader = idToken.split("\\.")[0];

        // 인코딩 된 값을 디코딩 함
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String decodedHeader = new String(decoder.decode(encodedHeader));
        log.info(decodedHeader);
        Map map = null;
        try {
            map = objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException exception) {
            log.info(exception.getMessage());
            throw new InvalidTokenException();
        }

        // 애플에서 가져온 public key와 ios가 준 idToken을 바탕으로 public key를 생성함
        String kid = (String) map.get("kid");
        String alg = (String) map.get("alg");
        ApplePublicKey applePublicKey = getApplePublicKey(kid, alg);
        byte[] nBytes = decoder.decode(applePublicKey.getN());
        byte[] eBytes = decoder.decode(applePublicKey.getE());
        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.getKty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new InvalidTokenException();
        }
    }
    public Claims parseIdToken(String idToken, PublicKey publicKey) {
        // 에러는 AuthenticationExceptioinFilter에서 처리 ex) ExpiredJwtException...
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(idToken)
                .getBody();

    }

    public boolean validateToken(String idToken, PublicKey publicKey) {
        Claims claims = parseIdToken(idToken, publicKey);
        return claims.getIssuer().contains(iss)
                && claims.getAudience().equals(clientId);

    }

    public Map<String, String> getUserInfo(String idToken) {
        System.out.println("idToken = " + idToken);

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL("https://appleid.apple.com/auth/keys");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JsonParser parser = new JsonParser();
        JsonObject keys = (JsonObject) parser.parse(result.toString());
        JsonArray keyArray = (JsonArray) keys.get("keys");


        //클라이언트로부터 가져온 identity token String decode
        String[] decodeArray = idToken.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0]));

        //apple에서 제공해주는 kid값과 일치하는지 알기 위해
        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        //써야하는 Element (kid, alg 일치하는 element)
        JsonObject avaliableObject = null;
        for (int i = 0; i < keyArray.size(); i++) {
            JsonObject appleObject = (JsonObject) keyArray.get(i);
            JsonElement appleKid = appleObject.get("kid");
            JsonElement appleAlg = appleObject.get("alg");

            if (Objects.equals(appleKid, kid) && Objects.equals(appleAlg, alg)) {
                avaliableObject = appleObject;
                break;
            }
        }

        //일치하는 공개키 없음
        if (ObjectUtils.isEmpty(avaliableObject))
            throw new RuntimeException();

        PublicKey publicKey = this.getPublicKey(avaliableObject);

        //--> 여기까지 검증

        Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();
        JsonObject userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));
        String userId = userInfoObject.get("sub").getAsString();
        String email = userInfoObject.get("email").getAsString();
        log.info("sub : {} email : {}", userId, email);
        Map<String, String> map = new HashMap<>();
        map.put("sub", userId);
        map.put("email", email);
        return map;
    }

    public PublicKey getPublicKey(JsonObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


}