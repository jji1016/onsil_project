package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import com.onsil.onsil.flower.repository.FlowerRepository;
import com.onsil.onsil.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlowerService {

    private final FlowerRepository flowerRepository;

    // 월 이름 매핑 (Java 8 호환)
    private static final Map<Integer, String> MONTH_NAMES;
    private static final Map<Integer, String> MONTH_NAMES_ENG;

    static {
        Map<Integer, String> monthNames = new HashMap<>();
        monthNames.put(1, "1월"); monthNames.put(2, "2월"); monthNames.put(3, "3월"); monthNames.put(4, "4월");
        monthNames.put(5, "5월"); monthNames.put(6, "6월"); monthNames.put(7, "7월"); monthNames.put(8, "8월");
        monthNames.put(9, "9월"); monthNames.put(10, "10월"); monthNames.put(11, "11월"); monthNames.put(12, "12월");
        MONTH_NAMES = Collections.unmodifiableMap(monthNames);

        Map<Integer, String> monthNamesEng = new HashMap<>();
        monthNamesEng.put(1, "January"); monthNamesEng.put(2, "February"); monthNamesEng.put(3, "March"); monthNamesEng.put(4, "April");
        monthNamesEng.put(5, "May"); monthNamesEng.put(6, "June"); monthNamesEng.put(7, "July"); monthNamesEng.put(8, "August");
        monthNamesEng.put(9, "September"); monthNamesEng.put(10, "October"); monthNamesEng.put(11, "November"); monthNamesEng.put(12, "December");
        MONTH_NAMES_ENG = Collections.unmodifiableMap(monthNamesEng);
    }

    /**
     * 특정 월의 꽃 정보 조회
     */
    public Map<String, Object> getFlowersByMonth(Integer month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1-12 사이여야 합니다.");
        }

        List<Product> products = flowerRepository.findByFMonth(month);
        List<FlowerDto> flowerDtos = products.stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("month", month);
        result.put("monthName", MONTH_NAMES.get(month));
        result.put("monthNameEng", MONTH_NAMES_ENG.get(month));
        result.put("flowers", flowerDtos);

        return result;
    }

    /**
     * 전체 월별 꽃 정보 조회 (1월~12월)
     */
    public Map<Integer, Map<String, Object>> getAllMonthlyFlowers() {
        List<Product> allProducts = flowerRepository.findAllByFMonthOrderByFMonth();

        // 월별로 그룹핑
        Map<Integer, List<Product>> groupedByMonth = allProducts.stream()
                .collect(Collectors.groupingBy(Product::getFMonth));

        Map<Integer, Map<String, Object>> result = new HashMap<>();

        // 1월부터 12월까지 모든 월에 대해 처리
        for (int month = 1; month <= 12; month++) {
            List<Product> monthProducts = groupedByMonth.getOrDefault(month, new ArrayList<>());
            List<FlowerDto> flowerDtos = monthProducts.stream()
                    .map(FlowerDto::new)
                    .collect(Collectors.toList());

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month);
            monthData.put("monthName", MONTH_NAMES.get(month));
            monthData.put("monthNameEng", MONTH_NAMES_ENG.get(month));
            monthData.put("flowers", flowerDtos);

            result.put(month, monthData);
        }

        return result;
    }

    /**
     * 키워드로 꽃 검색
     */
    public List<FlowerDto> searchFlowers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Product> products = flowerRepository.searchByKeyword(keyword.trim());
        return products.stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 꽃 상세정보 조회
     */
    public FlowerDto getFlowerDetail(Integer productId) {
        Optional<Product> productOpt = flowerRepository.findByProductId(productId);
        if (productOpt.isPresent()) {
            return new FlowerDto(productOpt.get());
        }
        throw new IllegalArgumentException("해당 꽃 정보를 찾을 수 없습니다.");
    }

    /**
     * 특정 월의 꽃 개수 조회
     */
    public Integer getFlowerCountByMonth(Integer month) {
        return flowerRepository.countByFMonth(month);
    }

    /**
     * 모든 월별 꽃 개수 조회
     */
    public Map<Integer, Integer> getAllMonthlyFlowerCounts() {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            counts.put(month, flowerRepository.countByFMonth(month));
        }
        return counts;
    }

    /**
     * 특정 월의 꽃 리스트만 조회 (단순)
     */
    public List<FlowerDto> getFlowerListByMonth(Integer month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1-12 사이여야 합니다.");
        }

        List<Product> products = flowerRepository.findByFMonth(month);
        return products.stream()
                .map(FlowerDto::new)
                .collect(Collectors.toList());
    }
}
