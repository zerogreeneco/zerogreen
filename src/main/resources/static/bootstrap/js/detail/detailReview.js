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

// Preview for review Images
$("input[type='file']").change(function(e){
      //div 내용 비워주기
      $('#preview').empty();

      var files = e.target.files;
      var arr =Array.prototype.slice.call(files);

      preview(arr);
    });//file change


}); //end script


// Preview for review Images
function preview(arr){
    arr.forEach(function(f){

    //파일명이 길면 파일명...으로 처리
    let fileName = f.name;

    if(fileName.length > 10){
        fileName = fileName.substring(0,7)+"...";
    }

    //div에 이미지 추가
    let str = '<div style="display: inline-flex; padding: 10px;"><li>';
    str += '<span>'+fileName+'</span><br>';

    //이미지 파일 미리보기
    if(f.type.match('image.*')){
        let reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성

        reader.onload = function (e) { //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
        //str += '<button type="button" class="delBtn" value="'+f.name+'" style="background: red">x</button><br>';
        str += '<img src="'+e.target.result+'" width=100 height=100>';
        str += '</li></div>';

        $(str).appendTo('#preview');
        }

        reader.readAsDataURL(f);
/*
    }else{
        str += '<img src="/resources/img/fileImg.png" title="'+f.name+'" width=100 height=100 />';
        $(str).appendTo('#preview');
*/
        }
    });//arr.forEach
} //end function(preview)


/*
function showResult(uploadResultArr1){
    var uploadUL = $(".ul1");
    var str = "";
    $(uploadResultArr1).each(function(i,obj){
        str += "<li data-name='" + obj.fileName + "' data-path='"
            + obj.sfolderPath + "' data-uuid='"+obj.suuid+"'>";
        str += "<div>";
        str += "<button type='button' data-file='" + obj.imageURL
            + "\' class='btn-warning btn-sm'>X</button><br>";
        str += "<img src='"+contextPath+"/storeDisplay?fileName="+obj.thumbnailURL+"'>";;
        str += "</div>";
        str += "</li>";
    });
    uploadUL.append(str);
}
*/



//이하 성공한 것들

/*
//다중파일 ** 뷰에 onchange="getPreview(event)" 추가 **
function getPreview(event){
    for(let image of event.target.files){
        let reader = new FileReader();

        reader.onload = function(event){
            let img = document.createElement("img");

            img.setAttribute("src", event.target.result);
            document.querySelector("div#preview").appendChild(img);
        };

        console.log("FR"+image);
        reader.readAsDataURL(image);
    }
}
*/


/*
//단일 파일 ** 뷰에 onchange="getPreview(this,$('#preview'))" 추가 **
function getPreview(html, expression) {
    if (html.files && html.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
        $(expression).html('<img src="'+ e.target.result +'"/>');
      }
      reader.readAsDataURL(html.files[0]);
  }
}
*/

/*
//이거 안쓸거야... 단일파일..
//onload 밖에 있어야 함
function getPreview(input, expression) {

    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function (e) {
        $(expression).attr('src', e.target.result);
      }
      reader.readAsDataURL(input.files[0]);
  }
}
//onload안에 있어야 함
$('#img-input').change(function(){
    getPreview(this, '#imgT');
});
*/
