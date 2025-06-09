package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminInputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminInputDao {

    private final AdminInputRepository adminInputRepository;

    public List<Object[]> searchInputs(String flowerName,
                                       LocalDateTime startDate,
                                       LocalDateTime endDate) {
        List<Object[]> list = adminInputRepository.searchInputs(flowerName, startDate, endDate);
        log.info("DaoList={}",list.toString());
        return list;
    }
}
