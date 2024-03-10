package js.portfolio.controller;

import js.portfolio.service.PnuCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PnuCodeController {

    private final PnuCodeService pnuCodeService;

    @GetMapping("/find/{sidoName}/{sigunguName}/{eupmyeondongName}/{riName}/{parcel}/{bon}/{bu}")
    public ResponseEntity<Map<String, Object>> getPnuCode(@PathVariable String sidoName,         // 시도명
                                                          @PathVariable String sigunguName,      // 시군구명
                                                          @PathVariable String eupmyeondongName, // 읍면동명
                                                          @PathVariable String riName,           // 법정리명
                                                          @PathVariable String parcel,           // 산여부(0 : 대지, 1 : 산)
                                                          @PathVariable String bon,              // 지번본번(번지)
                                                          @PathVariable String bu) {             // 지번부번(호)

        String pnuCodeByAddress = pnuCodeService.filterPnuCodeByAddress(sidoName, sigunguName, eupmyeondongName, riName, parcel, bon, bu);

        Map<String, Object> map = new HashMap<>();
        map.put("pnucdoe", pnuCodeByAddress);

        return ResponseEntity.ok().body(map);
    }
}
