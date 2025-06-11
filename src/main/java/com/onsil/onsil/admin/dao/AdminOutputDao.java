package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminOutputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminOutputDao {

    private final AdminOutputRepository adminOutputRepository;

    public List<Object[]> searchOutputs(String flowerName,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate) {
        List<Object[]> result = adminOutputRepository.searchOutputs(flowerName, startDate, endDate);
//        log.info("출고내역 조회 결과: {}", result.size());
        return result;
    }
}
