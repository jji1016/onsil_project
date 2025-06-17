package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.Product;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowerDto {
<<<<<<< HEAD

    private Integer productId;
=======
    private Integer id;
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    private Integer dataNo;
    private Integer fMonth;
    private String flowerName;
    private String flowLang;
    private String flowerInfo;
    private String fUse;
    private String fGrow;
    private String fType;
<<<<<<< HEAD
    private Integer price;
    private String image;
    private String imageUrl; // 실제 이미지 URL

    // Entity에서 DTO로 변환하는 생성자
    public FlowerDto(Product product) {
        this.productId = product.getProductId();
=======
    private int price;
    private String image;
    private final String publishOrg = "농촌진흥청 국립원예특작과학원";

    public FlowerDto(Product product) {
        this.id = product.getId();
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
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
<<<<<<< HEAD

        // image 컬럼 값(영어 파일명, 확장자 포함)을 그대로 사용
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            this.imageUrl = "/upload/products/" + product.getImage();
        } else {
            this.imageUrl = "/upload/products/default.jpg";
        }
=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    }
}
