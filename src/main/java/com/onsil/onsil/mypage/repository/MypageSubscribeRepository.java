package com.onsil.onsil.mypage.repository;

import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageSubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Override
    void deleteById(Integer id);
}
