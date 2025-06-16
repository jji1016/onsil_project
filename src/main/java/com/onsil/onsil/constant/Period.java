package com.onsil.onsil.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Period {
    WEEKLY("주간"),
    MONTHLY("월간");
    private final String label;
}
