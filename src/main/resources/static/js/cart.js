document.addEventListener("DOMContentLoaded", function () {
    // "상품구매" 버튼 클릭 시 payment.html로 이동
    const buyBtn = document.querySelector(".btn_buy");
    if (buyBtn) {
        buyBtn.addEventListener("click", function () {
            // 단순 이동 (쿼리스트링 등 추가 정보 필요 없으면 이대로)
            location.href = "/payment";
        });
    }

    // 전체선택 체크박스 동작
    const allCheckbox = document.getElementById("all");
    const itemCheckboxes = document.querySelectorAll(".sell_it");
    if (allCheckbox) {
        allCheckbox.addEventListener("change", function () {
            const isChecked = allCheckbox.checked;
            itemCheckboxes.forEach(function (checkbox) {
                checkbox.checked = isChecked;
            });
        });
    }

    // 수량 조절 버튼 동작
    const buttons = document.querySelectorAll(".sel_mp");
    buttons.forEach(function (btn) {
        btn.addEventListener("click", function () {
            const parent = btn.closest(".btn_all");
            const countSpan = parent.querySelector("span");
            let count = parseInt(countSpan.textContent);
            if (btn.value === "+") {
                count += 1;
            } else if (btn.value === "-" && count > 1) {
                count -= 1;
            }
            countSpan.textContent = count;
        });
    });
});
