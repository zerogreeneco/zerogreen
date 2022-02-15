$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let cnt = $(".review-cnt");
    let sno = $(".js-storeId").text();
    let count = 0;



    //show comment input box
    $(".srv-toAdd").on("click", function(){
        let inputBox = $(this).parent().parent().children(".srv-input");
        let toAdd = $(this).parent().children(".srv-toAdd");

        inputBox.show();
        toAdd.attr('style',"display:none;");
    });


    // 대댓글 추가
   $(".srv-adding").click(function () {
        let parent = $(this).parent().parent()
        let reviewText = parent.children("#storeReviewText");
        let rno = parent.parent().children(".rno").text();
        let btn = parent.parent().children().children(".srv-toAdd");

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
                $("#reviewList").replaceWith(fragment);
            })
            //btn.attr('style',"display:none;");
    }); //end save comment


    //edit member reviews
    $(".mrv-modify").on("click", function(){
        let rno = $(this).parent().parent().children(".rno").text();
        let editText = $(this).parent().parent().children(".mrv-textarea");
        let deleteBtn = $(this).parent().children(".rv-delete");
        count++;

       if (count == 1) {
            console.log("count");
            editText.removeAttr('readonly');
            editText.css('border','solid 1px #3498db');
            editText.css('cursor','text');
            deleteBtn.css('display','none');
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
                editText.replaceWith("<textarea class='mrv-textarea' name='reviewText' readonly>" + editText.val() + "</textarea>");
            });
            count = 0;
        } //end else if
    });// end edit member + store Reviews

    // edit store Reviews
    $(".srv-modify").on("click", function(){
        let rno = $(this).parent().parent().children(".rno").text();
        let editText = $(this).parent().parent().children(".storeReviewText");
        let deleteBtn = $(this).parent().children(".rv-delete");
        count++;

       if (count == 1) {
            console.log("count");
            editText.removeAttr('readonly');
            editText.css('border','solid 1px #3498db');
            editText.css('cursor','text');
            deleteBtn.css('display','none');
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
                editText.replaceWith("<textarea class='storeReviewText' name='storeReviewText' readonly>" + editText.val() + "</textarea>");
            });
            count = 0;
        } //end else if
    });// end edit member + store Reviews


    //delete member review
    $(".mrv-delete").on("click", function(){
       let rno = $(this).parent().parent().children(".rno").text();

       $.ajax({
            url: contextPath + "/deleteReview/"+rno ,
            type:"DELETE",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                rno: rno
            })
            })
            .done(function (fragment) {
                //$("#reviewList").replaceWith(fragment);
                $(".review-cnt").html(Number(cnt.text())-1);
                self.location.reload();
            });
    }); //delete member Review end


    //delete store review
    $(".srv-delete").on("click", function(){
       let srno = $(this).parent().parent().children(".srno").text();

       $.ajax({
            url: contextPath + "/deleteReview/"+srno ,
            type:"DELETE",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                rno: srno
            })
            })
            .done(function (fragment) {
                self.location.reload();
                //$("#reviewList").replaceWith(fragment);
            });
    }); //delete store Review end


    //textarea 자동 늘이기
    $('textarea').on('keyup',function (e) {
        $(this).css('height', 'auto');
        $(this).height(this.scrollHeight);
    });
    $('textarea').keyup();


    //리뷰이미지 슬라이드
    $("#image-right").click(function() {
        $(".image-slider").animate({marginLeft: "-130px"},
        function() {
            $(".image-slider .card:first").appendTo(".image-slider");
            $(".image-slider").css({marginLeft: 0 });
        });
    });
    $("#image-left").click(function() {
        $(".image-slider .card:last").prependTo(".image-slider");
        $(".image-slider").css({ "marginLeft": "-130px"});
        $(".image-slider").animate({ marginLeft: 0 });
    });


    //탑 버튼
    $("#top-btn").click(function () {
        $('html, body').animate({scrollTop: 0}, '400');
    });


    //이미지 모달
    let modal = $(".modal");
    let close = $(".close");
    let modalImage = $(".modal_content");
    let imageList = $(".rv-imageOnly");
    let mImage = $(".mImage-fr");

    //이미지(list) 모달 띄우기 ** on working **
    $(".rv-img-list").click(function () {
        let imgSrc = $(this).children().attr("src");
        let imgEach = $(this).attr("each");

        $(".modal").show();
        $(".modalEach").attr("each", imgEach);
        $(".modal_content").attr("src", imgSrc);
    });

    //이미지(withReview) 모달 띄우기
    $(".mImage-fr").click(function () {
        let imgSrc2 = $(this).attr("src");

        $(".modal").show();
        $(".modal_content").attr("src", imgSrc2);
    });

    //이미지 모달 닫기
    $(".close").click(function () {
        console.log("closeeeeee");
        $(".modal").hide();
    });

    //모달이미지 슬라이드
    $("#modal-right").click(function() {
        $(".modal-slider").animate({marginLeft: "-700px"},
        function() {
            $(".modal-slider .card:first").appendTo(".modal-slider");
            $(".modal-slider").css({marginLeft: 0 });
        });
    });
    $("#modal-left").click(function() {
        $(".modal-slider .card:last").prependTo(".modal-slider");
        $(".modal-slider").css({ "marginLeft": "-700px"});
        $(".modal-slider").animate({ marginLeft: 0 });
    });


    // save Reviews
/*
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
*/


}); //end script
