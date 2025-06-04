package com.onsil.onsil.cart.controller;

import com.onsil.onsil.cart.dto.CartItemDto;
import com.onsil.onsil.cart.dto.CartSummaryDto;
import com.onsil.onsil.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 목록 조회 (DTO)
    @GetMapping("/{memberId}")
    public List<CartItemDto> getCartList(@PathVariable Integer memberId) {
        return cartService.getCartItems(memberId);
    }

    // 장바구니 합계/배송비 계산
    @GetMapping("/{memberId}/summary")
    public CartSummaryDto getCartSummary(@PathVariable Integer memberId) {
        return cartService.getCartSummary(memberId);
    }

    // 장바구니에 상품 추가
    @PostMapping
    public void addToCart(@RequestParam Integer memberId, @RequestParam Integer productId, @RequestParam int quantity) {
        cartService.addToCart(memberId, productId, quantity);
    }

    // 장바구니 상품 수량 변경
    @PutMapping("/{cartId}")
    public void updateQuantity(@PathVariable Integer cartId, @RequestParam int quantity) {
        cartService.updateQuantity(cartId, quantity);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable Integer cartId) {
        cartService.removeFromCart(cartId);
    }

    // 장바구니 전체 비우기
    @DeleteMapping("/clear/{memberId}")
    public void clearCart(@PathVariable Integer memberId) {
        cartService.clearCart(memberId);
    }
}
