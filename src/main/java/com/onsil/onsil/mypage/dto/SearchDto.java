package com.onsil.onsil.mypage.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {
    private String searchInfo;
    private String searchYear;
    private int currentPage;
    private int itemsPerPage;
}
