package com.onsil.onsil.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_USER("일반회원",3),
    ROLE_ADMIN("관리자",3);
    private final String label;
    private final int level;
}
