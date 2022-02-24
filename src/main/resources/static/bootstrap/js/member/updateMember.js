$(function (event) {
    // 회원 정보 변경
    $("#info-submit").click(function () {

        const nickname = $("#nickname").val();
        const phoneNum = $("#phoneNumber").val();
        const vegetarianGrade = $("#vegetarianGrade").val();

        $.ajax({
            url: "/zerogreen/members/account",
            type: 'patch',
            dataType: 'json',
            data: {
                nickname: nickname,
                phoneNumber: phoneNum,
                vegetarianGrade: vegetarianGrade
            }
        })
            .done(function (data) {
                if (data.result === "success") {
                    alert("회원정보를 수정했습니다.");
                }
            });
    });

    // 비밀번호 변경
    $("#pwd-change").click(function () {
        const password = $("#password").val();
        const newPassword = $("#newPassword").val();
        const reNewPassword = $("#re-newPassword").val();

        if (password === "" || newPassword === "") {
            alert("비밀번호를 입력해주세요.");
        } else if (password === newPassword) {
            alert("현재 비밀번호로 변경할 수 없습니다.");
        }

        if (newPassword !== reNewPassword) {
            alert("변경하려는 비밀번호가 일치하지 않습니다.\n변경할 비밀번호를 다시 확인해주세요.");
        } else {
            $.ajax({
                url: "/zerogreen/members/account/pwdChange",
                type: "patch",
                data: {
                    password: password,
                    newPassword: newPassword
                },
                dataType: "json"
            })
                .done(function (data) {
                    if (data.result === "success") {
                        alert("비밀번호가 변경되었습니다.\n다시 로그인해주십시오.");
                        location.replace("/zerogreen/login");
                    } else if (data.result === "fail") {
                        alert("현재 비밀번호가 일치하지 않습니다.\n다시 확인해주십시오.");
                    }
                });
        }
    });

    // 회원 탈퇴
    $("#delete_id_btn").click(function () {
        const password = $("#delete_id").val();

        $.ajax({
            url: "/zerogreen/members/account/deleteMember",
            type: "delete",
            dataType: "json",
            data: {
                password: password
            }
        }).done(function (data) {
            if (data.result === "success") {
                alert("꼭 다시 돌아와주세요..😥");
                location.replace("/zerogreen");
            } else if (data.result === "fail") {
                alert("비밀번호가 일치하지 않습니다.");
            }
        });
    });
});
