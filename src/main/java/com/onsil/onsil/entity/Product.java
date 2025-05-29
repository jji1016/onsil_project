package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String flowerName;

    @Column(nullable = false)
    private String flowerInfo;

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
