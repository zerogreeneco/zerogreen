$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();

    // save Reviews
    $("#rv-btn").click(function () {
        let reviewText = $("#reviewText");

        $.ajax({
            url: contextPath+'/page/detail/addReview/' + sno,
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                sno: sno,
                reviewText: reviewText.val()
            }
        })
            .done(function (fragment) {
                $("#reviewList").replaceWith(fragment);
                //alert("댓글이 등록되었습니다.");
                $("#reviewText").val("");
                //$("#text-count").text("0 / 100");
            });

    }); // end save reviews


    // 대댓글
/*
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
*/


}); //end script
