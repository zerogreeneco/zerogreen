$(function () {

    let boardId = $("#id").val();
    let actionForm = $("form");

    /* 좋아요 버튼 */
    $("#like-btn").click(function () {

        $.ajax({
            url: "/zerogreen/community/like/" + boardId,
            method: "post",
            dataType: "json",
            data: {
                boardId: boardId
            }
        })
            .done(function (data) {
                if (data.count === 1) {
                    $(".like-img").attr("src", "/zerogreen/bootstrap/images/like/disLike.png")
                    $(".test-count").text(data.totalCount);
                } else if (data.count === 0) {
                    $(".like-img").attr("src", "/zerogreen/bootstrap/images/like/like.png")
                    $(".test-count").text(data.totalCount);
                }
            });
    }); // like-btn end.

    /* 댓글 전송 */
    $("#review-send-btn").click(function () {
        let text = $("#text").val();

        $.ajax({
            url: "/zerogreen/community/" + boardId + "/reply",
            method: "post",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                boardId: boardId,
                text: text
            }
        })
            .done(function (fragment) {
                $("#review-table").replaceWith(fragment);
                // getReplyData(boardId, table);
                alert("댓글이 등록되었습니다.");
                $("#text").val("");
                $("#text-count").text("0 / 100");
            });

    }); // review send end.

    // 게시물 삭제
    $("#delete-board").click(function () {
        actionForm
            .attr("action", "/zerogreen/community/" + boardId + "/delete")
            .attr("method", "post");
        if (confirm("정말 삭제하시겠습니까?") === true) {
            actionForm.submit();
        } else {
            return;
        }

    });

    $("#text").on("keyup", function () {
        let text = $(this);
        limitTextInput(text);
    });


}); // onload end.

    /* 댓글 글자 수 제한 */
    function limitTextInput(event) {
        console.log(event);
        let countText = $(event).prev().children(".cm-text-count");
        console.log(countText);
        countText.html($(event).val().length + " / 100");

        if ($(event).val().length > 100) {
            $(event).val($(event).val().substring(0, 100));
            alert("100자까지 입력이 가능합니다.");
            countText.html("100 / 100");
        }
    }

let boardId = $("#id").val();

function textareaResize(obj) {
    obj.style.height = "1px";
    obj.style.height = (12 + obj.scrollHeight) + "px";
}

function getReplyData(boardId, table) {

    $.getJSON("/zerogreen/community/" + boardId + "/replyList", function (list) {

        let html= "";

        $.each(list, function (key, value) {
            html += "<div class='comment-wrapper'>";
            html += "<input type='hidden' class='replyId' value=" + value.id + ">";
            html += "<div>" + value.nickname + "</div>";
            html += "<p class='rp-text'>"+ value.text +"</p>"
            html += "<a type='button' class='modify-test-btn' style='display: none;' onclick='modifyReply(this)'>수정</a>";
            html += "<div>" + value.createdTime + "</div>";
            html += "<a class='nested-reply-btn' onClick='nestedReplyInput(this)'>답글</a>";
            html += "<a>삭제</a>";
            html += "<a class='rp-modify-btn' onclick='replaceTag(this)'>수정</a>";
            html += "<div></div>";
            html += "<div class='nested-reply-wrapper' style='display: none;'>";
            html += "<textarea class='nested-reply-input'></textarea>";
            html += "<span>0 / 100</span>";
            html += "<a onclick='nestedReplySend(this)'>답글 달기</a>";
            html += "<a>취소</a>";
            html += "</div>";
            html += "<hr/>";
            html += "</div>"
        })
        table.html(html);
    });
} // getReplyData end

function getNestedReplyData(table, replyId) {
    let html = "";

    $.getJSON("/zerogreen/community/" + replyId + "/nestedReplyList", function (list) {
        $.each(list, function (key, value) {
            html += "<div id='nested-reply'>";
            html += "<div class='cm-replier'>" + value.nickname + "</div>";
            html += "<p class='cm-reply-text'>" + value.text + "</p>";
            html += "<div>" + value.createdTime + "</div>";
            html += "</div>";
        })
        table.append(html);
    });
}

/* 댓글 수정용 태그 변경 */
function replaceTag(event) {
    let thisBtn = $(event);
    let parent = thisBtn.parent().parent();
    let rpText = parent.find(".rp-text:first");
    let text = rpText.text();

    rpText.replaceWith("<textarea class='modify-test' name='text' onkeyup='limitTextInput(this)'>" + text + "</textarea>");
    parent.find(".modify-test-btn:first").show();
    parent.find(".cm-text-count").show();
    thisBtn.hide();

}

function nestedReplyInput(event) {
    let inputAdd = $(event);
    inputAdd.parent().parent().find(".nested-reply-wrapper").show();
}

/* 댓글 수정 */
function modifyReply(event) {
    let boardId = $("#id").val();
    let modifyBtn = $(event);
    let replyId = modifyBtn.parent().find(".replyId").val();
    let text = modifyBtn.parent().find(".modify-test");
    console.log(text.val());

    $.ajax({
        url: "/zerogreen/community/" + boardId + "/replyModify/" + replyId,
        method: "post",
        dataType: "json",
        data: {
            boardId: boardId,
            replyId: replyId,
            text: text.val()
        }
    })
        .done(function (data) {
            if (data.result === "success") {
                alert("성공");
                text.replaceWith("<p class='rp-text'>" + text.val() + "</p>")
                // $(".rp-text:eq(1)").remove();
                $(".replyId").next(".modify-test").hide();
                $(".replyId").next().next().children(".cm-text-count").hide();
                modifyBtn.hide();
                modifyBtn.prevAll("#text-count-wrapper").children().hide()
                modifyBtn.next().children(".rp-modify-btn").show();
            } else {
                alert("실패");
            }
        })
}

// 대댓글
function nestedReplySend(event) {
    let boardId = $("#id").val();
    let replyBtn = $(event);
    let replyId = replyBtn.parents(".comment-wrapper").find(".replyId").val();
    let text = replyBtn.parent().children(".nested-reply-input").val();

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

function deleteReply(replyId) {

    let boardId = $("#id").val();

    $.ajax({
        url: "/zerogreen/community/" + replyId + "/delete",
        method: "delete",
        // dataType: "json",
        data: {
            replyId: replyId,
            boardId: boardId
        },
    })
        .done(function (fragment) {
            alert("댓글이 삭제되었습니다.");
            $("#review-table").replaceWith(fragment);
        });
}
