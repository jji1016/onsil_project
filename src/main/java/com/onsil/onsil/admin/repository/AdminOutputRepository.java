package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface AdminOutputRepository extends JpaRepository<Output, Integer> {

    Collection<Object> findByOutDateBetween(LocalDate start, LocalDate end);
}
