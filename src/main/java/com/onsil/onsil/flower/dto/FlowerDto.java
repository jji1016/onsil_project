// src/main/java/com/onsil/onsil/flower/dto/FlowerDto.java
package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.Flower;
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

    // 엔티티 → DTO 변환 생성자
    public FlowerDto(Flower flower) {
        this.dataNo = flower.getDataNo();
        this.fMonth = flower.getFMonth();
        this.fDay = flower.getFDay();
        this.flowNm = flower.getFlowNm();
        this.fSctNm = flower.getFSctNm();
        this.fEngNm = flower.getFEngNm();
        this.flowLang = flower.getFlowLang();
        this.fContent = flower.getFContent();
        this.fUse = flower.getFUse();
        this.fGrow = flower.getFGrow();
        this.fType = flower.getFType();
        this.fileName1 = flower.getFileName1();
        this.fileName2 = flower.getFileName2();
        this.fileName3 = flower.getFileName3();
        this.imgUrl1 = flower.getImgUrl1();
        this.imgUrl2 = flower.getImgUrl2();
        this.imgUrl3 = flower.getImgUrl3();
        this.publishOrg = flower.getPublishOrg();
    }
}
