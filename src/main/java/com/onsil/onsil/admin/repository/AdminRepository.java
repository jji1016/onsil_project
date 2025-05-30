package com.onsil.onsil.admin.repository;

import com.onsil.onsil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository <Member, Long>{


}
