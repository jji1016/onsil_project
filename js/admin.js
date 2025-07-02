

//gnb tab 메뉴---------------------------------------------------
function openTab(tabName, btn){

    let tabs = document.getElementsByClassName('tab');
    for(let i = 0; i < tabs.length; i++){
        tabs[i].style.display = "none";
    }

    let menus = document.querySelectorAll(".gnb>li");
    menus.forEach(list => list.classList.remove("active"));

    document.getElementById(tabName).style.display = "block";
    if(btn){
        btn.classList.add("active");
    }
}

document.addEventListener("DOMContentLoaded", function(){
    let firstBtn = document.querySelector(".gnb>li");
    openTab("home", firstBtn);
});





//홈화면 매출요약 차트
    const ctx = document.getElementById('homeSales').getContext('2d');
    const sales = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: [
        '2024-01','2024-02', '2024-03', '2024-04', '2024-05', '2024-06', '2024-07',
        '2024-08', '2024-09', '2024-10', '2024-11', '2024-12',
        '2025-01', '2025-02', '2025-03', '2025-04', '2025-05'
        ],
        datasets: [{
        label: '월별 매출',
        data: [34, 45, 30, 55, 65, 45, 25, 42, 35, 38, 28, 60, 35, 65, 58, 50, 80],
        backgroundColor: '#A6BFA4',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false,
        // {
            
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}백만원`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출 (백만원)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0
            }
        }
        }
    }
});






//회원관리 페이지
    const members = [
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 346,
        name: "박보영",
        id: "bboyoung",
        grade: "일반회원",
        phone: "010-5678-1234",
        joined: "2025-05-01 10:12",
        history: 2,
        point: "1,500",
        login: 8
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    },
    {
        no: 345,
        name: "한지민",
        id: "jiminpro",
        grade: "구독회원",
        phone: "010-1234-5678",
        joined: "2025-04-27 16:34",
        history: 3,
        point: "2,000",
        login: 5
    }
    ];


const rowsPerPage = 9;
let currentPage = 1;

const tbody = document.getElementById("member-body"); // member-body를 가져와서 tbody 변수에 저장
const pager = document.getElementById("member-pagination");

function renderTable(page = 1) { //renderTable 함수 , 기본값 1페이지
    currentPage = page; //현재 페이지 번호 
    const start = (page - 1) * rowsPerPage; //몇번째 회원부터 보여줄지 계산
    const slice = members.slice(start, start + rowsPerPage); //회원 배열에서 보여줄 만큼 잘라서 slice에 저장함

    tbody.innerHTML = slice.map(m => `
        <tr>
        <td>${m.no}</td>
        <td>${m.name}</td>
        <td>${m.id}</td>
        <td>${m.grade}</td>
        <td>${m.phone}</td>
        <td>${m.joined}</td>
        <td>${m.history}</td>
        <td>${m.point}</td>
        <td>${m.login}</td>
        <td>
            <button class="admin_btn">수정</button>
            <button class="del_btn">삭제</button>
        </td>
        </tr>
    `).join('');
    }

    function renderPagination() { 
    const totalPages = Math.ceil(members.length / rowsPerPage);
    pager.innerHTML = '';

    pager.appendChild(makeBtn('◀', currentPage - 1, currentPage === 1));

    for(let i = 1; i <= totalPages; i++) {
        pager.appendChild(makeBtn(i, i, false, i === currentPage));
    }

    pager.appendChild(makeBtn('▶', currentPage + 1, currentPage === totalPages));
    }

    function makeBtn(label, goPage, disabled, active) {
    const btn = document.createElement('button');
    btn.textContent = label;
    btn.disabled = disabled;
    if (active) btn.classList.add('active');
    btn.addEventListener('click', () => {
        renderTable(goPage);
        renderPagination();
    });
    return btn;
    }

    window.onload = () => {
    renderTable();
    renderPagination();
    };

    window.onload = () => {
        renderTable();
        renderPagination();
    };



    
    //회원아이디 불러오기(모달창 주문내역 아이디 부분)
    document.querySelectorAll('.admin_btn').forEach((btn, index) => {
        btn.addEventListener('click', () => {
            // 버튼이 눌린 row의 ID를 가져오기!
            const clickedId = members[index].id;

            // <td class="view-user-id">에 넣기!
            document.querySelector('.view-user-id').textContent = clickedId;
        });
        });





    //회원 숫자 세기
    document.querySelector(".mem_count").textContent = members.length;


    
    //모달창 tab 메뉴---------------------------------------------------
    function openPage(tabName, btn){

        let tabs = document.getElementsByClassName('tab-cont');
        for(let i = 0; i < tabs.length; i++){
            tabs[i].style.display = "none";
        }

        let menus = document.querySelectorAll(".wrap-cont li");
        menus.forEach(list => list.classList.remove("activeM"));

        let buttons = document.querySelectorAll(".tab-btn>li");
        buttons.forEach(b => b.classList.remove("activeM"));

        document.getElementById(tabName).style.display = "block";
        if(btn){
            btn.classList.add("activeM");
        }

    }

    document.addEventListener("DOMContentLoaded", function(){
        let firstBtn = document.querySelector(".wrap-cont li");
        openPage("mem-info", firstBtn);
    });
    document.addEventListener("DOMContentLoaded", function(){
        let firstBtn = document.querySelector(".tab-btn li");
        openPage("mem-info", firstBtn);
    });


    //회원별주문내역관리 페이지
    const memOrders = [
    {
        no: 1,
        date: "2025-06-09",
        orderNo: "20250609007A",
        product: "6월의 탄생화 장미 시리즈",
        state: "결제완료",
        price: "48,900"
    },
    {
        no: 2,
        date: "2025-06-09",
        orderNo: "20250609007A",
        product: "6월의 탄생화 장미 시리즈",
        state: "결제완료",
        price: "48,900"
    }
    ];

    const memOrderBody = document.getElementById("mem-order-body");

    memOrders.forEach(order => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
        <td>${order.no}</td>
        <td>${order.date}</td>
        <td>${order.orderNo}</td>
        <td>${order.product}</td>
        <td>${order.state}</td>
        <td>${order.price}</td>
    `;

    memOrderBody.appendChild(tr);
    });












