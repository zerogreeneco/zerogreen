$(document).ready(function () {
    let duplication = false;

    // 영업시간
    for (var i = 0; i <= 47; i++) {
        var hour = "";
        var min = ":00";

        if ((Math.ceil(i / 2)) < 13) {
            hour = Math.floor(i / 2);
        } else {
            hour = Math.floor(i / 2);
        }
        if ((Math.ceil(i % 2)) != 0) { <!--나머지 0 또는 1-->
            var min = ":30";
        }

        $(".startTime").append("<option value="
            + ((hour >= 10) ? hour : ('0' + hour)) + min + ">" + ((hour >= 10) ? hour : ("0" + hour)) + min + "</option>");
    }

    for (var i = 47; i >= 0; i--) {
        var hour = "";
        var min = ":00";

        if ((Math.ceil(i / 2)) < 13) {
            hour = Math.floor(i / 2);
        } else {
            hour = Math.floor(i / 2);
        }
        if ((Math.ceil(i % 2)) != 0) { <!--나머지 0 또는 1-->
            var min = ":30";
        }

        $(".closeTime").append("<option value="
            + ((hour >= 10) ? hour : ('0' + hour)) + min + ">" + ((hour >= 10) ? hour : ("0" + hour)) + min + "</option>");
    }
    // 이미지 슬라이더
    $("#img-right").click(function() {
        $(".img-slider").animate({marginLeft: "-150px"
        }, function() {
            $(".img-slider .store-img:first").appendTo(".img-slider");
            $(".img-slider").css({marginLeft: 0});
        });
    });
    $("#img-left").click(function() {
        $(".img-slider .store-img:last").prependTo(".img-slider");
        $(".img-slider").css({ "marginLeft": "-150px"});
        $(".img-slider").animate({ marginLeft: 0 });
    });

    // 업로드 미리보기
    $(".input-box").on('change', selectedImageFile);

    // 연락처 중복 확인
    $("#phoneCheck").off().on("click", function () {
        let phoneNumber = $("#phoneNumber").val();
        let error = $(".phoneNumber-error");

        if (phoneNumber === "" || phoneNumber === "undefined") {
            error.html("전화번호를 입력해 주세요");
            error.css("color", "#dc3545")

        } else {
            $.ajax({
                url: "/zerogreen/stores/phoneNumber",
                method: "post",
                dataType: "json",
                data: {
                    phoneNumber: phoneNumber
                }
            })
                .done(function (data) {
                    if (data.result === 1) {
                        error.html("이미 존재하는 연락처입니다");
                        error.css("color", "#dc3545");
                    } else {
                        duplication = true;
                        error.html("사용 가능한 연락처입니다");
                        error.css("color", "blue");
                    }
                });
        }
    });

    // 회원가입 버튼
    $("#btnUpdate").on("click", function () {
        if (duplication === true ) {
            $("#storeAccountUpdate").submit();
        }else{
            $(".phoneNumber-error").html("중복 검사를 실시해 주세요");
            $(".phoneNumber-error").css("color", "#dc3545");
        }
    });
}); //end Update

// 글자수 제한
function limitTextInput(event) {
    let countText = $(event).prev().children(".cm-text-count");
    let check=$("#textCheck");
    countText.html($(event).val().length + " / 300");

    if ($(event).val().length > 300) {
        $(event).val($(event).val().substring(0, 300));
        check.html("300자까지 입력 가능합니다");
        check.css("color","#dc3545");
        countText.html("300 / 300");
    }
}

// Social 주소 추가
function addSocial() {
    $("#socialAddress2").removeAttr("hidden");
    $("#socialAdd").attr("hidden", true);
}

// Price 회계단위 추가
function inputPriceFormat(event){
    event.value = comma(uncomma(event.value));
}
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}


// 테이블 Grade 추가
function menuAdd() {
    let name = $("#menuName").val();
    let price = $("#menuPrice").val();
    let grade = $(":input:radio[name=vegetarianGrade]:checked").val();
    let inputCheck = $("#inputCheck");
    let gradeCheck = $("#gradeCheck");

    if(name == "" || price == ""){
        inputCheck.html("메뉴의 이름과 가격을 입력해 주세요");
        inputCheck.css("color","#dc3545");

        return null;
    } else if(grade == null) {
        gradeCheck.html("해당 메뉴의 비건 등급을 입력해 주세요");
        gradeCheck.css("color", "#dc3545");
    }

    $.ajax({
        url: "/zerogreen/stores/update/gradeTable",
        method: "post",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: {
            name: name,
            price: price,
            grade: grade
        }
    })
        .done(function (fragment) {
            $("#grade-table").replaceWith(fragment);
            $("#menuName").val("");
            $("#menuPrice").val("");
            $(":input:radio[name=vegetarianGrade]:checked").prop('checked', false);
            inputCheck.html("");
            gradeCheck.html("");
        });
} //menuAdd ajax end

