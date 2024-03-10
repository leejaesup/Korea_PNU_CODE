package js.portfolio.entity;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class PnuCode {

    String sidoCode;
    String sidoName;
    String sigunguCode;
    String sigunguName;
    String eupmyeondongCode;
    String eupmyeondongName;
    String riCode;
    String riName;
    String parcel;
    String bon;
    String bu;

    @Builder
    public PnuCode(String sidoCode, String sidoName, String sigunguCode, String sigunguName, String eupmyeondongCode, String eupmyeondongName, String riCode, String riName, String parcel, String bon, String bu) {
        this.sidoCode = sidoCode;
        this.sidoName = sidoName;
        this.sigunguCode = sigunguCode;
        this.sigunguName = sigunguName;
        this.eupmyeondongCode = eupmyeondongCode;
        this.eupmyeondongName = eupmyeondongName;
        this.riCode = riCode;
        this.riName = riName;
        this.parcel = parcel;
        this.bon = bon;
        this.bu = bu;
    }

    @Override
    public String toString() {
        return  "시도코드 :" + sidoCode + ", 시도 명 :" + sidoName + "\n" +
                "시군구코드 :" + sigunguCode + ", 시군구 명 :" + sigunguName + "\n" +
                "읍면동코드 :" + eupmyeondongCode + ", 읍면동 명 :" + eupmyeondongName + "\n" +
                "리코드 :" + riCode + ", 리 명 :" + riName + "\n" +
                "필지구분 : " + parcel + "\n" +
                "본번/부번 : " + bon + "-" + bu;
    }

    public String getCode() {
        return  sidoCode + sigunguCode + eupmyeondongCode + riCode + parcel + bon + bu;
    }

    public String getName() {
        return  sidoName + " "+ sigunguName + " " + eupmyeondongName + " " + riName + " " + parcel + " " + bon + "-" + bu;
    }
}
