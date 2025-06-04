package com.onsil.onsil.admin.controller;

import com.onsil.onsil.entity.Cart;
import com.onsil.onsil.entity.Member;
import com.onsil.onsil.entity.Product;
import com.onsil.onsil.admin.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 목록 조회
    @GetMapping("/{memberId}")
    public List<Cart> getCartList(@PathVariable Integer memberId) {
        Member member = new Member();
        member.setId(memberId); // 실제 구현에서는 memberService 등으로 조회
        return cartService.getCartList(member);
    }

    // 장바구니에 상품 추가
    @PostMapping
    public Cart addToCart(@RequestParam Integer memberId, @RequestParam Integer productId, @RequestParam int quantity) {
        Member member = new Member();
        member.setId(memberId);
        Product product = new Product();
        product.setId(productId);
        return cartService.addToCart(member, product, quantity);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable Integer cartId) {
        cartService.removeFromCart(cartId);
    }

    // 장바구니 전체 비우기
    @DeleteMapping("/clear/{memberId}")
    public void clearCart(@PathVariable Integer memberId) {
        Member member = new Member();
        member.setId(memberId);
        cartService.clearCart(member);
    }

    // 장바구니 수량 변경
    @PutMapping("/{cartId}")
    public Cart updateQuantity(@PathVariable Integer cartId, @RequestParam int quantity) {
        return cartService.updateQuantity(cartId, quantity);
    }
}
