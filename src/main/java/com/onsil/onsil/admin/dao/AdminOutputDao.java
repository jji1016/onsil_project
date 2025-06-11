package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminOutputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminOutputDao {
    private final AdminOutputRepository adminOutputRepository;

    public List<Object[]> searchOutputs(String category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        return adminOutputRepository.searchOutputs(category, keyword, startDate, endDate);
    }
}
