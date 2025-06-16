package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @Column(name = "FLOWERNAME", length = 300)
    private String flowerName;

    @Column(name = "FLOW_LANG", length = 1000)
    private String flowLang;

    @Column(name = "FLOWERINFO", length = 4000)
    private String flowerInfo;

    @Column(name = "F_USE", length = 2000)
    private String fUse;

    @Column(name = "F_GROW", length = 2000)
    private String fGrow;

    @Column(name = "F_TYPE", length = 1000)
    private String fType;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "IMAGE", length = 255)
    private String image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}

