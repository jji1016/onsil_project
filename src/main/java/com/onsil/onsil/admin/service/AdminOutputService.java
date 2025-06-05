package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminOutputDao;
import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.repository.AdminOutputRepository;
import com.onsil.onsil.entity.Output;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOutputService {

    private final AdminOutputDao adminOutputDao;

    public List<AdminOutputDto> getOutputs() {

        List<Object[]> objects = adminOutputDao.getOutputs();

        log.info("objects: {}", objects);
        List<AdminOutputDto> adminOutputDtos = objects.stream()
                .map(index -> new AdminOutputDto(
                        ((Number) index[0]).intValue(),
                        ((Timestamp) index[1]).toLocalDateTime(),
                        ((String) index[2]),
                        ((String) index[3])
                ))
                .collect(Collectors.toList());
        return adminOutputDtos;
    }

    }

