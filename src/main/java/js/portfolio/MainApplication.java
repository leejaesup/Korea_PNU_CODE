package js.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

/*
Convert_Number_Address_to_PNU_CODE

아래 링크(행정표준코드 관리시스템)에서 법정동 정보 내려받기
1. 링크 접속 : https://www.code.go.kr/stdcode/regCodeL.do
2. 법정동 코드 전체자료 버튼 클릭
3. 압축 해제 후 resources/text_file 에 이동
 */