package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @Column(name = "PRODUCTID")
    private Integer productId;

    @Column(name = "DATANO")
    private Integer dataNo;

    @Column(name = "F_MONTH")
    private Integer fMonth;

    @Column(name = "FLOWERNAME")
    private String flowerName;

    @Column(name = "FLOW_LANG")
    private String flowLang;

    @Column(name = "FLOWERINFO")
    private String flowerInfo;

    @Column(name = "F_USE")
    private String fUse;

    @Column(name = "F_GROW")
    private String fGrow;

    @Column(name = "F_TYPE")
    private String fType;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "IMAGE")
    private String image;
}
