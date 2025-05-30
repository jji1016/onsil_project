package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminDao {

    private final AdminRepository adminRepository;
}
