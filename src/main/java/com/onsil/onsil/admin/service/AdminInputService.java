package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminInputDao;
import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.repository.AdminInputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInputService {

    private final AdminInputDao adminInputDao;
    private final AdminInputRepository adminInputRepository;

    public List<AdminInputDto> searchInputs() {

        List<Object[]> objects = adminInputDao.searchInputs();

        log.info("objects: {}", objects);
        List<AdminInputDto> adminInputDtos = objects.stream()
                .map(index -> new AdminInputDto(
                        ((Number) index[0]).intValue(),
                        ((Timestamp) index[1]).toLocalDateTime(),
                        ((String) index[2]),
                        ((String) index[3]),
                        ((String) index[4])
                ))
                 .collect(Collectors.toList());
        return adminInputDtos;
    }

    //입고 목록 검색
    public List<AdminInputDto> searchInputs(String keyword, LocalDate startDate, LocalDate endDate) {
        return adminInputRepository.searchByConditions(keyword, startDate, endDate);
    }
}
