package js.portfolio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import js.portfolio.entity.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressSearchService {

    @Value("${api.juso.service.key}")
    private String serviceKey;
    private final RestTemplate customRestTemplate;

    /**
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     * 주소 조회
     * @param keyword(도로명주소 또는 지번주소)
     * @return
     */
    public Address searchAddress(String keyword) {
        String baseUrl = "https://business.juso.go.kr/addrlink/addrLinkApiJsonp.do";

        return requestData(keyword, baseUrl);
    }

    private Address requestData(String keyword, String baseUrl) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("confmKey", serviceKey)
                .queryParam("currentPage", "1")
                .queryParam("countPerPage", "10")
                .queryParam("keyword", keyword)
                .queryParam("resultType", "json")
                .queryParam("hstryYn", "N")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = customRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Address address = null;
        try {
            String addressData = response.getBody().substring(1, response.getBody().length() - 1);
            address = objectMapper.readValue(addressData, Address.class);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }

        return address;
    }

}