//재고관리 페이지

//재고관리 탭메뉴
function changeTab(tabName, btn){

    let stocks = document.getElementsByClassName('st_tab');
    for(let i = 0; i < stocks.length; i++){
        stocks[i].style.display = "none";
    }
    
    let cont = document.querySelectorAll(".stock_btn>button");
    cont.forEach(list => list.classList.remove("btn_on"));

    document.getElementsByClassName(tabName)[0].style.display = "block";
    if(btn){
        btn.classList.add("btn_on");
    }
}

document.addEventListener("DOMContentLoaded", function(){
    let firstBtn = document.querySelector(".stock_btn>button");
    changeTab("desc_stock", firstBtn);
});



//재고------------------------
    const stocks = [
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB08",
        name: "유럽피언 꽃구독 M",
        category: "절화",
        unit: "다발",
        count: "42",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    },
    {
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        unit: "다발",
        count: "52",
        storage: "장항동 온실하우스"
    }
    ];

const stocksPerPage = 8;
let currentStockPage = 1;

const sbody = document.getElementById("stock-body");
const stockPager = document.getElementById("stock-pagination");

function renderStockTable(page = 1) {
    currentStockPage = page;
    const start = (page - 1) * stocksPerPage;
    const slice = stocks.slice(start, start + stocksPerPage);

    sbody.innerHTML = slice.map(stock => `
        <tr>
        <td>${stock.code}</td>
        <td>${stock.name}</td>
        <td>${stock.category}</td>
        <td>${stock.unit}</td>
        <td>${stock.count}</td>
        <td>${stock.storage}</td>
        <td>
            <button class="admin_btn">관리</button>
            <button class="del_btn">삭제</button>
        </td>
        </tr>
    `).join('');
    }

    function renderStockPagination() {
    const totalPages = Math.ceil(stocks.length / stocksPerPage);
    stockPager.innerHTML = '';

    stockPager.appendChild(makeStockBtn('◀', currentStockPage - 1, currentStockPage === 1));

    for(let i = 1; i <= totalPages; i++) {
        stockPager.appendChild(makeStockBtn(i, i, false, i === currentStockPage));
    }

    stockPager.appendChild(makeStockBtn('▶', currentStockPage + 1, currentStockPage === totalPages));
    }

    function makeStockBtn(label, goPage, disabled, active) {
    const btn = document.createElement('button');
    btn.textContent = label;
    btn.disabled = disabled;
    if (active) btn.classList.add('active');
    btn.addEventListener('click', () => {
        renderStockTable(goPage);
        renderStockPagination();
    });
    return btn;
    }

    









    
    //입고------------------------
    const stocksIn = [
    {
        date: "2025-05-29",
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        count: "52",
        storage: "조은화원",
        director: "김나라"
    },
    {
        date: "2025-05-29",
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        count: "52",
        storage: "조은화원",
        director: "김나라"
    }
    ];
    const stocksInPerPage = 8;
    let currentStockInPage = 1;

    const sInbody = document.getElementById("stock_in-body");
    const stockInPager = document.getElementById("stockIn-pagination");

    function renderStockInTable(page = 1) {
        currentStockInPage = page;
        const start = (page - 1) * stocksInPerPage;
        const slice = stocksIn.slice(start, start + stocksInPerPage);

        sInbody.innerHTML = slice.map(stocksIn => `
            <tr>
            <td>${stocksIn.date}</td>
            <td>${stocksIn.code}</td>
            <td>${stocksIn.name}</td>
            <td>${stocksIn.category}</td>
            <td>${stocksIn.count}</td>
            <td>${stocksIn.storage}</td>
            <td>${stocksIn.director}</td>
            <td>
                <button class="admin_btn">관리</button>
                <button class="del_btn">삭제</button>
            </td>
            </tr>
        `).join('');
        }

        function renderStockInPagination() {
        const totalInPages = Math.ceil(stocksIn.length / stocksInPerPage);
        stockInPager.innerHTML = '';

        stockInPager.appendChild(makeStockBtn('◀', currentStockInPage - 1, currentStockInPage === 1));

        for(let i = 1; i <= totalInPages; i++) {
            stockInPager.appendChild(makeStockBtn(i, i, false, i === currentStockInPage));
        }

        stockInPager.appendChild(makeStockBtn('▶', currentStockInPage + 1, currentStockInPage === totalInPages));
        }

        function makeStockInBtn(label, goPage, disabled, active) {
        const btn = document.createElement('button');
        btn.textContent = label;
        btn.disabled = disabled;
        if (active) btn.classList.add('active');
        btn.addEventListener('click', () => {
            renderStockInTable(goPage);
            renderStockInPagination();
        });
        return btn;
    }

    // 페이지 로딩 시 테이블 다 렌더링
    window.onload = () => {
        renderTable();
        renderPagination();
        renderStockTable();
        renderStockPagination();
        renderStockInTable();
        renderStockInPagination();
    };
    









    //출고------------------------
    const stocksOut = [
    {
        date: "2025-05-29",
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        count: "2",
        storage: "구독회원",
        director: "김나라"
    },
    {
        date: "2025-05-29",
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        count: "1",
        storage: "구독회원",
        director: "김나라"
    }
    ];

    const stocksOutPerPage = 8;
    let currentStockOutPage = 1;

    const sOutbody = document.getElementById("stock_out-body");
    const stockOutPager = document.getElementById("stockOut-pagination");

    function renderStockOutTable(page = 1) {
        currentStockOutPage = page;
        const start = (page - 1) * stocksOutPerPage;
        const slice = stocksOut.slice(start, start + stocksOutPerPage);

        sOutbody.innerHTML = slice.map(stockOut => `
            <tr>
            <td>${stockOut.date}</td>
            <td>${stockOut.code}</td>
            <td>${stockOut.name}</td>
            <td>${stockOut.category}</td>
            <td>${stockOut.count}</td>
            <td>${stockOut.storage}</td>
            <td>${stockOut.director}</td>
            <td>
                <button class="admin_btn">관리</button>
                <button class="del_btn">삭제</button>
            </td>
            </tr>
        `).join('');
        }

        function renderStockOutPagination() {
        const totalOutPages = Math.ceil(stocksOut.length / stocksOutPerPage);
        stockOutPager.innerHTML = '';

        stockOutPager.appendChild(makeStockOutBtn('◀', currentStockOutPage - 1, currentStockOutPage === 1));

        for(let i = 1; i <= totalOutPages; i++) {
            stockOutPager.appendChild(makeStockOutBtn(i, i, false, i === currentStockOutPage));
        }

        stockOutPager.appendChild(makeStockOutBtn('▶', currentStockOutPage + 1, currentStockOutPage === totalOutPages));
        }

        function makeStockOutBtn(label, goPage, disabled, active) {
        const btn = document.createElement('button');
        btn.textContent = label;
        btn.disabled = disabled;
        if (active) btn.classList.add('active');
        btn.addEventListener('click', () => {
            renderStockOutTable(goPage);
            renderStockOutPagination();
        });
        return btn;
    }

    window.addEventListener('DOMContentLoaded', () => {
        renderStockOutTable();
        renderStockOutPagination();
    });






