package com.onsil.onsil.admin.service;

import com.onsil.onsil.entity.Cart;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.admin.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    // 장바구니 목록 조회
    public List<Cart> getCartList(Member member) {
        return cartRepository.findByMember(member);
    }

    // 장바구니에 상품 추가 (이미 있으면 수량 증가)
    @Transactional
    public Cart addToCart(Member member, Product product, int quantity) {
        return cartRepository.findByMemberAndProduct(member, product)
                .map(cart -> {
                    cart.setQuantity(cart.getQuantity() + quantity);
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .member(member)
                            .product(product)
                            .quantity(quantity)
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    // 장바구니에서 특정 상품 삭제
    @Transactional
    public void removeFromCart(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    // 장바구니 전체 비우기 (회원별)
    @Transactional
    public void clearCart(Member member) {
        List<Cart> carts = cartRepository.findByMember(member);
        cartRepository.deleteAll(carts);
    }

    // 장바구니 상품 수량 변경
    @Transactional
    public Cart updateQuantity(Integer cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }
}

