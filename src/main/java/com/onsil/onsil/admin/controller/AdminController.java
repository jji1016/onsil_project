package com.onsil.onsil.admin.controller;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.dto.MemberDto;
import com.onsil.onsil.admin.dto.PopularCountDto;
import com.onsil.onsil.admin.dto.SubscribeDto;
import com.onsil.onsil.admin.service.AdminOutputService;
import com.onsil.onsil.admin.service.AdminService;
import com.onsil.onsil.constant.Period;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.product.repository.ProductRepository;
import com.onsil.onsil.product.service.ProductService;
import com.onsil.onsil.subscribe.repository.SubScribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final SubScribeRepository subscribeRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    @Value("${file.path}products/")
    String productsPath;  // 여기서 주입

    @GetMapping("/dashboard")
    public String index(Model model) {

        int countedMember = adminService.countAllMembers();
        int inOneMonthSubscribeMember = adminService.countOneMonth();
        List<PopularCountDto> popularSubscribe = adminService.popularSubscribe();
        List<SubscribeDto> recentSubscribes = adminService.findRecentSubscribeInOneMonth();

        model.addAttribute("allSubscribeMember", countedMember);
        model.addAttribute("inOneMonthSubscribeMember", inOneMonthSubscribeMember);
        model.addAttribute("popularSubscribe", popularSubscribe);
        model.addAttribute("recentSubscribes", recentSubscribes);


        return "admin/dashboard";
    }

    @GetMapping("/member-list")
    public String memberList(Model model) {

        List<MemberDto> memberList = adminService.getAllMembers();
        model.addAttribute("memberList", memberList);

        return "admin/member-list";
    }

    @PostMapping("/member-list/delete/{userID}")
    @ResponseBody
    public MemberDto delete(@PathVariable String userID) {
        return adminService.deleteByUserID(userID);
    }

    @GetMapping("/member-detail/{userID}")
    public String memberDetail(@PathVariable String userID, Model model) {

        MemberDto memberDetail = adminService.findByUserID(userID);
        model.addAttribute("memberDetail", memberDetail);

        return "admin/member-detail";
    }

    @GetMapping("/member-modify/{userID}")
    public String modifyMember(@PathVariable String userID, Model model) {

        MemberDto memberModify = adminService.findByUserID(userID);
        model.addAttribute("memberModify", memberModify);

        return "admin/member-modify";
    }

    @PostMapping("/member-modify/{userID}")
    public String modifyMember(@PathVariable String userID, @ModelAttribute MemberDto dto) {
        adminService.modifyMember(userID, dto);
        return "redirect:/admin/member-list";
    }


    @GetMapping("/order-list/{id}")
    public String orderList(@PathVariable int id, Model model) {

        List<SubscribeDto> orderLists = adminService.findByMemberID(id);
        model.addAttribute("orderLists", orderLists);

        return "admin/order-list";
    }

    @GetMapping("/member-search")
    @ResponseBody
    public List<MemberDto> searchMembers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return adminService.search(keyword, category, start, end);
    }

    private final AdminOutputService adminOutputService;


    @GetMapping("/outputlist")
    public String list(Model model) {

        List<AdminOutputDto> list = adminOutputService.getOutputs();
        log.info("list={}", list);

        model.addAttribute("outputList", list);
        return "admin/output";
    }

    @GetMapping("/product")
    public String product(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 7, Sort.by(Sort.Direction.DESC, "id"));
        Page<Product> productPage = productRepository.findAll(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("productCount", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/product";
    }

    @PostMapping("/save")
    public String saveProduct(
            @RequestParam("f_month") int fMonth,
            @RequestParam("flowerName") String flowerName,
            @RequestParam("price") int price,
            @RequestParam("flowerInfo") String flowerInfo,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("f_lang") String flowLang,
            @RequestParam("f_use") String fUse,
            @RequestParam("f_grow") String fGrow,
            @RequestParam("f_type") String fType
    ) throws IOException {

        File saveDir = new File(productsPath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }


        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String storedFileName = baseName + "_" + timestamp + extension; // abc_20250605123045.jpg


        String savePath = productsPath + storedFileName;


        imageFile.transferTo(new File(savePath));


        Product product = new Product();
        product.setFMonth(fMonth);
        product.setFlowerName(flowerName);
        product.setImage(storedFileName);
        product.setFlowLang(flowLang);
        product.setFUse(fUse);
        product.setFGrow(fGrow);
        product.setFType(fType);
        product.setFlowerInfo(flowerInfo);
        product.setPrice(price);

        productRepository.save(product);

        return "redirect:/admin/product";
    }
}



