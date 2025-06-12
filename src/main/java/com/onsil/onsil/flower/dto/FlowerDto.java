package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {
    private Integer id;
    private Integer dataNo;
    private Integer fMonth;
    private String flowerName;
    private String flowLang;
    private String flowerInfo;
    private String fUse;
    private String fGrow;
    private String fType;
    private int price;
    private String image;
    private final String publishOrg = "농촌진흥청 국립원예특작과학원";

    public FlowerDto(Product product) {
        this.id = product.getId();
        this.dataNo = product.getDataNo();
        this.fMonth = product.getFMonth();
        this.flowerName = product.getFlowerName();
        this.flowLang = product.getFlowLang();
        this.flowerInfo = product.getFlowerInfo();
        this.fUse = product.getFUse();
        this.fGrow = product.getFGrow();
        this.fType = product.getFType();
        this.price = product.getPrice();
        this.image = product.getImage();
    }
}
