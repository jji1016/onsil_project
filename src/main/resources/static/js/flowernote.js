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

    if (btn) {
        btn.classList.add("active");
    }

    // 검색 모드 해제
    isSearchMode = false;
    hideSearchResults();

    // 동적으로 해당 월의 꽃 정보 로드
    loadFlowersByMonth(tabName);
}

// 월별 꽃 정보를 서버에서 가져와서 기존 HTML 구조에 삽입
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
            // 에러 시 기존 정적 데이터 유지
        });
}

// 기존 HTML li 구조를 유지하면서 내용만 동적으로 교체
function updateFlowerListInExistingStructure(monthName, flowers) {
    const container = document.getElementById(monthName);
    if (!container) return;

    // 기존 li 요소들 가져오기
    const existingItems = container.querySelectorAll('li');

    // 최대 6개까지만 처리 (기존 구조 유지)
    const maxItems = Math.min(flowers.length, 6);

    for (let i = 0; i < 6; i++) {
        let listItem;

        // 기존 li가 있으면 재사용, 없으면 새로 생성
        if (i < existingItems.length) {
            listItem = existingItems[i];
        } else {
            listItem = createNewListItem(i + 1);
            container.appendChild(listItem);
        }

        if (i < maxItems) {
            // 꽃 데이터로 업데이트
            updateListItemWithFlowerData(listItem, flowers[i]);
            listItem.style.display = 'block';
        } else {
            // 데이터가 없으면 숨김
            listItem.style.display = 'none';
        }
    }
}

// 새로운 li 요소 생성 (기존 구조와 동일)
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

// li 요소를 꽃 데이터로 업데이트
function updateListItemWithFlowerData(listItem, flower) {
    const flowerImage = listItem.querySelector('.flower-image');
    const nameText = listItem.querySelector('.name-txt');

    // 꽃 이름 업데이트
    if (nameText) {
        nameText.textContent = flower.flowerName;
    }

    // 이미지 업데이트 (CSS background-image 방식 유지)
    if (flowerImage) {
        const imageUrl = flower.imageUrl || `/images/flower/${String(flower.dataNo).padStart(5, '0')}_${flower.flowerName}.png`;
        flowerImage.style.backgroundImage = `url('${imageUrl}')`;
        flowerImage.style.backgroundSize = 'cover';
        flowerImage.style.backgroundPosition = 'center';
        flowerImage.style.backgroundRepeat = 'no-repeat';
    }

    // 클릭 이벤트로 상세정보 표시
    listItem.onclick = () => showFlowerDetail(flower.productId);
    listItem.style.cursor = 'pointer';
}

// 검색 기능 초기화 (기존 HTML 요소 활용)
function initializeSearchFeature() {
    // 검색 입력창이 이미 있는지 확인
    let searchInput = document.querySelector('.search-area input');

    if (!searchInput) {
        // 검색 영역에 검색 입력창 추가 (기존 CSS 클래스 활용)
        const searchArea = document.querySelector('.search-area');
        if (searchArea) {
            // 기존 구조 유지하면서 검색 기능 추가
            const searchLi = searchArea.querySelector('li.search-f');
            if (searchLi) {
                searchInput = searchLi.querySelector('input');
                if (searchInput) {
                    // 기존 입력창을 검색용으로 활용
                    searchInput.placeholder = '꽃 이름이나 꽃말을 검색해보세요...';
                    searchInput.addEventListener('keypress', function (e) {
                        if (e.key === 'Enter') {
                            performSearch(this.value);
                        }
                    });

                    // 검색 버튼 기능 활성화
                    const searchButton = searchLi.querySelector('a');
                    if (searchButton) {
                        searchButton.onclick = () => performSearch(searchInput.value);
                        searchButton.style.cursor = 'pointer';
                    }
                }
            }
        }
    }
}

// 검색 수행
function performSearch(keyword) {
    if (!keyword || !keyword.trim()) {
        alert('검색어를 입력해주세요.');
        return;
    }

    fetch(`/api/flowers/search?keyword=${encodeURIComponent(keyword.trim())}`)
        .then(response => response.json())
        .then(data => {
            currentSearchResults = data;
            isSearchMode = true;
            showSearchResults(data);
        })
        .catch(error => {
            console.error('검색 실패:', error);
            alert('검색 중 오류가 발생했습니다.');
        });
}

