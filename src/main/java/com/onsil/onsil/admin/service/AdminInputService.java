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

    public List<AdminInputDto> searchInputs(String flowerName, LocalDateTime startDate, LocalDateTime endDate) {

        List<Object[]> objects = adminInputDao.searchInputs(flowerName,startDate,endDate);

        log.info("objects: {}", objects);
        List<AdminInputDto> adminInputDtos = objects.stream()
                .map(index -> new AdminInputDto(
                        ((Timestamp) index[0]).toLocalDateTime(),
                        ((Number) index[1]).intValue(),
                        ((String) index[2]),
                        ((Number) index[3]).intValue(),
                        ((String) index[4]),
                        ((String) index[5])
                ))
                 .collect(Collectors.toList());
        return adminInputDtos;
    }

    //입고 목록 검색
//    public List<AdminInputDto> searchInputs(String keyword, LocalDate startDate, LocalDate endDate) {
//        return adminInputRepository.searchByConditions(keyword, startDate, endDate);
//    }
}
