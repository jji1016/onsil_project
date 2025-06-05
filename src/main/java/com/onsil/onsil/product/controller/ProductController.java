package com.onsil.onsil.product.controller;

import com.onsil.onsil.entity.Product;
import com.onsil.onsil.product.dto.ProductDto;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Value("${file.path}")
    private String upload;  // 여기서 주입

    @GetMapping("/upload")
    public String uploadForm() {
        return "/product/upload";  // templates/upload.html 렌더링
    }

    @PostMapping("/save")
    public String saveProduct(
                              @RequestParam("flowerName") String flowerName,
                              @RequestParam("price") int price,
                              @RequestParam("flowerInfo") String flowerInfo,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // 1. 파일명 처리
        String originalFilename = imageFile.getOriginalFilename(); // abc.jpg
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // .jpg
        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf(".")); // abc
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String storedFileName = baseName + "_" + timestamp + extension; // abc_20250605123045.jpg

        // 2. 저장 경로 설정

        String savePath = upload+ "products/"+ storedFileName;


        imageFile.transferTo(new File(savePath));

        // 4. DB 저장
        Product product = new Product();
        product.setFlowerName(flowerName);
        product.setPrice(price);
        product.setFlowerInfo(flowerInfo);
        product.setImage(storedFileName); // 오직 파일명만 저장
        productRepository.save(product);

        return "redirect:/product/list"; // 목록 페이지로 이동
    }
    @GetMapping("/list")
    public String list(Model model) {
        List<ProductDto> flowers=productService.findAll();
        model.addAttribute("flowers", flowers);
        return "product/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProductDto flower = productService.getProductById(id);
        if (flower == null) {
            return "error/404"; // 존재하지 않는 경우 예외 처리 가능
        }
        model.addAttribute("flower", flower);
        return "product/detail";
    }

}
