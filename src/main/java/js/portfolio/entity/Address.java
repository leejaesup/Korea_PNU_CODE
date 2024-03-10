package js.portfolio.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Address {
    public Results results;

    @Getter @Setter
    public static class Results {
        public Common common;
        public List<Juso> juso;

        @Getter
        @Setter
        public static class Common {
            private String errorMessage;
            private String countPerPage;
            private String totalCount;
            private String errorCode;
            private String currentPage;
        }

        @Getter
        @Setter
        public static class Juso {
            private String detBdNmList;
            private String engAddr;
            private String rn;
            private String emdNm;
            private String zipNo;
            private String roadAddrPart2;
            private String emdNo;
            private String sggNm;
            private String jibunAddr;
            private String siNm;
            private String roadAddrPart1;
            private String bdNm;
            private String admCd;
            private String udrtYn;
            private String lnbrMnnm;
            private String roadAddr;
            private String lnbrSlno;
            private String buldMnnm;
            private String bdKdcd;
            private String liNm;
            private String rnMgtSn;
            private String mtYn;
            private String bdMgtSn;
            private String buldSlno;
        }
    }
}
