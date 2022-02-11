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


    //show comment input box
    $(".srv-toAdd").on("click", function(){
        console.log("SRVSRV");

        let inputBox = $(this).parent().parent().children(".srv-input");
        let toAdd = $(this).parent().children(".srv-toAdd");

        inputBox.show();
        toAdd.attr('style',"display:none;")
    });


    // 대댓글

   $("#srv-adding").click(function () {
        console.log("cemmmmmet");

        let reviewText = $(this).parent().parent().children("#storeReviewText");
        let rno = $(this).parent().parent().parent().children(".rno").text();
        console.log("reviewText " + reviewText);
        console.log("rno " + rno);


        $.ajax({
            url: contextPath+"/page/detail/addReview/"+sno+"/"+rno,
            method: "post",
            data: {
                sno: sno,
                rno: rno,
                reviewText: reviewText.val()
            },
        })
            .done(function (fragment) {
                    console.log("plsssssss");

                $("#reviewList").replaceWith(fragment);
            })
    }); //end save comment

/*
    function nestedReviewSave(event) {
       let saveBtn = $(event);
       let reviewText = saveBtn.parent().parent().children("#storeReviewText").val();
       let rno = saveBtn.parent().parent().parent().children(".rno").text();

        $.ajax({
            url: contextPath+"/page/detail/addReview/"+sno+"/"+rno,
            method: "post",
            data: {
                sno: sno,
                rno: rno,
                reviewText: reviewText
            },
        })
            .done(function (fragment) {
                $("#reviewList").replaceWith(fragment);
            })
    } //end save comment
*/



    //edit member + store Reviews
    $(".rv-modify").on("click", function(){
        console.log("editedit");

        let rno = $(this).parent().parent().children(".rno").text();
        let editText = $(this).parent().parent().children(".rv-textarea");

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
                editText.replaceWith(editText.val());
                //$(".rv-textarea").replaceWith(fragment);
            });
            count = 0;
        } //end else if
    });// end edit member + store Reviews


    //delete member review
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
    }); //delete member Review end


    //delete store review
    $(".srv-delete").on("click", function(){
       console.log("deletedelete");
       let srno = $(this).parent().parent().children(".srno").text();
       console.log(srno);

       $.ajax({
            url: contextPath + "/deleteReview/"+srno ,
            type:"DELETE",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            data: {
                rno: srno
            }
            })
            .done(function (fragment) {
                $("#reviewList").replaceWith(fragment);
            });
    }); //delete store Review end


    //textarea 자동 늘이기
    $('textarea').keyup(function(e){
        $(this).css('height', 'auto');
        $(this).height(this.scrollHeight);
    });

}); //end script
