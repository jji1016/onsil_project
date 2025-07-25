CREATE TABLE MEMBER (
                        MEMBERID NUMBER PRIMARY KEY,
                        USERID VARCHAR2(100) UNIQUE NOT NULL,
                        NICKNAME VARCHAR2(100) UNIQUE NOT NULL,
                        USERNAME VARCHAR2(100) NOT NULL,
                        USERPW VARCHAR2(100) NOT NULL,
                        USEREMAIL VARCHAR2(50) UNIQUE NOT NULL,
                        ADDRESS01 VARCHAR2(100) NOT NULL,
                        ADDRESS02 VARCHAR2(100),
                        zipcode NUMBER NOT NULL,
                        TEL VARCHAR2(30) NOT NULL,
                        REGDATE DATE DEFAULT SYSDATE,
                        ROLE VARCHAR2(100) NOT NULL -- 'admin' 또는 'member'
);
SELECT * FROM MEMBER;

CREATE TABLE PRODUCT (
                         PRODUCTID NUMBER PRIMARY KEY,
                         FLOWERNAME VARCHAR2(100) NOT NULL,
                         PRICE NUMBER NOT NULL,
                         FLOWERINFO VARCHAR2(1000) NOT NULL,
                         IMAGE VARCHAR2(100) NOT NULL
);
SELECT * FROM PRODUCT;

CREATE TABLE CART (
                      CARTID NUMBER PRIMARY KEY,
                      PRODUCTID NUMBER,
                      MEMBERID NUMBER,
                      ORDERDATE DATE DEFAULT SYSDATE,
                      CONSTRAINT FK_CART_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID),
                      CONSTRAINT FK_CART_MEMBER FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID)
);
SELECT * FROM cart;

CREATE TABLE REVIEW (
                        REVIEWID NUMBER PRIMARY KEY,
                        PRODUCTID NUMBER,
                        MEMBERID NUMBER,
                        CONTENT VARCHAR2(1000) NOT NULL,
                        REGDATE DATE DEFAULT SYSDATE,
                        IMAGE VARCHAR2(100),
                        CONSTRAINT FK_REVIEW_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID),
                        CONSTRAINT FK_REVIEW_MEMBER FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID)
);
SELECT * FROM REVIEW;

CREATE TABLE SUBSCRIBE (
                           SUBSCRIBEID NUMBER PRIMARY KEY,
                           PRODUCTID NUMBER,
                           MEMBERID NUMBER,
                           STARTDATE DATE NOT NULL,
                           ENDDATE DATE NOT NULL,
                           PERIOD VARCHAR2(100) NOT NULL,
                           CONSTRAINT FK_SUBSCRIBE_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID),
                           CONSTRAINT FK_SUBSCRIBE_MEMBER FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID)
);
SELECT * FROM SUBSCRIBE;

CREATE TABLE ORDERLIST (
                           ORDERLISTID NUMBER PRIMARY KEY,
                           MEMBERID NUMBER,
                           PRODUCTID NUMBER,
                           ORDERTIME DATE DEFAULT SYSDATE,
                           QUANTITY NUMBER DEFAULT 1 NOT NULL, -- 수량 추가
                           CONSTRAINT FK_ORDERLIST_MEMBER FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID),
                           CONSTRAINT FK_ORDERLIST_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID)
);
SELECT * FROM ORDERLIST;

CREATE TABLE STOCK (
                       STOCKID NUMBER PRIMARY KEY,
                       PRODUCTID NUMBER,
                       QUANTITY NUMBER NOT NULL,
                       CONSTRAINT FK_STOCK_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID)
);
SELECT * FROM STOCK;

CREATE TABLE INPUT (
                       INPUTID NUMBER PRIMARY KEY,
                       PRODUCTID NUMBER,
                       AMOUNT NUMBER NOT NULL,
                       COMPANY VARCHAR2(100) DEFAULT 'flipflower' NOT NULL,
                       REGDATE DATE DEFAULT SYSDATE,
                       CONSTRAINT FK_INPUT_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID)
);
SELECT * FROM

    CREATE TABLE OUTPUT (
    OUTPUTID NUMBER PRIMARY KEY,
    PRODUCTID NUMBER,
    MEMBERID NUMBER,
    AMOUNT NUMBER NOT NULL,
    REGDATE DATE DEFAULT SYSDATE,
    CONSTRAINT FK_OUTPUT_PRODUCT FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID),
    CONSTRAINT FK_OUTPUT_MEMBER FOREIGN KEY (MEMBERID) REFERENCES MEMBER(MEMBERID)
);
SELECT * FROM OUTPUT;
