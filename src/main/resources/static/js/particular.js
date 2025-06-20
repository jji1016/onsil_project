$(document).ready(function () {

    $(".btn_sell").click(function () {
        alert("장바구니에 담겼습니다.")
    });

    $(".btn_sell4").click(function () {
        alert("찜목록에 담겨졌습니다.")
    });

    $(".deta_gnb>li").click(function (e) {
        e.preventDefault()
        let list = $(this).index();
        $(".deta_gnb>li").removeClass("active");
        $(".deta_gnb>li").eq(list).addClass("active");
    });

    $(".deta_txt>p:nth-child(1)").click(function () {
        $(".detailed_img").css({"height": "2672px"});
    });
    $(".deta_txt>p:nth-child(2)").click(function () {
        $(".detailed_img").css({"height": "340px"});
    });
    $(".deta_txt>p:nth-child(1)").click(function () {
        $(".deta_txt>p:nth-child(1)").removeClass("on");
        $(".deta_txt>p:nth-child(2)").addClass("on");
    });
    $(".deta_txt>p:nth-child(2)").click(function () {
        $(".deta_txt>p:nth-child(2)").removeClass("on")
        $(".deta_txt>p:nth-child(1)").addClass("on")
    });
});
