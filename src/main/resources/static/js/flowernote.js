//이달의 꽃 tab 메뉴 및 API 연동 기능

// 전역 변수
let currentSearchResults = [];
let isSearchMode = false;

function openTab(tabName, btn) {
    let tabs = document.getElementsByClassName('m-flower-list');
    for (let i = 0; i < tabs.length; i++) {
        tabs[i].style.display = "none";
    }
    let menus = document.querySelectorAll(".years>li");
    menus.forEach(list => list.classList.remove("active"));
    document.getElementById(tabName).style.display = "flex";
    if (btn) btn.classList.add("active");
    isSearchMode = false;
    hideSearchResults();
    loadFlowersByMonth(tabName);
}

function loadFlowersByMonth(monthName) {
    const monthMap = {
        'january': 1, 'february': 2, 'march': 3, 'april': 4,
        'may': 5, 'june': 6, 'july': 7, 'august': 8,
        'september': 9, 'october': 10, 'november': 11, 'december': 12
    };
    const monthNumber = monthMap[monthName.toLowerCase()];
    if (!monthNumber) return;
    fetch(`/api/flowers/month/${monthNumber}`)
        .then(response => response.json())
        .then(data => {
            updateFlowerListInExistingStructure(monthName, data.flowers);
        })
        .catch(error => {
            console.error('꽃 정보 로드 실패:', error);
        });
}

function updateFlowerListInExistingStructure(monthName, flowers) {
    const container = document.getElementById(monthName);
    if (!container) return;
    const existingItems = container.querySelectorAll('li');
    const maxItems = Math.min(flowers.length, 6);
    for (let i = 0; i < 6; i++) {
        let listItem;
        if (i < existingItems.length) {
            listItem = existingItems[i];
        } else {
            listItem = createNewListItem(i + 1);
            container.appendChild(listItem);
        }
        if (i < maxItems) {
            updateListItemWithFlowerData(listItem, flowers[i]);
            listItem.style.display = 'block';
        } else {
            listItem.style.display = 'none';
        }
    }
}

function createNewListItem(index) {
    const li = document.createElement('li');
    li.className = `list${index}`;
    li.innerHTML = `
            <div class="flower-image"></div>
            <div class="flower-name">
                <p class="name-txt">꽃이름</p>
            </div>
        `;
    return li;
}

function updateListItemWithFlowerData(listItem, flower) {
    const flowerImage = listItem.querySelector('.flower-image');
    const nameText = listItem.querySelector('.name-txt');
    if (nameText) {
        nameText.textContent = flower.flowerName;
    }
    if (flowerImage) {
        const imageUrl = flower.imageUrl || (flower.image ? `/upload/products/${flower.image}` : '/upload/products/default.jpg');
        flowerImage.style.backgroundImage = `url('${imageUrl}')`;
        flowerImage.style.backgroundSize = 'cover';
        flowerImage.style.backgroundPosition = 'center';
        flowerImage.style.backgroundRepeat = 'no-repeat';
    }
    listItem.onclick = () => showFlowerDetail(flower.productId);
    listItem.style.cursor = 'pointer';
}

// 검색 기능 초기화 (이벤트 연결 확실히!)
function initializeSearchFeature() {
    let searchInput = document.querySelector('.search-area input');
    if (searchInput) {
        // Enter 입력 시 검색
        searchInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                performSearch(this.value);
            }
        });
        // 검색 버튼 클릭 시 검색 (a 태그의 기본 동작 막기)
        const searchButton = document.querySelector('.search-area li.search-f a');
        if (searchButton) {
            searchButton.onclick = function (event) {
                event.preventDefault(); // 새로고침 방지!
                performSearch(searchInput.value);
            };
            searchButton.style.cursor = 'pointer';
        }
    }
}

function performSearch(keyword) {
    if (!keyword || !keyword.trim()) {
        alert('검색어를 입력해주세요.');
        return;
    }
    // 검색 키워드 쿼리 보내기
    fetch(`/api/flowers/search?keyword=${encodeURIComponent(keyword.trim())}`)
        .then(response => response.json())
        .then(data => {
            console.log("검색 결과 리스트 확인");
            console.log(data)
            currentSearchResults = data;
            isSearchMode = true;
            showSearchResults(data);
        })
        .catch(error => {
            console.error('검색 실패:', error);
            alert('검색 중 오류가 발생했습니다.');
        });
}

