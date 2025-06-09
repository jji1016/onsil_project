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

        List<Object[]> objects = adminOutputDao.searchOutputs(flowerName, startDate, endDate);

        log.info("objects: {}", objects);
        List<AdminOutputDto> adminOutputDtos = objects.stream()
                .map(index -> new AdminOutputDto(
                        ((Number) index[0]).intValue(),
                        ((String) index[1]),
                        ((String) index[2]),
                        ((Timestamp) index[3]).toLocalDateTime()
                ))
                .collect(Collectors.toList());
        return adminOutputDtos;
    }

    //출고 검색 기능
//    public List<AdminOutputDto> searchOutputs(String keyword, LocalDate startDate, LocalDate endDate) {
//        return adminOutputRepository.searchByConditions(keyword, startDate, endDate);
//    }
}

