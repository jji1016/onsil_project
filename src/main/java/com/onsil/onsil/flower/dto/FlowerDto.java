package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {
    private Integer productId;
    private Integer dataNo; // Integer → Integer 변경
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
        this.dataNo = product.getDataNo(); // Integer 타입으로 변경
        this.fMonth = product.getFMonth();
        this.flowerName = product.getFlowerName();
        this.flowLang = product.getFlowLang();
        this.flowerInfo = product.getFlowerInfo();
        this.fUse = product.getFUse();
        this.fGrow = product.getFGrow();
        this.fType = product.getFType();
        this.price = product.getPrice();
        this.image = product.getImage();

        // 이미지 URL 생성 (Integer dataNo 사용)
        if (product.getDataNo() != null && product.getFlowerName() != null) {
            this.imageUrl = String.format("/images/flower/%05d_%s.png",
                    product.getDataNo(), product.getFlowerName());
        } else {
            this.imageUrl = "/images/flower/default.png";
        }
    }
}
