$(document).ready(function () {
    $(".join_btn").click(function () {
        $(".modal").stop().fadeIn(300);
    });
    $("#gohome").click(function () {
        $(".modal").stop().fadeOut(300);
    });
    $("#login").click(function () {
        $(".modal").stop().fadeOut(300);
    });
});

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById('address01').value = data.address;
            document.getElementById('address02').focus();
        }
    }).open();
}

function updateTel() {
    const tel1 = document.getElementById('tel1').value.trim();
    const tel2 = document.getElementById('tel2').value.trim();
    const tel3 = document.getElementById('tel3').value.trim();
    const tel = document.getElementById('tel');

    if (tel1 && tel2 && tel3) {
        tel.value = `${tel1}-${tel2}-${tel3}`;
    } else {
        tel.value = '';
    }
}

document.getElementById('tel1').addEventListener('change', updateTel);
document.getElementById('tel2').addEventListener('input', updateTel);
document.getElementById('tel3').addEventListener('input', updateTel);

document.getElementById('signupForm').addEventListener('submit', function () {
    emailChange(); // 이메일 최신화
    updateTel();   // 전화번호 최신화
});

//이메일 분리 처리과정
function emailChange() {
    const emailId = document.getElementById('emailId').value.trim();
    const emailSelect = document.getElementById('email');
    const emailDirect = document.getElementById('emailDirect');
    const userEmail = document.getElementById('userEmail');

    if (emailSelect.value === 'direct') {
        emailDirect.style.display = 'inline-block';
        const domain = emailDirect.value.trim();
        userEmail.value = emailId + '@' + domain;
    } else {
        emailDirect.style.display = 'none';
        userEmail.value = emailId + '@' + emailSelect.value;
    }
}

document.getElementById('emailDirect').addEventListener('input', emailChange);
document.getElementById('email').addEventListener('change', emailChange);
document.getElementById('emailId').addEventListener('input', emailChange);

let isUserIDChecked = false;
let isNickNameChecked = false;
let isEmailChecked = false;
//중복체크 안하고 회원가입 버튼 누를경우
document.getElementById('signupForm').addEventListener('submit', function (event) {
    const fields = [
        {id: 'userID', label: '아이디'},
        {id: 'userPW', label: '비밀번호'},
        {id: 'userEmail', label: '이메일'},
        {id: 'userName', label: '이름'},
        {id: 'nickName', label: '닉네임'},
        {id: 'tel', label: '전화번호'},
        {id: 'address01', label: '주소'},
        {id: 'zipcode', label: '우편번호'}
    ];

    let missing = [];

    fields.forEach(field => {
        const input = document.getElementById(field.id);
        if (!input.value.trim()) {
            missing.push(field.label);
        }
    });

    if (missing.length > 0) {
        alert("다음 항목을 입력해주세요: " + missing.join(', '));
        event.preventDefault();
        return;
    }


    if (!isUserIDChecked) {
        alert("아이디 중복 체크를 해주세요.");
        event.preventDefault();
        return;
    }
    if (!isNickNameChecked) {
        alert("닉네임 중복 체크를 해주세요.");
        event.preventDefault();
        return;
    }
});
//아이디 중복체크
document.getElementById('checkUserIDBtn').addEventListener('click', function () {
    const userID = document.getElementById('userID').value;
    const idRegex = /^[a-z0-9]{6,16}$/;
    if (!userID.trim()) {
        alert("아이디를 먼저 입력하세요.");
        isUserIDChecked = false;
        return;
    }
    if (!idRegex.test(userID)) {
        alert("아이디는 6~16자의 소문자 영문과 숫자만 사용 가능합니다.");
        isUserIDChecked = false;
        return;
    }

    fetch(`/member/checkUserID?userID=${encodeURIComponent(userID)}`)
        .then(response => response.json())
        .then(isDuplicate => {
            if (isDuplicate) {
                alert("이미 사용 중인 아이디입니다.");
                isUserIDChecked = false;
            } else {
                alert("사용 가능한 아이디입니다.");
                isUserIDChecked = true;
            }
        })
        .catch(() => {
            alert('중복 체크 중 오류가 발생했습니다.');
            isUserIDChecked = false;
        });
});
//닉네임 중복체크
document.getElementById('checkNickNameBtn').addEventListener('click', function () {
    const nickName = document.getElementById('nickName').value;
    if (!nickName.trim()) {
        alert("닉네임을 먼저 입력하세요.");
        isNickNameChecked = false;
        return;
    }
    fetch(`/member/checkNickName?nickname=${encodeURIComponent(nickName)}`)
        .then(response => response.json())
        .then(isDuplicate => {
            if (isDuplicate) {
                alert("이미 사용 중인 닉네임입니다.");
                isNickNameChecked = false;
            } else {
                alert("사용 가능한 닉네임입니다.");
                isNickNameChecked = true;
            }
        })
        .catch(() => {
            alert('중복 체크 중 오류가 발생했습니다.');
            isNickNameChecked = false;
        });
});
//비밀번호 체크
let pw = document.getElementById('userPW');
let confirmPw = document.getElementById('confPass');
let message = document.getElementById('pwmsg');

function checkPasswordMatch() {
    if (pw.value === "" || confirmPw.value === "") {
        message.textContent = "";
        return;
    }

    if (pw.value === confirmPw.value) {
        message.textContent = "비밀번호가 일치합니다.";
        message.className = "match";
    } else {
        message.textContent = "비밀번호가 일치하지 않습니다.";
        message.className = "no-match";
    }
}

pw.addEventListener('input', checkPasswordMatch);
confirmPw.addEventListener('input', checkPasswordMatch);


//동의 체크박스
const allAgree = document.getElementById('allAgree');
const checkboxes = document.querySelectorAll('.agreeCheck');
const requiredChecks = document.querySelectorAll('.agreeCheck.required');


allAgree.addEventListener('change', () => {
    checkboxes.forEach(box => box.checked = allAgree.checked);
});

// 회원가입 버튼 클릭 시 필수 약관 체크 여부 검사
document.querySelector('.join_btn').addEventListener('click', function (e) {
    const allRequiredChecked = [...requiredChecks].every(box => box.checked);
    if (!allRequiredChecked) {
        e.preventDefault(); // 🚫 폼 제출 막기
        alert('약관에 모두 동의해 주세요.');
    }
});
//index/index에서 이메일 입력시 처리과정
window.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(location.search);
    const fullEmail = params.get('email');     // hello@abc.com 형태

    if (!fullEmail || !fullEmail.includes('@')) return;


    const [idPart, domainPart] = fullEmail.split('@');


    document.getElementById('emailId').value = idPart;


    const emailSelect = document.getElementById('email');
    emailSelect.value = 'direct';


    const emailDirect = document.getElementById('emailDirect');
    emailDirect.style.display = 'inline-block';
    emailDirect.value = domainPart;


    emailChange();
});