// 검색 결과 표시 (기존 search-result 영역 활용)
function showSearchResults(flowers) {
    // 월별 탭 비활성화
    let menus = document.querySelectorAll(".years>li");
    menus.forEach(list => list.classList.remove("active"));

    // 월별 리스트 숨기기
    let tabs = document.getElementsByClassName('m-flower-list');
    for (let i = 0; i < tabs.length; i++) {
        tabs[i].style.display = "none";
    }

    // 기존 search-result 영역 활용
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        if (flowers.length === 0) {
            showNoResultsMessage();
            return;
        }

        // 첫 번째 검색 결과 표시
        displaySearchResultItem(searchResult, flowers[0]);
        searchResult.style.display = 'block';

        // 추가 검색 결과가 있으면 하단에 표시
        if (flowers.length > 1) {
            showAdditionalSearchResults(flowers.slice(1));
        }
    }
}

// 검색 결과 아이템 표시
function displaySearchResultItem(container, flower) {
    const imgArea = container.querySelector('.img-area');
    const flowerName = container.querySelector('.name-txt');

    if (imgArea) {
        const imageUrl = flower.imageUrl || `/images/flower/${String(flower.dataNo).padStart(5, '0')}_${flower.flowerName}.png`;
        imgArea.style.backgroundImage = `url('${imageUrl}')`;
        imgArea.style.backgroundSize = 'cover';
        imgArea.style.backgroundPosition = 'center';
        imgArea.style.backgroundRepeat = 'no-repeat';
    }

    if (flowerName) {
        flowerName.textContent = flower.flowerName;
    }

    // 클릭 시 상세정보 표시
    container.onclick = () => showFlowerDetail(flower.productId);
}

// 추가 검색 결과 표시 (월별 컨테이너 활용)
function showAdditionalSearchResults(flowers) {
    // june 컨테이너를 임시로 검색 결과 표시용으로 활용
    const tempContainer = document.getElementById('june');
    if (tempContainer) {
        tempContainer.style.display = 'flex';

        // 기존 방식으로 검색 결과 표시
        const maxItems = Math.min(flowers.length, 6);
        const existingItems = tempContainer.querySelectorAll('li');

        for (let i = 0; i < 6; i++) {
            let listItem;

            if (i < existingItems.length) {
                listItem = existingItems[i];
            } else {
                listItem = createNewListItem(i + 1);
                tempContainer.appendChild(listItem);
            }

            if (i < maxItems) {
                updateListItemWithFlowerData(listItem, flowers[i]);
                listItem.style.display = 'block';
            } else {
                listItem.style.display = 'none';
            }
        }
    }
}

// 검색 결과 없음 메시지
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

// 검색 결과 숨기기(안 나와도 보이게 수정)
function hideSearchResults() {
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        searchResult.style.display = 'block';
    }
}

// 꽃 상세정보 표시 (모달 형태로 동적 생성)
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

// 모달 생성 및 표시
function createAndShowModal(flower) {
    // 기존 모달이 있으면 제거
    const existingModal = document.getElementById('flowerDetailModal');
    if (existingModal) {
        existingModal.remove();
    }

    // 모달 HTML 생성
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

    const imageUrl = flower.imageUrl || (flower.image ? `/images/flower/${flower.image}` : '/images/flower/default.jpg');

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
                         onerror="this.src='/images/flower/default.png'">
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
                        <p style="margin: 5px 0; color: #333;">${flower.fMonth}월의 꽃</p>
                    </div>
                </div>
            </div>
        `;

    // 모달 외부 클릭 시 닫기
    modal.onclick = function (event) {
        if (event.target === modal) {
            closeFlowerModal();
        }
    };

    document.body.appendChild(modal);
}

// 모달 닫기
function closeFlowerModal() {
    const modal = document.getElementById('flowerDetailModal');
    if (modal) {
        modal.remove();
    }
}

// ESC 키로 모달 닫기
document.addEventListener('keydown', function (event) {
    if (event.key === 'Escape') {
        closeFlowerModal();
    }
});

// 검색 초기화 (기존 상태로 복귀)
function clearSearch() {
    const searchInput = document.querySelector('.search-area input');
    if (searchInput) {
        searchInput.value = '';
    }

    isSearchMode = false;
    hideSearchResults();

    // 6월 탭으로 돌아가기
    const juneBtn = document.querySelector(".years li[onclick*='june']");
    if (juneBtn) {
        openTab("june", juneBtn);
    }
}

// 초기화 함수
function initializeFlowerNote() {
    // 검색 기능 초기화
    initializeSearchFeature();

    // 6월 탭으로 시작
    const juneBtn = document.querySelector(".years li[onclick*='june']");
    if (juneBtn) {
        openTab("june", juneBtn);
    }

    // 태그 클릭 이벤트 추가 (빠른 검색)
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

// DOM 로드 완료 시 초기화
document.addEventListener("DOMContentLoaded", () => {
    initializeFlowerNote();
});

// 전역 함수로 노출 (HTML에서 직접 호출 가능)
window.clearSearch = clearSearch;
window.showFlowerDetail = showFlowerDetail;
window.closeFlowerModal = closeFlowerModal;