//매출관리 매출요약 차트

function openChart(chartName, btn){ 
    // 모든 차트 숨기기
    let allCharts = document.querySelectorAll(".charts, .c_charts"); 
    allCharts.forEach(chart => chart.style.display = "none");

    // 버튼 상태 초기화
    let buttonAll = document.querySelectorAll(".sale_title button"); 
    buttonAll.forEach(button => button.classList.remove("on")); 

    // 선택된 차트 보여주기 (일반 + 카테고리)
    document.getElementById(chartName).style.display = "block"; 
    
    let categoryChartName = "c_" + chartName; // 예: c_day_sales
    let categoryChart = document.getElementById(categoryChartName);
    if (categoryChart) {
        categoryChart.style.display = "block";
    }

    // 버튼 on 표시
    if(btn){  
        btn.classList.add("on"); 
    }
}

document.addEventListener("DOMContentLoaded", function(){ 
    let firstBtn = document.querySelector(".sale_title button"); 
    openChart("day_sales", firstBtn); 
});


//day chart
    const ddd = document.getElementById('day_sales').getContext('2d');
    const daySales = new Chart(ddd, {
    type: 'bar',
    data: {
        labels: [
        '2025-05-12', '2025-05-13', '2025-05-14', '2025-05-15', '2025-05-16',
        '2025-05-17', '2025-05-18', '2025-05-19', '2025-05-20', '2025-05-21','2025-05-22', '2025-05-23', '2025-05-24', '2025-05-25', '2025-05-26',
        '2025-05-27', '2025-05-28', '2025-05-29', '2025-05-30', '2025-05-31'
        ],
        datasets: [{
        label: '일별 매출',
        data: [180, 201, 196, 212, 232, 134, 145, 130, 155, 165, 145, 225, 142, 235, 238, 218, 160, 235, 265, 258],
        backgroundColor: '#A6BFA4',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false,
        // {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}만원`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출 (만원)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0,
            autoSkip: false
            }
        }
        }
    }
});

