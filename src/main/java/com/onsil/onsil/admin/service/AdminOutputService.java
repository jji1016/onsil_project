package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminOutputDao;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.repository.AdminOutputRepository;
import com.onsil.onsil.entity.Output;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOutputService {

    private final AdminOutputDao adminOutputDao;
//    private final AdminOutputRepository adminOutputRepository;

    public List<AdminOutputDto> searchOutputs(String flowerName,
                                              LocalDateTime startDate,
                                              LocalDateTime endDate) {

        List<Object[]> results = adminOutputDao.searchOutputs(flowerName, startDate, endDate);

        return results.stream().map(obj -> AdminOutputDto.builder()
                .regDate(((Timestamp) obj[0]).toLocalDateTime())
                .memberId(((Number) obj[1]).intValue())
                .flowerName((String) obj[2])
                .amount(((Number) obj[3]).intValue())
                .outPlace((String) obj[4])
                .userName((String) obj[5])
                .build()).collect(Collectors.toList());
    }
}

//출고 검색 기능
//    public List<AdminOutputDto> searchOutputs(String keyword, LocalDate startDate, LocalDate endDate) {
//        return adminOutputRepository.searchByConditions(keyword, startDate, endDate);
//    }


