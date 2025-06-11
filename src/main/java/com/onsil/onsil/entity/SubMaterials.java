package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class SubMaterials {

    @Id
    @Column(name = "materialsID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productInfo;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
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

