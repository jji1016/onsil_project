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
@CrossOrigin(origins = "*") // ★ 프론트엔드 연동을 위한 CORS 허용
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ★ 장바구니 목록 조회 (프론트엔드 cart.html용)
    @GetMapping("/{memberId}")
    public ResponseEntity<List<CartItemDto>> getCartList(@PathVariable Integer memberId) {
        List<CartItemDto> items = cartService.getCartItems(memberId);
        return ResponseEntity.ok(items);
    }

    // ★ 장바구니 요약 정보 (프론트엔드 결제 페이지용)
    @GetMapping("/{memberId}/summary")
    public ResponseEntity<CartSummaryDto> getCartSummary(@PathVariable Integer memberId) {
        CartSummaryDto summary = cartService.getCartSummary(memberId);
        return ResponseEntity.ok(summary);
    }

    // ★ 상품 추가 (프론트엔드에서 상품명으로 추가)
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestParam Integer memberId,
                                       @RequestParam String flowerName,
                                       @RequestParam int quantity) {
        try {
            cartService.addToCart(memberId, flowerName, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ★ 수량 변경 (프론트엔드 수량 조절 버튼용)
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateQuantity(@PathVariable Integer cartId,
                                            @RequestParam int quantity) {
        try {
            cartService.updateQuantity(cartId, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ★ 개별 삭제 (프론트엔드 삭제 버튼용)
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Integer cartId) {
        try {
            cartService.removeFromCart(cartId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("삭제 중 오류: " + e.getMessage());
        }
    }

    // ★ 전체 비우기 (프론트엔드 전체삭제 버튼용)
    @DeleteMapping("/clear/{memberId}")
    public ResponseEntity<?> clearCart(@PathVariable Integer memberId) {
        try {
            cartService.clearCart(memberId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("장바구니 비우기 오류: " + e.getMessage());
        }
    }
}
