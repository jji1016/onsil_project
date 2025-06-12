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
    @JsonProperty("flowNm")
    private String flowNm;
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
    @JsonProperty("publishOrg")
    private final String publishOrg = "농촌진흥청 국립원예특작과학원";

    public FlowerDto(Flower flower) {
        this.dataNo = flower.getDataNo();
        this.fMonth = flower.getFMonth();
        this.flowNm = flower.getFlowNm();
        this.flowLang = flower.getFlowLang();
        this.fContent = flower.getFContent();
        this.fUse = flower.getFUse();
        this.fGrow = flower.getFGrow();
        this.fType = flower.getFType();
    }
}
