package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dto.AdminOutputDto;
import com.onsil.onsil.admin.repository.AdminOutputRepository;
import com.onsil.onsil.entity.Output;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOutputService {

    private final AdminOutputRepository adminOutputRepository;


//    public List<AdminOutputDto> getStockOuts(LocalDate start, LocalDate end) {
//        return adminOutputRepository.findByOutDateBetween(start, end)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
    }

