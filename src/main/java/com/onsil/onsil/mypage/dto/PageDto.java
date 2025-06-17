package com.onsil.onsil.mypage.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {
    private int totalItems;
    private int currentPage;
    private int totalPages;
    private int itemsPerPage = 5;
}
