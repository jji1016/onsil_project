

//이달의 꽃 tab 메뉴---------------------------------------------------
function openTab(tabName, btn){

    let tabs = document.getElementsByClassName('m-flower-list');
    for(let i = 0; i < tabs.length; i++){
        tabs[i].style.display = "none";
    }

    let menus = document.querySelectorAll(".years>li");
    menus.forEach(list => list.classList.remove("active"));

    document.getElementById(tabName).style.display = "flex";
    if(btn){
        btn.classList.add("active");
    }
}

document.addEventListener("DOMContentLoaded", () => {
  const juneBtn = document.querySelector(".years li[onclick*='june']"); // HTML 안에서 onclick="openTab('june', …) 가 박혀 있는 li를 하나만 딱 집어 와
  openTab("june", juneBtn);
});