//테이블 추가
function tableAdd() {
    let name = $("#menuName").val();
    let price = $("#menuPrice").val();
    let inputCheck = $("#inputCheck");

    if(name == "" || price == "") {
        inputCheck.html("메뉴의 이름과 가격을 입력해 주세요");
        inputCheck.css("color", "#dc3545");

        return null;
    }

        $.ajax({
        url: "/zerogreen/stores/update/table",
        method: "post",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: {
            name: name,
            price: price
        }
    })
        .done(function (fragment) {
            $("#list-table").replaceWith(fragment);
            $("#menuName").val("");
            $("#menuPrice").val("");
            inputCheck.html("");
        });
} //menuAdd ajax end

// 테이블 Grade 삭제
function gradeDel(event) {
    let tableDel = $(event);
    let id = tableDel.parent().parent().find(".tableId").val();
    $.ajax({
        url: "/zerogreen/stores/update/grade/delete",
        method: "delete",
        data: {
            id: id
        }
    })
        .done(function (fragment) {
            $("#grade-table").replaceWith(fragment);
        });
}

// 테이블 삭제
function tableDel(event) {
    let tableDel = $(event);
    let id = tableDel.parent().parent().find(".tableId").val();
    $.ajax({
        url: "/zerogreen/stores/update/table/delete",
        method: "delete",
        data: {
            id: id
        }
    })
        .done(function (fragment) {
            $("#list-table").replaceWith(fragment);
        });
}


// 이미지 삭제
function imgDel(event) {
    let imgDel = $(event);
    let id = imgDel.parent().find(".imgId").val();
    let filePath = imgDel.parent().find(".filePath").val();
    let thumb = imgDel.parent().find(".thumb").val();
    $.ajax({
        url: "/zerogreen/stores/update/img/delete",
        method: "delete",
        dataType:"json",
        data: {
            id: id,
            filePath:filePath,
            thumb:thumb
        }
    })
        .done(function (data) {
            if (data.key === "success") {
                imgDel.parent().find('img').remove();
                imgDel.remove();
            }
        });
}
// 크기, 확장자 확인
function selectedImageFile(e) {
    selFiles = [];
    $("#selectedImg").empty();

    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);
    let totalSize = 0;
    let index = 0;

    let check = $("#imgCheck");

    filesArr.forEach(function (file){
        totalSize = totalSize + file.size;
    });

    filesArr.forEach(function (file) {

        if (!file.type.match("image.*")) {
            check.html("사진을 첨부해주세요");
            check.css("color","#dc3545");

            return;
        }else if(totalSize > 10485760){
                check.html("최대 10MB까지 첨부할 수 있습니다");
                check.css("color","#dc3545");
                $(".input-box").val("");

                return;
        } else{
            selFiles.push(file);
            let reader = new FileReader();

            reader.onload = function (e) {
                let html = "<img class='selectedImg' src='"+e.target.result+"' data-file='"+file.name+"' width='100px' height='100px'>";
                $("#selectedImg").append(html);
                index++;
            };
            reader.readAsDataURL(file);
       }
    });
}

//비밀번호 변경
function pwdChange(){
    const password = $("#pwd");
    const newPassword = $("#newPwd");
    const checkNewPassword = $("#checkNewPwd");
    const error = $(".pwd-error");
    const re_error = $(".rePwd-error");
    const check = $(".check-error");

    if(password.val() === "" || newPassword.val() === "" || checkNewPassword.val() === ""){
        check.html("비밀번호를 입력해 주세요");
        check.css("color","#dc3545");
        return false;
    }else if(password.val() === newPassword.val()){
        re_error.html("현재 비밀번호로 변경할 수 없어요");
        re_error.css("color","#dc3545");
        return false;
    }else if(!passwordRegCheck(newPassword.val())){
        re_error.html("숫자/대,소문자/특수기호를 포함한\n8~12자리로 변경해 주세요");
        re_error.css("color","#dc3545");
        return false;
    }

    if(newPassword.val() !== checkNewPassword.val()){
        re_error.html("");
        check.html("비밀번호가 일치하지 않아요");
        check.css("color","#dc3545");
        checkNewPassword.val("");
        checkNewPassword.focus();
    }else{
        $.ajax({
            url: "/zerogreen/stores/update/password",
            type: "patch",
            data:{
                password: password.val(),
                newPassword: newPassword.val()
            },
            dataType: "json"
        })
            .done(function (data){
                if(data.result === "success"){
                    alert("비밀번호가 변경되었습니다\n다시 로그인해 주세요");
                    location.replace("/zerogreen/login");
                }else if(data.result === "fail"){
                    re_error.html("");
                    check.html("");
                    error.html("비밀번호가 일치하지 않아요");
                    error.css("color","#dc3545");
                    password.val("");
                    password.focus();
                }
            });
    }
}

// 비밀번호 정규식 확인 (숫자, 소문자, 대문자, 특수문자 1개 이상 8~12자리)
function passwordRegCheck(password) {
    let regExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,12}/;
    return regExp.test(password);
}