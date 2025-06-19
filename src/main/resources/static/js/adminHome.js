document.addEventListener("DOMContentLoaded", function(){
    let firstBtn = document.querySelector(".gnb>li");
    openTab("home", firstBtn);
});

/*
document.addEventListener("DOMContentLoaded", function () {
    const tabContent = document.getElementById("tab-content");
    const tabs = document.querySelectorAll(".gnb li");

    function loadTab(tabName, button) {
        // UI 업데이트
        tabs.forEach(tab => tab.classList.remove("active"));
        button.classList.add("active");

        // Ajax로 HTML fragment 요청
        fetch(`/admin/${tabName}`)
            .then(response => {
                if (!response.ok) throw new Error("탭 데이터를 불러오지 못했습니다.");
                return response.text();
            })
            .then(html => {
                tabContent.innerHTML = html;
            })
            .catch(error => {
                tabContent.innerHTML = `<p style="color:red;">탭 로딩 실패</p>`;
                console.error(error);
            });
    }

    tabs.forEach(tab => {
        tab.addEventListener("click", function () {
            const tabName = this.dataset.tab;
            loadTab(tabName, this);
        });
    });

    // 첫 탭 자동 로딩
    const firstTab = tabs[0];
    if (firstTab) {
        loadTab(firstTab.dataset.tab, firstTab);
    }
});
*/



