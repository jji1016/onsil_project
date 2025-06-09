package com.onsil.onsil.flower.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {
    @JsonProperty("dataNo")
    private Integer dataNo;
    @JsonProperty("fMonth")
    private Integer fMonth;
    @JsonProperty("fDay")
    private Integer fDay;
    @JsonProperty("flowNm")
    private String flowNm;
    @JsonProperty("fSctNm")
    private String fSctNm;
    @JsonProperty("fEngNm")
    private String fEngNm;
    @JsonProperty("flowLang")
    private String flowLang;
    @JsonProperty("fContent")
    private String fContent;
    @JsonProperty("fUse")
    private String fUse;
    @JsonProperty("fGrow")
    private String fGrow;
    @JsonProperty("fType")
    private String fType;
    @JsonProperty("fileName1")
    private String fileName1;
    @JsonProperty("fileName2")
    private String fileName2;
    @JsonProperty("fileName3")
    private String fileName3;
    @JsonProperty("imgUrl1")
    private String imgUrl1;
    @JsonProperty("imgUrl2")
    private String imgUrl2;
    @JsonProperty("imgUrl3")
    private String imgUrl3;
    @JsonProperty("publishOrg")
    private String publishOrg;
}
