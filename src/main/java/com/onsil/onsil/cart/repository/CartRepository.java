package com.onsil.onsil.cart.repository;

import com.onsil.onsil.entity.Cart;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.cart.dto.CartItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT new com.onsil.onsil.cart.dto.CartItemDto(" +
            "c.id, p.id, p.flowerName, p.price, c.quantity, p.image, m.nickName) " +
            "FROM Cart c " +
            "JOIN c.product p " +
            "JOIN c.member m " +
            "WHERE m.id = :memberId")
    List<CartItemDto> findCartItemsByMemberId(@Param("memberId") Integer memberId);

    Optional<Cart> findByMemberAndProduct(Member member, Product product);

    List<Cart> findByMember(Member member);

    void deleteByMember(Member member);
}
