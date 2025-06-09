package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminOutputRepository extends JpaRepository<Output, Integer> {

    @Query(value = "SELECT o.AMOUNT, o.REGDATE, p.FLOWERNAME, m.USERNAME " +
            "FROM OUTPUT o " +
            "JOIN PRODUCT p ON o.PRODUCTID = p.PRODUCTID " +
            "JOIN MEMBER m ON o.PRODUCTID = m.PRODUCTID ", nativeQuery = true)
    List<Object[]> getOutputs();
}
