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

    public int getCartTotalPrice(int memberId) {
        String sql = "SELECT NVL(SUM(p.price * c.quantity), 0) " +
                "FROM cart c JOIN product p ON c.productid = p.productid " +
                "WHERE c.memberid = :memberId";
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        Integer total = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return total != null ? total : 0;
    }
}
