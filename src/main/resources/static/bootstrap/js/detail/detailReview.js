$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

//이하 아직 수정 안함, 뷰에 연결도 안했음------------------------------------

    // 댓글
    $("#review-send-btn").click(function () {
        let text = $("#text").val();

        $.ajax({
            url: "/zerogreen/community/" + boardId + "/reply",
            method: "post",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            // dataType: "json",
            data: {
                boardId: boardId,
                text: text
            }
        })
            .done(function (fragment) {
                $("#review-table").replaceWith(fragment);
                alert("댓글이 등록되었습니다.");
                $("#text").val("");
                $("#text-count").text("0 / 100");
            });

    }); // review send end.

    // 대댓글
    function nestedReplySend(event) {
        let boardId = $("#id").val();
        let replyBtn = $(event);
        let replyId = replyBtn.parents(".comment-wrapper").find(".replyId").val();
        let text = replyBtn.parent().children(".nested-reply-input").val();
        let table = replyBtn.closest("#nested-reply");
        console.log(replyBtn.parents(".comment-wrapper").find(".replyId"));
        $.ajax({
            url: "/zerogreen/community/" + boardId + "/" + replyId + "/nestedReply",
            method: "post",
            data: {
                boardId: boardId,
                replyId: replyId,
                text: text
            },
        })
            .done(function (fragment) {
                $("#review-table").replaceWith(fragment);
            })

    }


}); //end script
