package com.onsil.onsil.admin.dao;

import com.onsil.onsil.admin.repository.AdminOutputRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminOutputDao {

    private final AdminOutputRepository adminOutputRepository;

    public List<Object[]> getOutputs() {
        List<Object[]> list = adminOutputRepository.getOutputs();
        log.info("DaoList={}",list.toString());
        return list;
    }
}
