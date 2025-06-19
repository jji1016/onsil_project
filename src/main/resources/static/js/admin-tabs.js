document.addEventListener('DOMContentLoaded', function () {
    const tabs = document.querySelectorAll('.tab-btn');

    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const target = this.getAttribute('data-tab');

            fetch(`/admin/${target}`, {
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
                .then(response => response.text())
                .then(html => {
                    // 백엔드에서 fragment만 반환한다고 가정
                    document.querySelector('#content').innerHTML = html;
                    // 선택 탭에 active class 설정
                    tabs.forEach(t => t.classList.remove('active'));
                    this.classList.add('active');
                })
                .catch(err => console.error('페이지 로드 실패:', err));
        });
    });
});