// week 차트
    const www = document.getElementById('week_sales').getContext('2d');
    const weekSales = new Chart(www, {
    type: 'bar',
    data: {
        labels: [
        '2월3주','2월4주', '2월5주', '3월1주', '3월2주', '3월3주', '3월4주',
        '3월5주', '4월1주', '4월2주', '4월3주', '4월4주',
        '4월5주', '5월1주', '5월2주', '5월3주', '5월4주', '5월5주','6월1주'
        ],
        datasets: [{
        label: '주별 매출',
        data: [34, 45, 30, 55, 65, 45, 25, 42, 35, 38, 28, 60, 35, 65, 58, 50, 80],
        backgroundColor: '#A6BFA4',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false,
        // {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}백만원`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출 (백만원)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0
            }
        }
        }
    }
});




// month 차트
    const mmm = document.getElementById('month_sales').getContext('2d');
    const monthSales = new Chart(mmm, {
        type: 'bar',
        data: {
            labels: [
                '2024-01','2024-02', '2024-03', '2024-04', '2024-05', '2024-06',
                '2024-07','2024-08', '2024-09', '2024-10', '2024-11', '2024-12',
                '2025-01', '2025-02', '2025-03', '2025-04', '2025-05'
            ],
            datasets: [{
                label: '월별 매출',
                data: [34, 45, 30, 55, 65, 45, 25, 42, 35, 38, 28, 60, 35, 65, 58, 50, 80],
                backgroundColor: '#A6BFA4',
                borderRadius: 4,
                barThickness: 30
            }]
        },
        options: {
            responsive: true,
            animations: false,
        //     {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `${context.parsed.y}백만원`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: '매출 (백만원)'
                    }
                },
                x: {
                    ticks: {
                        maxRotation: 0,
                        minRotation: 0
                    }
                }
            }
        }
    });






