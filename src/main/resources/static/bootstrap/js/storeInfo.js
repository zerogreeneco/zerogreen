$(document).ready(function () {

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
}); //end Update

// 글자수 제한
function limitTextInput(event) {
    let countText = $(event).prev().children(".cm-text-count");
    let check=$("#textCheck");
    countText.html($(event).val().length + " / ㄴ300");

    if ($(event).val().length > 300) {
        $(event).val($(event).val().substring(0, 300));
        check.html("300자까지 입력 가능합니다");
        check.css("color","#dc3545");
        countText.html("300 / 300");
    }
}

// Social 주소 추가
$("#socialAdd").click(function () {
    $("#socialAddress2").removeAttr("hidden");
    $("#socialAdd").attr("hidden", true);
});

// 테이블 Grade 추가
$("#menuAdd").click(function () {
    let name = $("#menuName").val();
    let price = $("#menuPrice").val();
    let grade = $(":input:radio[name=vegetarianGrade]:checked").val();
    let check = $("#gradeCheck");

    if(grade == null){
        check.html("해당 메뉴의 비건 등급을 입력해 주세요");
        check.css("color","#dc3545");
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
        });
}); //menuAdd ajax end

//테이블 추가
$(".menuAdd").click(function () {
    let name = $("#menuName").val();
    let price = $("#menuPrice").val();
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
        });
}); //menuAdd ajax end

// 테이블 삭제
function tableDel(event) {
    let tableDel = $(event);
    let id = tableDel.parent().parent().find(".tableId").val();
    alert(id);
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