package com.blackshirts.movieshelf.service;

import com.blackshirts.movieshelf.dto.*;
import com.blackshirts.movieshelf.entity.User;
import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponseCode;
import com.blackshirts.movieshelf.repository.UserRepository;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {

    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String getKaKaoAccessToken(String code) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + clientId); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + redirectUri); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public HashMap<String, Object> getUserKakaoInfo(String access_Token) {

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            if(kakao_account.getAsJsonObject().get("email") != null){
                String email = kakao_account.getAsJsonObject().get("email").getAsString();
                userInfo.put("email", email);
            }

            userInfo.put("nickname", nickname);
            userInfo.put("id", id);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public UserKakaoLoginResponseDto kakaoLogin(String access_Token) {
        UserKakaoSignupRequestDto userKakaoSignupRequestDto = getUserKakaoSignupRequestDto(getUserKakaoInfo(access_Token));
        UserResponseDto userResponseDto = findByUserKakaoIdentifier(userKakaoSignupRequestDto.getUserKakaoIdentifier());
        if (userResponseDto == null){
            signUp(userKakaoSignupRequestDto);
            userResponseDto = findByUserKakaoIdentifier(userKakaoSignupRequestDto.getUserKakaoIdentifier());
        }

        String token = jwtTokenProvider.createToken(userResponseDto.getUserEmail());
        return new UserKakaoLoginResponseDto(HttpStatus.OK, token, userResponseDto.getUserEmail());
    }

    public UserResponseDto findByUserKakaoIdentifier(String kakaoIdentifier) {
        List<User> user = userRepository.findUserByUserKakaoIdentifier(kakaoIdentifier).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        if(user.size() == 0){
            return null;
        }
        return new UserResponseDto(user.get(0));
    }

    @Transactional
    public Long signUp(UserKakaoSignupRequestDto userKakaoSignupRequestDto) throws BaseException {
        Long id;
        try {
            id = userRepository.save(userKakaoSignupRequestDto.toEntity()).getUserId();
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_USER);
        }
        return id;
    }

    private UserKakaoSignupRequestDto getUserKakaoSignupRequestDto(HashMap<String, Object> userInfo){
        String userPassword = "-1";
        UserKakaoSignupRequestDto userKakaoSignupRequestDto = new UserKakaoSignupRequestDto(userInfo.get("email").toString(), userInfo.get("nickname").toString(),userPassword,userInfo.get("nickname").toString(),userInfo.get("id").toString());
        return userKakaoSignupRequestDto;
    }
}
