package com.onsil.onsil.cart.controller;

import com.onsil.onsil.cart.dto.CartItemDto;
import com.onsil.onsil.cart.dto.CartSummaryDto;
import com.onsil.onsil.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.stereotype.Controller;
=======
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@Controller // RestController 대신 Controller 사용
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // ★ 프론트엔드 연동을 위한 CORS 허용
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ★ 장바구니 목록 조회 (프론트엔드 cart.html용)
    @GetMapping("/{memberId}")
    @ResponseBody
    public ResponseEntity<List<CartItemDto>> getCartList(@PathVariable Integer memberId) {
        List<CartItemDto> items = cartService.getCartItems(memberId);
        return ResponseEntity.ok(items);
    }

    // ★ 장바구니 요약 정보 (프론트엔드 결제 페이지용)
    @GetMapping("/{memberId}/summary")
    @ResponseBody
    public ResponseEntity<CartSummaryDto> getCartSummary(@PathVariable Integer memberId) {
        CartSummaryDto summary = cartService.getCartSummary(memberId);
        return ResponseEntity.ok(summary);
    }

    // ★ 상품 추가 (프론트엔드에서 상품명으로 추가)
    @PostMapping
    @ResponseBody
=======
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
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    public ResponseEntity<?> addToCart(@RequestParam Integer memberId,
                                       @RequestParam String flowerName,
                                       @RequestParam int quantity) {
        try {
            cartService.addToCart(memberId, flowerName, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
<<<<<<< HEAD
        }
    }

    // ★ 수량 변경 (프론트엔드 수량 조절 버튼용)
    @PutMapping("/{cartId}")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<?> clearCart(@PathVariable Integer memberId) {
        try {
            cartService.clearCart(memberId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("장바구니 비우기 오류: " + e.getMessage());
        }
    }

    // ★ cart.html 뷰 반환 (templates/cart/cart.html)
    @GetMapping("/page")
    public String cartPage() {
        return "cart/cart";
=======
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
>>>>>>> ef7780897a89fcccb9445fd9a55465c3081b2c69
    }
}