//카테고리별 매출비중 차트
//day chart
    const cddd = document.getElementById('c_day_sales').getContext('2d');
    const cDaySales = new Chart(cddd, {
    type: 'bar',
    data: {
        labels: [
        '작약', '수국', '장미', '카네이션', '몬스테라', '프리지아', '골든볼', '알스트로메리아', '리시안셔스'
        , '플로랄파라다이스', '크림데이즈 꽃다발', '소프트블러시 꽃다발', '미드나잇 문 꽃다발', '부자재'
        ],
        datasets: [{
        label: '카테고리별 매출비중',
        data: [11, 11, 8, 5, 7, 6, 10, 4, 8 ,6, 9, 7, 5, 3 ],
        backgroundColor: '#95A294',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false, 
        // {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}%`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출비중 (%)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0,
            autoSkip: false
            }
        }
        }
    }
});

//week chart
    const wddd = document.getElementById('c_week_sales').getContext('2d');
    const cWeekSales = new Chart(wddd, {
    type: 'bar',
    data: {
        labels: [
        '작약', '수국', '장미', '카네이션', '몬스테라', '프리지아', '골든볼', '알스트로메리아', '리시안셔스'
        , '플로랄파라다이스', '크림데이즈 꽃다발', '소프트블러시 꽃다발', '미드나잇 문 꽃다발', '부자재'
        ],
        datasets: [{
        label: '카테고리별 매출비중',
        data: [ 4, 8 ,6, 9, 7, 5, 3, 11, 11, 8, 5, 7, 6, 10 ],
        backgroundColor: '#95A294',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false,
        // {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}%`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출비중 (%)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0,
            autoSkip: false
            }
        }
        }
    }
});



//month chart
    const mddd = document.getElementById('c_month_sales').getContext('2d');
    const cMonthSales = new Chart(mddd, {
    type: 'bar',
    data: {
        labels: [
        '작약', '수국', '장미', '카네이션', '몬스테라', '프리지아', '골든볼', '알스트로메리아', '리시안셔스'
        , '플로랄파라다이스', '크림데이즈 꽃다발', '소프트블러시 꽃다발', '미드나잇 문 꽃다발', '부자재'
        ],
        datasets: [{
        label: '카테고리별 매출비중',
        data: [ 7, 5, 3, 11, 11, 8, 4, 8, 5, 7, 6, 10, 6, 9 ],
        backgroundColor: '#95A294',
        borderRadius: 4,
        barThickness: 30
        }]
    },
    options: {
        responsive: true,
        animations: false,
        // {
        //     x: {
        //         duration: 0
        //     },
        //     y: {
        //         from: ctx => ctx.chart.scales.y.getPixelForValue(0),
        //         to: ctx => ctx.chart.scales.y.getPixelForValue(ctx.raw),
        //         delay: ctx => ctx.index * 100,
        //         type: 'number',
        //         easing: 'easeOutQuart'
        //     }
        // },
        plugins: {
        legend: {
            display: false // label 숨기고 싶으면 true로 바꿔도 돼
        },
        tooltip: {
            callbacks: {
            label: function(context) {
                return `${context.parsed.y}%`;
            }
            }
        }
        },
        scales: {
        y: {
            beginAtZero: true,
            title: {
            display: true,
            text: '매출비중 (%)'
            }
        },
        x: {
            ticks: {
            maxRotation: 0,
            minRotation: 0,
            autoSkip: false
            }
        }
        }
    }
});







