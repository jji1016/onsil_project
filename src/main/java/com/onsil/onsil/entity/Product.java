package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "PRODUCTID")
    private Integer id;

    @Column(name = "DATANO")
    private Integer dataNo;

    @Column(name = "F_MONTH")
    private Integer fMonth;

    @Column(name = "FLOW_LANG", length = 1000)
    private String flowLang;

    @Column(name = "F_USE", length = 2000)
    private String fUse;

    @Column(name = "F_GROW", length = 2000)
    private String fGrow;

    @Column(name = "F_TYPE", length = 1000)
    private String fType;

    @Column(name = "FLOWERNAME", length = 300, nullable = false)
    private String flowerName;

    @Column(name = "FLOWERINFO", length = 4000, nullable = false)
    private String flowerInfo;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "IMAGE", length = 255, nullable = false)
    private String image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderList> orderLists;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Stock> stockList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Input> inputList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Output> outputList;
}
