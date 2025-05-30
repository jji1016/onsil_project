package com.onsil.onsil.admin.service;

import com.onsil.onsil.admin.dao.AdminDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDao adminDao;

}
