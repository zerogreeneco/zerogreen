
$(function() {

    let id = $("#username");
    let password = $("#password");
    let rePassword = $("#re-password")
    let idCheck = false;
    let nicknameCheck = false;
    let phoneNumberCheck = false;
    let authKey = "";
    // 이메일 확인
    $("#send-mail").click(function() {

        if(id === "") {
            alert("메일 주소를 입력해주세요.");
        } else if (! emailRegCheck(id.val())) {
            alert("이메일 양식이 아닙니다.");
        } else {
            $.ajax({
                url: "/zerogreen/members/checkMail",
                type: "post",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: {
                    mail: id.val(),
                },
                success: function (key) {
                    if (key.fail) {
                        alert(key.fail);
                        id.focus();
                    } else {
                        alert("인증 번호가 전송되었습니다.");
                        authKey = key.key;
                        $(".auth-box").show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error" + status + ">>>> " + error);
                }
            }); // ajax end.
        }
    });

    // 이메일 인증
    $('#authConfirm').click(function() {
        let key = $(".authKey");

        if(key.val() === authKey) {
            alert("인증 성공");
            idCheck = true;
            if (idCheck === true) {
                $("#joinBtn").removeAttr("disabled");
                key.attr("disabled", true);
                this.attr("disabled", true);
            }
        } else {
            alert("인증 실패");
        }
    });

    // 닉네임 중복 확인
    $("#nickname-check").click(function () {
        let nickname = $("#nickname");

        if (nickname.val() === "" || nickname.val() === "undefined") {
            alert("닉네임을 입려해주세요.");
        } else {
            $.ajax({
                url: "/zerogreen/members/nickname",
                method: "post",
                dataType: "json",
                data: {
                    nickname: nickname.val()
                }
            })
                .done(function (data) {
                    let $nickname = $(".nickname-error");
                    if (data.result === 1) {
                        $nickname.text("이미 존재하는 닉네임입니다.");
                        $nickname.css("color", "red");
                        nickname.focus();
                    } else {
                        nicknameCheck = true;
                        $nickname.text("사용 가능한 닉네임입니다.");
                        $nickname.css("color", "blue");
                    }
                });
        }
        console.log(nicknameCheck);
    });

    // 연락처 중복 확인
    $("#phoneNumber-check").click(function () {
        let phoneNumber = $("#phoneNumber").val();

        if (phoneNumber === "" || phoneNumber === "undefined") {
            alert("연락처를 입력해주세요요");
        } else {
            $.ajax({
                url: "/zerogreen/members/phoneNumber",
                method: "post",
                dataType: "json",
                data: {
                    phoneNumber: phoneNumber
                }
            })
                .done(function (data) {
                    let $phoneNumber = $(".phoneNumber-error");
                    if (data.result === 1) {
                        $phoneNumber.text("이미 존재하는 연락처입니다.");
                        $phoneNumber.css("color","red");
                    } else {
                        phoneNumberCheck = true;
                        $phoneNumber.text("사용 가능한 연락처입니다.");
                        $phoneNumber.css("color","blue");
                    }
                });
        }
    });

    /*
    * 유효성 검사 (Blur)
    * */
    password.blur(function () {

        let $password = $(".password-error");
        if (!passwordRegCheck(password.val())) {
            $password.text("숫자/대,소문자/특수기호를 포함한 8~12자리");
            $password.css("color", "red");
            this.focus();
        } else {
            $password.text("사용 가능한 비밀번호입니다.");
            $password.css("color", "blue");
        }
    });

    // 비밀번호 중복 확인
    rePassword.blur(function () {
        let $re = $(".re-password-error");
        if (password.val() !== rePassword.val()) {
            $re.text("비밀번호가 일치하지 않습니다.");
            $re.css("color", "red");
            this.focus();
        } else {
            $re.text("비밀번호가 일치합니다.");
            $re.css("color", "blue");
        }
    });
    // 회원가입
    $("#joinBtn").click(function () {
        if (phoneNumberCheck === true && nicknameCheck === true
            && (password.val() === rePassword.val())) {
            $("#reg-form").submit();
        } else if (phoneNumberCheck !== true) {
            alert("연락처를 중복 확인을 해주세요.");
        } else if (nicknameCheck !== true) {
            alert("닉네임을 중복 확인을 해주세요.");
        } else if (password.val() !== rePassword.val()) {
            alert("비밀번호 일치여부를 확인해주세요.");
        }
    });
});

// 이메일 정규식 확인
function emailRegCheck(email) {
    let regExp =
        /^([\w\.\_\-])*[a-zA-Z0-9]+([\w\.\_\-])*([a-zA-Z0-9])+([\w\.\_\-])+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,8}$/;
    return (email !== "" && email !== "undefined" && regExp.test(email));
}

// 비밀번호 정규식 확인 (숫자, 소문자, 대문자, 특수문자 1개 이상 8~12자리)
function passwordRegCheck(password) {
    let regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,12}/;
    return regExp.test(password);
}

// KAKAO 추가 데이터
$(function() {
    $("#kakao-join-btn").click(function () {
        let memberId = $("#member-id").val();
        let phoneNum = $("#phoneNumber").val();
        let vegetarian = $("#vegetarianGrade").val();

        $.ajax({
            url: "/zerogreen/members/kakao/addData",
            type: "post",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",
            data: {
                memberId: memberId,
                phoneNumber: phoneNum,
                vegetarianGrade: vegetarian
            }
        });
    });

});

// 카카오 주소 검색 API
window.onload = function () {
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;

                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }
                document.get
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }

}