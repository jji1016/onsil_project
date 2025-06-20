package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminInputDao;
import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.repository.AdminInputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInputService {

    private final AdminInputRepository adminInputRepository;
    //private final AdminInputRepository adminInputRepository;

    public List<AdminInputDto> searchInputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        List<Object[]> results = adminInputRepository.searchInputs(category, keyword, startDate, endDate, startRow, endRow);

        return results.stream().map(obj -> AdminInputDto.builder()
                        .regDate(((java.sql.Timestamp) obj[0]).toLocalDateTime())
                        .inputId(((Number) obj[1]).intValue())
                        .productId(((Number) obj[2]).intValue())
                        .flowerName((String) obj[3])
                        .amount(((Number) obj[4]).intValue())
                        .supplier("온실본사")
                        .manager("유정환")
                        .build())
                .collect(Collectors.toList());
    }

    public int countInputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return adminInputRepository.countInputs(category, keyword, startDate, endDate);
    }
}

