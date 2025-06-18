package com.onsil.onsil.product.dao;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.review.dto.ReviewDto;
import com.onsil.onsil.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductDao {
    private final ProductRepository productRepository;

    public ProductDto findByDtoId(int id) {
        Optional<Product> findedProduct = productRepository.findById(id);
        if (findedProduct.isPresent()) {
            Product product = findedProduct.get();

            List<ReviewDto> reviewDtoList = product.getReviewList().stream()
                    .map(review -> new ReviewDto(
                            review.getId(),
                            review.getProduct().getId(),
                            review.getMember().getId(),
                            review.getMember().getUserID(),
                            review.getMember().getUserName(),
                            review.getMember().getNickName(),
                            review.getContent(),
                            review.getRating(),
                            review.getRegDate(),
                            review.getImage()
                    ))
                    .collect(Collectors.toList());

            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .flowerName(product.getFlowerName())
                    .flowerInfo(product.getFlowerInfo())
                    .price(product.getPrice())
                    .image(product.getImage())
                    .reviews(reviewDtoList)
                    .build();
            return productDto;
        } else {
            throw new RuntimeException("상품을 찾을 수 없습니다. ID: " + id);
        }
    }

    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = productList.stream().map(
                product -> ProductDto.builder()
                        .id(product.getId())
                        .flowerName(product.getFlowerName())
                        .flowerInfo(product.getFlowerInfo())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .build()
        ).toList();
        return productDtoList;
    }

    public List<ProductDto> findRandom8() {
        List<Product> productList = productRepository.findAll();
        Collections.shuffle(productList); // 리스트 무작위 섞기
        List<Product> subList = productList.size() > 8 ? productList.subList(0, 8) : productList;

        return subList.stream().map(
                product -> ProductDto.builder()
                        .id(product.getId())
                        .flowerName(product.getFlowerName())
                        .flowerInfo(product.getFlowerInfo())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .build()
        ).collect(Collectors.toList());
    }
}
