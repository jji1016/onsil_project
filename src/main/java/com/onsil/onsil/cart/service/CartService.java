package com.onsil.onsil.cart.service;

import com.onsil.onsil.entity.Cart;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.cart.dto.CartItemDto;
import com.onsil.onsil.cart.dto.CartSummaryDto;
import com.onsil.onsil.cart.repository.CartRepository;
import com.onsil.onsil.cart.dao.CartDao;
import com.onsil.onsil.member.repository.MemberRepository;
import com.onsil.onsil.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartDao cartDao;

    // ★ 장바구니 목록 조회
    public List<CartItemDto> getCartItems(Integer memberId) {
        return cartRepository.findCartItemsByMemberId(memberId);
    }

    // ★ 장바구니 요약 정보 (결제 페이지용)
    public CartSummaryDto getCartSummary(Integer memberId) {
        List<CartItemDto> items = getCartItems(memberId);
        int totalPrice = cartDao.getCartTotalPrice(memberId);
        int deliveryFee = (totalPrice >= 30000) ? 0 : 2500; // 3만원 이상 무료배송
        int finalPrice = totalPrice + deliveryFee;
        return new CartSummaryDto(items, totalPrice, deliveryFee, finalPrice);
    }

    // ★ 상품명으로 장바구니에 추가
    @Transactional
    public void addToCart(Integer memberId, String flowerName, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
        Product product = productRepository.findByFlowerName(flowerName)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + flowerName));

        // 이미 있는 상품이면 수량만 증가
        cartRepository.findByMemberAndProduct(member, product)
                .ifPresentOrElse(
                        cart -> {
                            cart.setQuantity(cart.getQuantity() + quantity);
                            cartRepository.save(cart);
                        },
                        () -> {
                            Cart newCart = Cart.builder()
                                    .member(member)
                                    .product(product)
                                    .quantity(quantity)
                                    .build();
                            cartRepository.save(newCart);
                        }
                );
    }

    // ★ 수량 변경
    @Transactional
    public void updateQuantity(Integer cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + cartId));
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    // ★ 개별 삭제
    @Transactional
    public void removeFromCart(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    // ★ 전체 비우기
    @Transactional
    public void clearCart(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
        cartRepository.deleteByMember(member);
    }
}