//주문내역관리 페이지
    const orders = [
    {
        no: 2,
        date: "2025-06-09",
        orderNo: "20250609007A",
        product: "6월의 탄생화 장미 시리즈",
        count : "1",
        fee: "0",
        state: "결제완료",
        id: "jiminpro",
        name: "한지민",
        delName: "이석훈",
        price: "48,900",
        pay: "신용카드"
    },
    {
        no: 1,
        date: "2025-06-09",
        orderNo: "20250609007A",
        product: "6월의 탄생화 장미 시리즈",
        count : "1",
        fee: "0",
        state: "결제완료",
        id: "jiminpro",
        name: "한지민",
        delName: "이석훈",
        price: "48,900",
        pay: "신용카드"
    },
    {
        no: 1,
        date: "2025-06-09",
        orderNo: "20250609007A",
        product: "6월의 탄생화 장미 시리즈",
        count : "1",
        fee: "0",
        state: "결제완료",
        id: "jiminpro",
        name: "한지민",
        delName: "이석훈",
        price: "48,900",
        pay: "신용카드"
    }
    ];


    const orderPerPage = 7;
    let currentOrderPage = 1;

    const orderBody = document.getElementById("order-body");
    const orderPager = document.getElementById("order-pagination");

    function renderOrderTable(page = 1) {
        currentOrderPage = page;
        const start = (page - 1) * orderPerPage;
        const slice = orders.slice(start, start + orderPerPage);

        orderBody.innerHTML = slice.map(order => `
            <tr>
            <td>${order.no}</td>
            <td>${order.date}</td>
            <td  class="order_btn">${order.orderNo}</td>
            <td>${order.product}</td>
            <td>${order.count}</td>
            <td>${order.fee}</td>
            <td>${order.state}</td>
            <td>${order.id}</td>
            <td>${order.name}</td>
            <td>${order.delName}</td>
            <td>${order.price}</td>
            <td>${order.pay}</td>
            </tr>
        `).join('');
        }

        function renderOrderPagination() {
        const totalorderPages = Math.ceil(orders.length / orderPerPage);
        orderPager.innerHTML = '';

        orderPager.appendChild(makeOrderBtn('◀', currentOrderPage - 1, currentOrderPage === 1));

        for(let i = 1; i <= totalorderPages; i++) {
            orderPager.appendChild(makeOrderBtn(i, i, false, i === currentOrderPage));
        }

        orderPager.appendChild(makeOrderBtn('▶', currentOrderPage + 1, currentOrderPage === totalorderPages));
        }

        function makeOrderBtn(label, goPage, disabled, active) {
        const btn = document.createElement('button');
        btn.textContent = label;
        btn.disabled = disabled;
        if (active) btn.classList.add('active');
        btn.addEventListener('click', () => {
            renderOrderTable(goPage);
            renderOrderPagination();
        });
        return btn;
    }

    window.addEventListener('DOMContentLoaded', () => {
        renderOrderTable();
        renderOrderPagination();
    });







//주문내역 모달창  주문정보영역--------------
    const orderD = [ 
    {
        no: "2505230098",
        date: "2025-06-09 14:05",
        id: "figma",
        name: "유재석",
        product: "[6월의 탄생화] 사랑을 담은 수국 꽃다발",
        state : "결제완료",
        price: "68,600",
        discount: "2,000",
        sum: "66,800",
        pay: "신용카드"
    }
];

const orderInforBody = document.getElementById("orderInforBody");

orderD.forEach(order => {
    // no, date
    let tr1 = document.createElement("tr");
    tr1.innerHTML = `
        <td>주문번호</td><td>${order.no}</td>
        <td>주문일시</td><td>${order.date}</td>
    `;
    orderInforBody.appendChild(tr1);

    // id, name
    let tr2 = document.createElement("tr");
    tr2.innerHTML = `
        <td>아이디</td><td>${order.id}</td>
        <td>이름</td><td>${order.name}</td>
    `;
    orderInforBody.appendChild(tr2);

    // product, state
    let tr3 = document.createElement("tr");
    tr3.innerHTML = `
        <td>상품명</td><td>${order.product}</td>
        <td>상태</td><td>${order.state}</td>
    `;
    orderInforBody.appendChild(tr3);

    // price, discount
    let tr4 = document.createElement("tr");
    tr4.innerHTML = `
        <td>가격</td><td>${order.price}</td>
        <td>할인</td><td>${order.discount}</td>
    `;
    orderInforBody.appendChild(tr4);

    // sum, pay
    let tr5 = document.createElement("tr");
    tr5.innerHTML = `
        <td>총합</td><td>${order.sum}</td>
        <td>결제수단</td><td>${order.pay}</td>
    `;
    orderInforBody.appendChild(tr5);
});