function showSearchResults(flowers) {
    let menus = document.querySelectorAll(".years>li");
    menus.forEach(list => list.classList.remove("active"));
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        if (flowers.length === 0) {
            showNoResultsMessage();
            return;
        }
        // 검색 결과 하나만 랜덤 조회
        // flowers가 검색 결과 리스트
        // 랜덤 인덱스 생성 (0 ~ flowers.length-1)
        const randomIndex = Math.floor(Math.random() * flowers.length);
        // 랜덤으로 뽑은 꽃 데이터
        const randomFlower = flowers[randomIndex];
        // 검색 결과에 표시
        displaySearchResultItem(searchResult, randomFlower);
        searchResult.style.display = 'block';
        // if (flowers.length > 1) {
        //     showAdditionalSearchResults(flowers.slice(1));
        // }
    }
}

// 검색 출력 공간 할당 및 정보 주입
function displaySearchResultItem(container, flower) {
    const imgArea = container.querySelector('.img-area');
    const flowerName = container.querySelector('.name-txt');
    if (imgArea) {
        const imageUrl = flower.imageUrl || (flower.image ? `/upload/products/${flower.image}` : '/upload/products/default.jpg');
        imgArea.style.backgroundImage = `url('${imageUrl}')`;
        imgArea.style.backgroundSize = 'cover';
        imgArea.style.backgroundPosition = 'center';
        imgArea.style.backgroundRepeat = 'no-repeat';
    }
    if (flowerName) {
        flowerName.textContent = flower.flowerName;
    }
    container.onclick = () => showFlowerDetail(flower.productId);
}

// 검색 결과 전체 리스트
// function showAdditionalSearchResults(flowers) {
//     const tempContainer = document.getElementById('june');
//     if (tempContainer) {
//         tempContainer.style.display = 'flex';
//         const maxItems = Math.min(flowers.length, 6);
//         const existingItems = tempContainer.querySelectorAll('li');
//         for (let i = 0; i < 6; i++) {
//             let listItem;
//             if (i < existingItems.length) {
//                 listItem = existingItems[i];
//             } else {
//                 listItem = createNewListItem(i + 1);
//                 tempContainer.appendChild(listItem);
//             }
//             if (i < maxItems) {
//                 updateListItemWithFlowerData(listItem, flowers[i]);
//                 listItem.style.display = 'block';
//             } else {
//                 listItem.style.display = 'none';
//             }
//         }
//     }
// }

function showNoResultsMessage() {
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        const imgArea = searchResult.querySelector('.img-area');
        const flowerName = searchResult.querySelector('.name-txt');
        if (imgArea) {
            imgArea.style.backgroundImage = 'none';
            imgArea.innerHTML = '<div style="display:flex;align-items:center;justify-content:center;height:100%;color:#666;">검색 결과가 없습니다</div>';
        }
        if (flowerName) {
            flowerName.textContent = '검색 결과 없음';
        }
        searchResult.style.display = 'block';
        searchResult.onclick = null;
    }
}

function hideSearchResults() {
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        searchResult.style.display = 'none';
    }
}

function showFlowerDetail(productId) {
    fetch(`/api/flowers/detail/${productId}`)
        .then(response => response.json())
        .then(flower => {
            createAndShowModal(flower);
        })
        .catch(error => {
            console.error('상세정보 로드 실패:', error);
            alert('상세정보를 불러올 수 없습니다.');
        });
}

function createAndShowModal(flower) {
    const existingModal = document.getElementById('flowerDetailModal');
    if (existingModal) {
        existingModal.remove();
    }
    const modal = document.createElement('div');
    modal.id = 'flowerDetailModal';
    modal.style.cssText = `
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
        `;
    const imageUrl = flower.imageUrl || (flower.image ? `/upload/products/${flower.image}` : '/upload/products/default.jpg');
    modal.innerHTML = `
            <div style="
                background-color: white;
                padding: 30px;
                border-radius: 15px;
                max-width: 600px;
                max-height: 80vh;
                overflow-y: auto;
                position: relative;
                margin: 20px;
            ">
                <span onclick="closeFlowerModal()" style="
                    position: absolute;
                    right: 15px;
                    top: 15px;
                    font-size: 24px;
                    cursor: pointer;
                    color: #aaa;
                ">&times;</span>
                <div style="text-align: center; margin-bottom: 20px;">
                    <img src="${imageUrl}" alt="${flower.flowerName}" 
                         style="width: 100%; max-width: 300px; height: 200px; object-fit: cover; border-radius: 10px;"
                         onerror="this.src='/upload/products/default.png'">
                </div>
                <h2 style="color: #8b9773; text-align: center; margin-bottom: 20px;">${flower.flowerName}</h2>
                <div style="line-height: 1.6;">
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">꽃말:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.flowLang || '정보가 없습니다.'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">꽃 정보:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.flowerInfo || '정보가 없습니다.'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">용도:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.fUse || flower.fuse || flower.f_use || '정보가 없습니다.'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">재배법:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.fGrow || flower.fgrow || flower.f_grow || '정보가 없습니다.'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">꽃 종류:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.fType || flower.ftype || flower.f_type || '정보가 없습니다.'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">가격:</strong>
                        <p style="margin: 5px 0; color: #333;">₩${flower.price ? flower.price.toLocaleString() : '가격미정'}</p>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <strong style="color: #4B4A45;">계절:</strong>
                        <p style="margin: 5px 0; color: #333;">${flower.fmonth || flower.fMonth}월의 꽃</p>
                    </div>
                </div>
            </div>
        `;
    modal.onclick = function (event) {
        if (event.target === modal) {
            closeFlowerModal();
        }
    };
    document.body.appendChild(modal);
}

