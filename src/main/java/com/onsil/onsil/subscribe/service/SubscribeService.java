package com.onsil.onsil.subscribe.service;

import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Subscribe;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.subscribe.dao.SubscribeDao;
import com.onsil.onsil.subscribe.dto.SubscribeDto;
import com.onsil.onsil.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeDao subscribeDao;

    public List<SubscribeDto> getRandom6Subscribes() {
        return subscribeDao.findRandom6Subscribes();
    }
}