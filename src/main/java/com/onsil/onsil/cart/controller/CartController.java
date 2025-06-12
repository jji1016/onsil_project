package com.onsil.onsil.cart.controller;

import com.onsil.onsil.cart.dto.CartItemDto;
import com.onsil.onsil.cart.dto.CartSummaryDto;
import com.onsil.onsil.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{memberId}")
    public List<CartItemDto> getCartList(@PathVariable Integer memberId) {
        return cartService.getCartItems(memberId);
    }

    @GetMapping("/{memberId}/summary")
    public CartSummaryDto getCartSummary(@PathVariable Integer memberId) {
        return cartService.getCartSummary(memberId);
    }

    // 상품명으로 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestParam Integer memberId,
                                       @RequestParam String flowerName,
                                       @RequestParam int quantity) {
        try {
            cartService.addToCart(memberId, flowerName, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }
    }

    @PutMapping("/{cartId}")
    public void updateQuantity(@PathVariable Integer cartId,
                               @RequestParam int quantity) {
        cartService.updateQuantity(cartId, quantity);
    }

    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable Integer cartId) {
        cartService.removeFromCart(cartId);
    }

    @DeleteMapping("/clear/{memberId}")
    public void clearCart(@PathVariable Integer memberId) {
        cartService.clearCart(memberId);
    }
}
