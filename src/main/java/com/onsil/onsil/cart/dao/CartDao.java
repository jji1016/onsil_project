package com.onsil.onsil.cart.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    // ★ 장바구니 총액 계산 (결제 페이지용)
    public int getCartTotalPrice(int memberId) {
        String sql = "SELECT NVL(SUM(p.PRICE * c.QUANTITY), 0) " +
                "FROM CART c JOIN PRODUCT p ON c.PRODUCTID = p.PRODUCTID " +
                "WHERE c.MEMBERID = :memberId";
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        Integer total = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return total != null ? total : 0;
    }
}
