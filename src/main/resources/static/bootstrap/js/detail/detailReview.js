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

    //이미지(list) 모달 띄우기 + 모달 슬라이드 ** on working **
    $(".rv-img-list").click(function () {
        let idx = $(this).index();
        let next = idx + 1;
        let prev = idx - 1;
        let imgSrc = $(this).find(".rv-imageOnly").attr("src");
        let nextImg = $(".rv-img-list").eq(next).find(".rv-imageOnly").attr("src");
        let prevImg = $(".rv-img-list").eq(prev).find(".rv-imageOnly").attr("src");

        $(".modal_content").attr("src", imgSrc);
        //console.log("imgSrc  " + imgSrc);
        $(".modal").show();

        //모달 슬라이드
        $("#modal-right").click(function() {
            $(".modal-slider").animate({marginLeft: "-700px"},
            function() {
                $(".modal-slider .card:first").appendTo(".modal-slider");
                $(".modal-slider").css({marginLeft: 0 });
                $(".modal_content").attr("src", nextImg);
            });
        });
        $("#modal-left").click(function() {
            $(".modal-slider .card:last").prependTo(".modal-slider");
            $(".modal-slider").css({ "marginLeft": "-700px"});
            $(".modal-slider").animate({ marginLeft: 0 });
            $(".modal_content").attr("src", prevImg);
        });
    }); //end 이미지 리스트 모달 띄우기 + 모달 슬라이드


    //이미지(withReview) 모달 띄우기
    $(".mImage-fr").click(function () {
        let imgSrc2 = $(this).attr("src");

        $(".modal").show();
        $(".modal_content").attr("src", imgSrc2);
    });

    //이미지 모달 닫기 (공통)
    $(".close").click(function () {
        //console.log("closeeeeee");
        $(".modal").hide();
    });


    // Preview for review Images
    $("input[type='file']").change(function(e){
          //div 내용 비워주기
          $('#preview').empty();

          let files = e.target.files;
          let arr =Array.prototype.slice.call(files);

          //업로드 가능 파일인지 체크
          for(i=0; i < files.length; i++){
            if(!checkExtension(files[i].name,files[i].size)){
            return false;
            }
          }

          preview(arr);
    });//file change


    //프리뷰 삭제
    $("#preview").on("click", "ul button", function() {
        console.log("clickyyyyyyyyyyyy");

        let previewImg = $(this).parent().parent("div");
        let target = $(e.target);
        let data = $(this).attr("value");
//        let idx = $(this).attr("data-idx");
        console.log(previewImg);
        console.log(target);
        console.log(data);
//        console.log(idx);

        previewImg.remove();

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


//업로드 가능한 파일크기, 파일
function checkExtension(fileName, fileSize){

    let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    let maxSize = 1048576;  //1MB

    if(fileSize >= maxSize){
        alert('파일 사이즈 초과');
        $("input[type='file']").val("");  //파일 초기화
        return false;
    }

    //이건 되는지 잘 모르겠음
    if(regex.test(fileName)){
        alert('업로드 불가능한 파일이 있습니다.');
        $("input[type='file']").val("");  //파일 초기화
        return false;
    }
    return true;
}



// Preview for review Images
function preview(arr){
    arr.forEach(function(f){

        //div에 이미지 추가
        let str = '<div style="display: inline-flex; padding: 10px;" class="previewImg"><ul>';

        //이미지 파일 미리보기
        if(f.type.match('image.*')){
            let reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성

            reader.onload = function (e) { //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러

                str += '<button type="button" class="previewDel" value="'+f.name+'" style="background: gray">X</button><br>';
                str += '<img src="'+e.target.result+'" width=100 height=100>';
                str += '</ul></div>';

                $(str).appendTo('#preview');
            }

            reader.readAsDataURL(f);
            }
    });//arr.forEach
} //end function(preview)


// 글자 수 제한
function limitTextInput(event) {
    let countText = $(event).parent().children().children(".text-count");
    countText.html($(event).val().length + " / 500");

    if ($(event).val().length > 500) {
        $(event).val($(event).val().substring(0, 500));
        countText.html("500 / 500");
    }
}


//리뷰 이미지 로컬 삭제
function deleteImage(event) {

    let parentChildren = $(event).parent().parent().find(".div11").find(".imgInfo");

    let id = parentChildren.children(".imgId").val();
    let rno = parentChildren.children(".rnoRno").val();
    let length = parentChildren.length;

    if (id != null) {

        for (let i = 0; i < parentChildren.length; i++) {
            let filePath = parentChildren.eq(i).children(".imgPath").val();

            (function(i) {

                $.ajax({
                    url: "/zerogreen/"+ id + "/imageDelete",
                    type: "DELETE",
                    contentType:"application/json; charset=utf-8",
                    dataType: "json",
                    async: false,
                    data: JSON.stringify({
                        filePath: filePath
                    })
                })
                .done(function (data) {
                    if (data.key === "success") {
                        //alert("이미지가 삭제되었습니다.");
                    }
                });
           })(i); //end function(i)
       }//end for statement
    } //end if statement
} //end of function deleteImage


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

