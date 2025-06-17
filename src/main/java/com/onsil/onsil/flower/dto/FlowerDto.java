package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {

    private Integer productId;
    private Integer dataNo;
    private Integer fMonth;
    private String flowerName;
    private String flowLang;
    private String flowerInfo;
    private String fUse;
    private String fGrow;
    private String fType;
    private Integer price;
    private String image;
    private String imageUrl; // 실제 이미지 URL

    // Entity에서 DTO로 변환하는 생성자
    public FlowerDto(Product product) {
        this.productId = product.getProductId();
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

        // image 컬럼 값(영어 파일명, 확장자 포함)을 그대로 사용
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            this.imageUrl = "/images/flower/" + product.getImage();
        } else {
            this.imageUrl = "/images/flower/default.jpg";
        }
    }
}