//주문내역 모달창  배송정보영역--------------
    const delivery = [ 
    {
        name: "한지민",
        phone: "010-1234-5678",
        adress: "경기도 고양시 장항로 111, 101동 1234호 (온실마을, 온실아파트)",
        msg: "부재시 문앞에 놔주세요."
    }
];

const deliBody = document.getElementById("deliBody");

delivery.forEach(info => {
    // name, phone
    let tr1 = document.createElement("tr");
    tr1.innerHTML = `
        <td>수령인</td><td>${info.name}</td>
        <td>전화번호</td><td>${info.phone}</td>
    `;
    deliBody.appendChild(tr1);

    // adress
    let tr2 = document.createElement("tr");
    tr2.innerHTML = `
        <td>배송주소</td><td colspan="3">${info.adress}</td>
    `;
    deliBody.appendChild(tr2);

    // msg
    let tr3 = document.createElement("tr");
    tr3.innerHTML = `
        <td>배송메세지</td><td colspan="3">${info.msg}</td>
    `;
    deliBody.appendChild(tr3);

});









// 상품관리 페이지--------------------------------

    
    const items = [
    {
        no: "1",
        code: "AE202050000AB07",
        name: "유럽피언 꽃구독 S",
        category: "절화",
        date: "2024-12-11",
        dateR: "2025-06-10",
        price: "36,000",
        sellPrice: "52,000",
    },
    {
        no: "2",
        code: "AE202040000CF12",
        name: "유럽피언 꽃구독 M",
        category: "절화",
        date: "2024-10-11",
        dateR: "2025-05-10",
        price: "42,000",
        sellPrice: "58,000",
    }
    ];

        const itemPerPage = 7;
        let currentItemPage = 1;

        const itemBody = document.getElementById("items_body");
        const itemPager = document.getElementById("item-pagination");

        function renderItemTable(page = 1) {
            currentItemPage = page;
            const start = (page - 1) * itemPerPage;
            const slice = items.slice(start, start + itemPerPage);

            itemBody.innerHTML = slice.map(itemsIn => `
                <tr>
                <td>${itemsIn.no}</td>
                <td>${itemsIn.code}</td>
                <td>${itemsIn.name}</td>
                <td>${itemsIn.category}</td>
                <td>${itemsIn.date}</td>
                <td>${itemsIn.dateR}</td>
                <td>${itemsIn.price}</td>
                <td>${itemsIn.sellPrice}</td>
                <td>
                <button class="modi_btn">수정</button>
                <button class="del_btn">삭제</button>
                </td>
                </tr>
            `).join('');
            }

            function renderItemPagination() {
            const totalItemPages = Math.ceil(items.length / itemPerPage);
            itemPager.innerHTML = '';

            itemPager.appendChild(makeItemBtn('◀', currentItemPage - 1, currentItemPage === 1));

            for(let i = 1; i <= totalItemPages; i++) {
                itemPager.appendChild(makeItemBtn(i, i, false, i === currentItemPage));
            }

            itemPager.appendChild(makeItemBtn('▶', currentItemPage + 1, currentItemPage === totalItemPages));
            }

            function makeItemBtn(label, goPage, disabled, active) {
            const btn = document.createElement('button');
            btn.textContent = label;
            btn.disabled = disabled;
            if (active) btn.classList.add('active');
            btn.addEventListener('click', () => {
                renderItemTable(goPage);
                renderItemPagination();
            });
            return btn;
        }

        window.addEventListener('DOMContentLoaded', () => {
            renderItemTable();
            renderItemPagination();
    });



    //상품수량 카운트
    document.querySelector(".item_count").textContent = items.length;






    