let currentSearchResults = [];
let isSearchMode = false;

// 월별 탭 전환
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

// 월별 꽃 정보 로드
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

// 월별 꽃 리스트 UI 업데이트
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

// 꽃 리스트 아이템 생성
function createNewListItem(index) {
    const li = document.createElement('li');
    li.className = `list${index}`;
    li.innerHTML = `
        <div class="flower-image"></div>
        <div class="flower-name">
            <span class="name-txt">꽃이름</span>
        </div>
    `;
    return li;
}

// 꽃 리스트 아이템 데이터 주입 및 클릭 이벤트 연결
function updateListItemWithFlowerData(listItem, flower) {
    const flowerImage = listItem.querySelector('.flower-image');
    const nameText = listItem.querySelector('.name-txt');
    if (nameText) {
        nameText.textContent = flower.flowerName || flower.fname || flower.name || '';
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

// 검색 인풋/버튼/인기검색어 이벤트 연결
function initializeSearchFeature() {
    let searchInput = document.querySelector('.search-area input');
    if (searchInput) {
        searchInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                performSearch(this.value);
            }
        });
        const searchButton = document.querySelector('.search-area li.search-f a');
        if (searchButton) {
            searchButton.onclick = function (event) {
                event.preventDefault();
                performSearch(searchInput.value);
            };
            searchButton.style.cursor = 'pointer';
        }
    }
    // 인기검색어 클릭
    const tagSpans = document.querySelectorAll('.tag span:not(.tag-title)');
    tagSpans.forEach(span => {
        span.onclick = () => {
            const keyword = span.textContent.trim();
            if (keyword) {
                if (searchInput) searchInput.value = keyword;
                performSearch(keyword);
            }
        };
    });
}

function performSearch(keyword) {
    if (!keyword || !keyword.trim()) {
        alert('검색어를 입력해주세요.');
        return;
    }
    fetch(`/api/flowers/search?keyword=${encodeURIComponent(keyword.trim())}`)
        .then(response => response.json())
        .then(data => {
            // data.flowers가 있으면 그걸, 아니면 data 자체
            let flowers = data.flowers ? data.flowers : data;
            if (flowers && flowers.length > 0) {
                // 랜덤 하나만 뽑아서 검색결과에 표시
                const randomIndex = Math.floor(Math.random() * flowers.length);
                const randomFlower = flowers[randomIndex];
                currentSearchResults = [randomFlower]; // 배열로 저장(필요시)
                isSearchMode = true;
                showSearchResults([randomFlower]); // 랜덤 하나만 배열로 넘김
            } else {
                showNoResultsMessage();
            }
        })
        .catch(error => {
            console.error('검색 실패:', error);
            alert('검색 중 오류가 발생했습니다.');
        });
}


// 검색결과 UI 표시
function showSearchResults(flowers) {
    let menus = document.querySelectorAll(".years>li");
    menus.forEach(list => list.classList.remove("active"));
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        if (flowers.length === 0) {
            showNoResultsMessage();
            return;
        }
        // 첫 번째 결과만 표시
        const flower = flowers[0];
        displaySearchResultItem(searchResult, flower);
        searchResult.style.display = 'block';
    }
}

// 검색결과 클릭 시 상세 모달 띄우기
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
        flowerName.textContent = flower.flowerName || flower.fname || flower.name || '';
    }
    container.onclick = () => showFlowerDetailInModal(flower);
    container.style.cursor = 'pointer';
}

// 검색결과 없음 메시지
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
        searchResult.style.cursor = 'default';
    }
}

// 검색결과 숨기기
function hideSearchResults() {
    const searchResult = document.querySelector('.search-result');
    if (searchResult) {
        searchResult.style.display = 'none';
    }
}

// 월별/검색결과 클릭 시 상세정보 모달 띄우기
function showFlowerDetail(productId) {
    fetch(`/api/flowers/detail/${productId}`)
        .then(response => response.json())
        .then(flower => {
            showFlowerDetailInModal(flower);
        })
        .catch(error => {
            console.error('상세정보 로드 실패:', error);
            alert('상세정보를 불러올 수 없습니다.');
        });
}

// 상세정보를 .modal에 채워서 보여줌
function showFlowerDetailInModal(flower) {
    const modal = document.querySelector('.modal');
    if (!modal) return;

    const imgArea = modal.querySelector('.img-area');
    if (imgArea) {
        imgArea.innerHTML = '';
        if (flower.image) {
            const img = document.createElement('img');
            img.src = '/upload/products/' + flower.image;
            img.alt = flower.flowerName || flower.fname || flower.name || '';
            img.style.width = '100%';
            img.style.height = '100%';
            img.style.objectFit = 'cover';
            imgArea.appendChild(img);
        } else {
            imgArea.style.backgroundColor = 'rgb(196,199,201)';
        }
    }

    if (modal.querySelector('.title h2')) modal.querySelector('.title h2').textContent = flower.flowerName || flower.fname || flower.name || '';
    if (modal.querySelector('.title p')) modal.querySelector('.title p').textContent = flower.flowLang ? `"${flower.flowLang}"` : '';
    if (modal.querySelector('.name-language h2')) modal.querySelector('.name-language h2').textContent = flower.flowerName || flower.fname || flower.name || '';
    if (modal.querySelector('.name-language p')) modal.querySelector('.name-language p').textContent = flower.flowLang || '';

    const infoBlocks = modal.querySelectorAll('.season-price .sub-txt');
    if (infoBlocks.length >= 5) {
        infoBlocks[0].textContent = (flower.fmonth || flower.fMonth) ? `${flower.fmonth || flower.fMonth}월의 꽃` : '정보가 없습니다.';
        infoBlocks[1].textContent = flower.flowerInfo || '정보가 없습니다.'; // 꽃정보
        infoBlocks[2].textContent = flower.fUse || flower.fuse || flower.f_use || '정보가 없습니다.'; // 용도
        infoBlocks[3].textContent = flower.fGrow || flower.fgrow || flower.f_grow || '정보가 없습니다.'; // 재배법
        infoBlocks[4].textContent = flower.fType || flower.ftype || flower.f_type || '정보가 없습니다.'; // 꽃종류
    }

    modal.style.display = 'block';
}

// 초기화
document.addEventListener("DOMContentLoaded", () => {
    // 6월 탭 기본 활성화
    const juneBtn = document.querySelector(".years li[onclick*='june']");
    if (juneBtn) openTab("june", juneBtn);

    initializeSearchFeature();

    // 모달 닫기 버튼
    const closeBtn = document.querySelector('.modal .close');
    if (closeBtn) {
        closeBtn.onclick = function() {
            document.querySelector('.modal').style.display = 'none';
        };
    }
    // 모달 배경 클릭시 닫기
    const modal = document.querySelector('.modal');
    if (modal) {
        modal.onclick = function(e) {
            if (e.target === modal) modal.style.display = 'none';
        };
    }
});
