$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let cnt = $(".review-cnt");
    let sno = $(".js-storeId").text();
    let count = 0;

    // save Reviews
    $("#rv-btn").click(function () {
        let reviewText = $("#reviewText");
        let imageList = $("#img-input");

        $.ajax({
            url: contextPath+'/page/detail/addReview/' + sno,
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                sno: sno,
                reviewText: reviewText.val(),
            }
        })
            .done(function (fragment) {
                $("#reviewList").replaceWith(fragment);
                $(".review-cnt").html(Number(cnt.text())+1);
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


    //edit Reviews
    $(".mrv-modify").on("click", function(){
        console.log("editedit");

        let rno = $(this).parent().parent().children(".rno").text();
        let editText = $(this).parent().parent().children(".mrv-textarea");

        count++;

       if (count == 1) {
            console.log("count");
            editText.removeAttr('readonly');
            editText.css('border','solid 1px #3498db');
            editText.css('cursor','text');
            editText.focus();

       } else if (count == 2) {
           console.log("countcount");

            $.ajax({
            url: contextPath + "/editReview/"+rno ,
            type:"PUT",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                 rno: rno,
                 reviewText: editText.val()
            })
            })

            .done(function (fragment) {
                $(".mrv-textarea").replaceWith(editText.val());
                //$(".mrv-textarea").replaceWith(fragment);
            });
            count = 0;
        } //end else if
    });// end edit Reviews


    //delete review
    $(".mrv-delete").on("click", function(){
       console.log("deletedelete");
       let rno = $(this).parent().parent().children(".rno").text();
       console.log(rno);

       $.ajax({
            url: contextPath + "/deleteReview/"+rno ,
            type:"DELETE",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            data: {
                rno: rno
            }
            })
            .done(function (fragment) {
                $("#reviewList").replaceWith(fragment);
                $(".review-cnt").html(Number(cnt.text())-1);
            });
    }); //delete end


    //textarea 자동 늘이기
    $('textarea').keyup(function(e){
        $(this).css('height', 'auto');
        $(this).height(this.scrollHeight);
    });

}); //end script
