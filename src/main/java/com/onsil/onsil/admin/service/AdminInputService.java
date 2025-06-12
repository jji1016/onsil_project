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

    private final AdminInputDao adminInputDao;
    //private final AdminInputRepository adminInputRepository;

    public List<AdminInputDto> searchInputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        List<Object[]> results = adminInputDao.searchInputs(category,keyword,startDate,endDate);

        log.info("results: {}", results);
        return results.stream().map(obj -> AdminInputDto.builder()
                .regDate(((java.sql.Timestamp)obj[0]).toLocalDateTime())
                .inputId(((Number)obj[1]).intValue())
                .productId(((Number)obj[2]).intValue())
                .flowerName((String)obj[3])
                .amount(((Number)obj[4]).intValue())
                .build()).collect(Collectors.toList());
    }
    }

