package com.onsil.onsil.mypage.repository;

import com.onsil.onsil.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageSubscribeRepository extends JpaRepository<Subscribe,Integer> {

    //구독 취소
    @Override
    void deleteById(Integer id);
}
