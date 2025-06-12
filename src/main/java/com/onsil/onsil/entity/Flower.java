package com.onsil.onsil.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FLOWER")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Flower {
    @Id
    @Column(name = "DATA_NO")
    private Integer dataNo;

    @Column(name = "F_MONTH")
    private Integer fMonth;

    @Column(name = "FLOW_NM", length = 300)
    private String flowNm;

    @Column(name = "FLOW_LANG", length = 1000)
    private String flowLang;

    @Column(name = "F_CONTENT", length = 4000)
    private String fContent;

    @Column(name = "F_USE", length = 2000)
    private String fUse;

    @Column(name = "F_GROW", length = 2000)
    private String fGrow;

    @Column(name = "F_TYPE", length = 1000)
    private String fType;
}
