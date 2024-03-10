package js.portfolio.service;

import js.portfolio.entity.PnuCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PnuCodeService {

    public String filterPnuCodeByAddress(String sidoName, String sigunguName, String eupmyeondongName, String riName, String parcel, String bon, String bu) {
        String filteredAddress = "";

        List<PnuCode> pnuCodes = textFileToPnuCode();

        for (PnuCode pnuCode : pnuCodes) {
            if (pnuCode.getSidoName().contains(sidoName) &&
                    pnuCode.getSigunguName().contains(sigunguName) &&
                    pnuCode.getEupmyeondongName().contains(eupmyeondongName) &&
                    pnuCode.getRiName().contains(riName)) {

                String jibunCode = parcel.contains("1")? "2": "1";
                jibunCode = jibunCode + StringUtils.leftPad(bon, 4, "0");
                jibunCode = jibunCode + StringUtils.leftPad(bu, 4, "0");

                filteredAddress = pnuCode.getCode() + jibunCode;
                log.info("filteredAddress : {}", filteredAddress);
                return filteredAddress;
            }
        }

        return filteredAddress;
    }

    private List<PnuCode> textFileToPnuCode() {
        List<PnuCode> listPnuCode = new ArrayList<>();

        String encoding = null;
        Resource resource = null;

        try {
            Resource[] resources = ResourcePatternUtils
                    .getResourcePatternResolver(new DefaultResourceLoader())
                    .getResources("classpath*:text_file/*.txt");

            // 로드된 각 리소스에 대해 반복 처리
            for (Resource findResource : resources) {
                encoding = detectEncoding(findResource);
                log.info("[{}] 파일의 인코딩 정보 : {}", findResource.getFilename(), encoding);
                resource = findResource;
            }
        } catch (IOException e) {
            log.error("텍스트 파일을 찾을 수 없습니다.");
        }

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding))) {

            reader.readLine(); //헤더는 건너뜀.

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                PnuCode pnuCode = createPnuCode(currentLine);
                if (pnuCode != null) {
                    listPnuCode.add(pnuCode);
                }
            }
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }

        return listPnuCode;
    }

    private PnuCode createPnuCode(String pnuCode) {
        String[] parts = pnuCode.split("\t");
        String code = parts[0];
        String[] names = parts[1].split(" ");
        String use = parts[2];

        if (use.equals("폐지")) {
            return null;
        }

        // JSON 객체 생성
        return PnuCode.builder()
                .sidoCode(code.substring(0, 2))
                .sidoName(getName(names, 0))
                .sigunguCode(code.substring(2, 5))
                .sigunguName(getName(names, 1))
                .eupmyeondongCode(code.substring(5, 8))
                .eupmyeondongName(getName(names, 2))
                .riCode(code.substring(8))
                .riName(getName(names, 3))
                .parcel("")
                .bon("")
                .bu("")
                .build();
    }

    private String getName(String[] names, int index) {
        return index < names.length ? names[index] : "";
    }

    //csv 파일의 인코딩 체크
    private String detectEncoding(Resource resource) throws IOException {
        byte[] buf = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);

        try (InputStream is = resource.getInputStream()) {
            int nread;
            while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
        }
        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding != null ? encoding : "Unknown";
    }
}
