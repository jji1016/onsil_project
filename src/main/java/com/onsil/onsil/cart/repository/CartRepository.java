package com.onsil.onsil.cart.repository;

import com.onsil.onsil.entity.Cart;
import com.onsil.onsil.entity.Member;
<<<<<<< HEAD
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.cart.dto.CartItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
=======
import com.onsil.onsil.cart.dto.CartItemDto;
import com.onsil.onsil.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
<<<<<<< HEAD

    // ★ 장바구니 목록 조회 (프론트엔드 표시용)
=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    @Query("SELECT new com.onsil.onsil.cart.dto.CartItemDto(" +
            "c.id, p.id, p.flowerName, p.price, c.quantity, p.image, m.nickName) " +
            "FROM Cart c " +
            "JOIN c.product p " +
            "JOIN c.member m " +
            "WHERE m.id = :memberId")
    List<CartItemDto> findCartItemsByMemberId(@Param("memberId") Integer memberId);

<<<<<<< HEAD
    // ★ 중복 상품 체크용
    Optional<Cart> findByMemberAndProduct(Member member, Product product);

    // ★ 회원별 장바구니 조회
    List<Cart> findByMember(Member member);

    // ★ 장바구니 전체 비우기용
=======
    Optional<Cart> findByMemberAndProduct(Member member, Product product);

    List<Cart> findByMember(Member member);

>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    void deleteByMember(Member member);
}
