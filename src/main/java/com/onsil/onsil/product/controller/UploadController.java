package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    @Value("${file.path}products/")
    String productsPath;  // 여기서 주입

@GetMapping("/products")
public String uploadForm() {
    return "upload/products";
}

@PostMapping("/save")
public String saveProduct(
        @RequestParam("flowerName") String flowerName,
        @RequestParam("price") int price,
        @RequestParam("flowerInfo") String flowerInfo,
        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

    File saveDir = new File(productsPath);
    if (!saveDir.exists()) {
        saveDir.mkdirs();
    }


    String originalFilename = imageFile.getOriginalFilename(); // abc.jpg
    String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // .jpg
    String baseName = originalFilename.substring(0, originalFilename.lastIndexOf(".")); // abc
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    String storedFileName = baseName + "_" + timestamp + extension; // abc_20250605123045.jpg



    String savePath = productsPath+ storedFileName;


        imageFile.transferTo(new File(savePath));


        Product product = new Product();
        product.setFlowerName(flowerName);
        product.setPrice(price);
        product.setFlowerInfo(flowerInfo);
        product.setImage(storedFileName);
        productRepository.save(product);
        return "redirect:/product/list";
    }

}