function closeFlowerModal() {
    const modal = document.getElementById('flowerDetailModal');
    if (modal) {
        modal.remove();
    }
}

document.addEventListener('keydown', function (event) {
    if (event.key === 'Escape') {
        closeFlowerModal();
    }
});

function clearSearch() {
    const searchInput = document.querySelector('.search-area input');
    if (searchInput) {
        searchInput.value = '';
    }
    isSearchMode = false;
    hideSearchResults();
    const juneBtn = document.querySelector(".years li[onclick*='june']");
    if (juneBtn) {
        openTab("june", juneBtn);
    }
}

function initializeFlowerNote() {
    initializeSearchFeature();
    const juneBtn = document.querySelector(".years li[onclick*='june']");
    if (juneBtn) {
        openTab("june", juneBtn);
    }
    const tagSpans = document.querySelectorAll('.tag span:not(.tag-title)');
    tagSpans.forEach(span => {
        span.onclick = () => {
            const keyword = span.textContent.trim();
            if (keyword) {
                const searchInput = document.querySelector('.search-area input');
                if (searchInput) {
                    searchInput.value = keyword;
                }
                performSearch(keyword);
            }
        };
    });
}

document.addEventListener("DOMContentLoaded", () => {
    initializeFlowerNote();
    initializeSearchFeature();

    // 꼬리조팝나무 클릭 시 기존 모달 구조로 상세정보 띄우기
    const searchResultDiv = document.querySelector('.search-result');
    if (searchResultDiv) {
        searchResultDiv.onclick = function () {
            const defaultFlower = {
                productId: 15506,
                flowerName: "꼬리조팝나무",
                image: "15506_ggorijopabnamu.jpg",
                imageUrl: "/upload/products/15506_ggorijopabnamu.jpg",
                flowLang: "기다림, 인내",
                flowerInfo: "꼬리조팝나무는 초여름에 하얀 꽃이 피는 관목입니다.",
                fUse: "조경, 관상용",
                fGrow: "양지, 배수가 잘 되는 토양",
                fType: "관목",
                price: 12000,
                fmonth: 6
            };
            // 기존 모달 구조 사용
            openFlowerModal(defaultFlower);
        };
    }
});

// 기존 모달 구조를 활용하는 함수 추가
function openFlowerModal(flower) {
    const modal = document.querySelector('.modal');
    if (!modal) return;
    modal.style.display = 'block';
    const modalCont = modal.querySelector('.wrap-modal-cont .modal-cont');
    if (!modalCont) return;
    modalCont.innerHTML = `
        <div style="text-align:center;padding:20px;">
            <img src="${flower.imageUrl}" alt="${flower.flowerName}" style="max-width:100%;max-height:300px;border-radius:10px;">
            <h2 style="margin:15px 0 5px;">${flower.flowerName}</h2>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">꽃말:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.flowLang || '정보가 없습니다.'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">꽃 정보:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.flowerInfo || '정보가 없습니다.'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">용도:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.fUse || '정보가 없습니다.'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">재배법:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.fGrow || '정보가 없습니다.'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">꽃 종류:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.fType || '정보가 없습니다.'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">가격:</strong>
                <p style="margin: 5px 0; color: #333;">₩${flower.price ? flower.price.toLocaleString() : '가격미정'}</p>
            </div>
            <div style="margin-bottom: 15px;">
                <strong style="color: #4B4A45;">계절:</strong>
                <p style="margin: 5px 0; color: #333;">${flower.fmonth || flower.fMonth}월의 꽃</p>
            </div>
        </div>
    `;
    // 닫기 버튼
    const closeBtn = modal.querySelector('.close');
    if (closeBtn) closeBtn.onclick = () => { modal.style.display = 'none'; };
}

window.clearSearch = clearSearch;
window.showFlowerDetail = showFlowerDetail;
window.closeFlowerModal = closeFlowerModal;
