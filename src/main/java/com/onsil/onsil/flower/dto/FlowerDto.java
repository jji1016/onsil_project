package com.onsil.onsil.flower.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {
    private Integer dataNo;
    private Integer fMonth;
    private Integer fDay;
    private String flowNm;
    private String fSctNm;
    private String fEngNm;
    private String flowLang;
    private String fContent;
    private String fUse;
    private String fGrow;
    private String fType;
    private String fileName1;
    private String fileName2;
    private String fileName3;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String publishOrg;
}
