package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dto.AdminInputDto;
import com.onsil.onsil.admin.repository.AdminInputRepository;
import com.onsil.onsil.entity.Input;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminInputService {

    private final AdminInputRepository adminInputRepository;

    public List<AdminInputDto> getAllStockEntries(String keyword) {
        List<Input> entries = (keyword == null || keyword.isBlank())
                ? adminInputRepository.findAll()
                : adminInputRepository.findByProductNameContaining(keyword);

        return entries.stream()
                .map(e -> new AdminInputDto(
                        e.getRegDate(),
                        e.getProduct(),
                        e.getId(),
                        e.getAmount(),
                        e.getCompany()
                ))
                .collect(Collectors.toList());
    }
}
