$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let count = 0;


    //add StoreReviews
    $(".srv-toAdd").on("click", function(){
        console.log("SRVSRV");

        let srvText = $(this).parent().parent().children(".srv-input");
        let toAdd = $(".srv-toAdd");

        srvText.removeAttr('style',"display:none;");
        toAdd.attr('style',"display:none;")

        $("#srv-adding").on("click", function(){
           let parent = $(this).parent().parent().parent();
           let rno = parent.children(".rno").text();
           let review = $(".srv-input-textarea");

           console.log("storeReview");
           console.log(rno);

           $.ajax({
               url: contextPath+'/addStoreReview/'+rno,
               type: "POST",
               contentType:"application/json; charset=utf-8",
               dataType: "json",
               //data 필드명이랑 무슨 변수명이랑 맞춰줘야하는지 모르겠음
               data: JSON.stringify({
                    storeReviewText: review.val(),
                    sno: sno,
                    rno: rno
               }),
                success: function(data){
                    let srno = parseInt(data);
                    console.log("storeReview: " +srno);
                   console.log("storeReview added");

                    //self.location.reload();
                },
                error: function(data){
                    alert("errorrrrrrrrrrrrrrrr");
                }
            }); // end of ajax
        }); // end of function addReview
    }); //end add StoreReviews


    //delete reviews
    $(".srv-delete").on("click", function(){
       console.log("deletedelete");
       let srno = $(this).parent().parent().children(".srno").text();
       console.log(srno);

       $.ajax({
            url: contextPath + "/deleteStoreReview/"+sno+"/"+srno ,
            type:"DELETE",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                id: srno
            }),
            success: function(data){
               console.log("success");
                ///self.location.reload();
            },
            error:function(data){
                alert("delete_errorrrrrrrrrrrrrrrr");
                console.log(srno);
                console.log("result: " + data);
            }
        }); //ajax end
    }); //delete end


    //edit Reviews
    $(".srv-modify").on("click", function(){
        console.log("editedit");

        let parent = $(this).parent().parent().parent();

        let srno = $(this).parent().parent().children(".srno").text();
        let editText = $(this).parent().parent().children(".srv-textarea");
        let rno = parent.children(".rno").text();

        count++;

       if (count == 1) {
            console.log("count");
            editText.removeAttr('readonly');
            editText.css('border','solid 1px #3498db');
            editText.css('cursor','text');
            editText.focus();

       } else if (count == 2) {
           console.log("countcount");
           console.log(sno);
           console.log(rno);
           console.log(srno);
            //잘은 모르겠는데 컨트롤러에서 dto에서 값을 가져와서 필드값도 dto에 맞추는걸까?
            $.ajax({
            url: contextPath + "/modifyStoreReview/"+srno ,
            type:"PUT",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                 srno: srno,
                 storeReviewText: editText.val(),
                 rno: rno,
                 sno: sno
            }),
            success: function(data){
                    console.log("result111: " + data);
                    console.log("edit success");
                    self.location.reload();
                },
            error:function(data){
                console.log("edit failed");
            }
            }); //end ajax
            count = 0;
        } //end else if
    });// end edit Reviews



}); //end script
