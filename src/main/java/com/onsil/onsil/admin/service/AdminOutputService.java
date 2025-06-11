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

    public List<AdminOutputDto> searchOutputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = adminOutputDao.searchOutputs(category, keyword, startDate, endDate);
        return results.stream().map(obj -> AdminOutputDto.builder()
                .regDate(((Timestamp)obj[0]).toLocalDateTime().toLocalDate())
                .productId(((Number)obj[1]).intValue()) // 품목코드 가공
                .flowerName((String)obj[2])
                .outputAmount(((Number)obj[3]).intValue())
                .userName((String)obj[4])
                .build()).collect(Collectors.toList());
    }
}



