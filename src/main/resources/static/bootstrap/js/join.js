$(function() {

    var authKey = "";

    $(".sendMail").click(function() {

        var id = $("#username").val();

        alert($("#sample6_postcode").val() + "__" + $("#sample6_address").val());

        if(id == "") {
            alert("메일 주소를 입력해주세요.");
        } else {
            $.ajax({
                url: "/zerogreen/members/checkMail",
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                dataType: "json",
                data: {
                    mail:id
                },
                success: function(key) {
                    alert("인증 번호가 전송되었습니다.");
                    authKey = key.key;
                    $(".auth-box").show();
                },
                error: function(xhr, status, error) {
                    alert("Error" + status + ">>>> " + error);
                }
            }); // ajax end.
        }
    });


    $('#authConfirm').click(function() {
        var key = $(".authKey");

        if(key.val() == authKey) {
            alert("인증 성공");
            $("#joinBtn").removeAttr("disabled");
            key.attr("disabled", true);
            this.attr("disabled", true);
        } else {
            alert("인증 실패");
        }
    });
});

// KAKAO 추가 데이터
$(function() {
    $("#kakao-join-btn").click(function () {
        var memberId = $("#member-id").val();
        var phoneNum = $("#phoneNumber").val();
        var vegetarian = $("#vegetarianGrade").val();

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