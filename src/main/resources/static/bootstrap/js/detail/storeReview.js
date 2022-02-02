$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let parent = $(this).parent().parent().parent();


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
           let review = $(".srv-textarea");

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



}); //end script
