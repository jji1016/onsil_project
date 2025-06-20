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

    //private final AdminOutputDao adminOutputDao;
    private final AdminOutputRepository adminOutputRepository;

    public List<AdminOutputDto> searchOutputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate,  int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;
        List<Object[]> results = adminOutputRepository.searchOutputs(category, keyword, startDate, endDate, startRow, endRow);
        return results.stream().map(obj -> AdminOutputDto.builder()
                .regDate(((Timestamp)obj[0]).toLocalDateTime())
                .outputId(((Number)obj[1]).intValue())
                .productId(((Number)obj[2]).intValue())
                .flowerName((String)obj[3])
                .amount(((Number)obj[4]).intValue())
                .implier("포레스트화원")
                .manager("김의진")
                .build()).collect(Collectors.toList());
    }

    public int countOutputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return adminOutputRepository.countOutputs(category, keyword, startDate, endDate);
    }
}



