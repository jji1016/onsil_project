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

    @Column(name = "F_DAY")
    private Integer fDay;

    @Column(name = "FLOW_NM", length = 300)
    private String flowNm;

    @Column(name = "F_SCT_NM", length = 300)
    private String fSctNm;

    @Column(name = "F_ENG_NM", length = 300)
    private String fEngNm;

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

    @Column(name = "FILE_NAME1", length = 500)
    private String fileName1;

    @Column(name = "FILE_NAME2", length = 500)
    private String fileName2;

    @Column(name = "FILE_NAME3", length = 500)
    private String fileName3;

    @Column(name = "IMG_URL1", length = 1000)
    private String imgUrl1;

    @Column(name = "IMG_URL2", length = 1000)
    private String imgUrl2;

    @Column(name = "IMG_URL3", length = 1000)
    private String imgUrl3;

    @Column(name = "PUBLISH_ORG", length = 500)
    private String publishOrg;
}
