$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let review = $("#reviewText");
    //let reviewText = $('textarea[name="reviewText"]');

    //Add reviews. 현재 텍스트만 가능
    $("#rv-btn").on("click", function(){
       console.log("reviewreview");
       console.log(sno);
       console.log(username);
       console.log(storeName);

       $.ajax({
           url: contextPath+'/addReview/'+sno,
           type: "POST",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                reviewText: review.val(),
                username: username,
                id: sno
           }),
            success: function(data){
                console.log("result1: "+data);
                let rno = parseInt(data);
                console.log("result2: " +rno);
                //self.location.reload();
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
    }); // end of function addReview



}); //end script
