package com.onsil.onsil.flower.dto;

import com.onsil.onsil.entity.SubMaterials;
import com.onsil.onsil.product.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubMaterialsDto {

    private Integer id;
    private String materialName;
    private String materialInfo;
    private int price;
    private String image;

    private List<ReviewDto> reviews;

    public SubMaterialsDto(SubMaterials subMaterials) {
        this.id = subMaterials.getId();
        this.materialName = subMaterials.getMaterialName();
        this.materialInfo = subMaterials.getMaterialInfo();
        this.price = subMaterials.getPrice();
        this.image = subMaterials.getImage();
    }
}
