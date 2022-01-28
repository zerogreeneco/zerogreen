$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let review = $("#rv-textarea");
    let sno = $(".js-storeId").text();

//    let username = $(".js-username").val();
//    let reviewText = $('textarea[name="reviewText"]');
//    let storeName = $(".js-storeName").text();

    $("#rv-btn").on("click", function(){
       console.log("reviewreview");
           let sno = $(".js-storeId").text();

    let username = $(".js-username").text();
    let storeName = $(".js-storeName").text();
    let reviewText = $('textarea[name="reviewText"]');

       $.ajax({
           url: contextPath+'/addReview/'+sno,
           type: "POST",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                username: username,
                storeName: storeName,
                reviewText: reviewText.val()
           }),
            success: function(data){
                console.log("result: "+data);
                let rno = parseInt(data);

                console.log("result: "+rno);
                self.location.reload();
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
    }); // end of function


}); //end script
