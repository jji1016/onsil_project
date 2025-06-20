$(document).ready(function () {
    $('.qna_ti').on('click', function () {
        const $currentRow = $(this).closest('tr');
        const $currentAnswer = $currentRow.find('.qna_txt');

        if ($currentRow.hasClass('active')) {
            // 닫기
            $currentAnswer
                .slideUp(300, function () {
                    $(this).css('display', 'none');
                });
            $currentRow.removeClass('active');
        } else {
            // 모든 항목 닫기
            $('.qna_box tr').removeClass('active');
            $('.qna_txt')
                .slideUp(300, function () {
                    $(this).css('display', 'none');
                });

            // 클릭한 항목 열기
            $currentRow.addClass('active');
            $currentAnswer
                .css('display', 'block')
                .hide()
                .slideDown(300);
        }
    });
});