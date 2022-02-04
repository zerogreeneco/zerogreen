$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let liking = $(".liking");
    let cnt = $(".like-cnt");


    $(".liking").on("click", function(){
        $.ajax({
           url: contextPath+'/detailLikes/'+sno,
           method: "POST",
           dataType: "json",
           data: {
                sno: sno
           }
        }) // end of ajax

        .done(function (data) {
        if (data.count === 1) {
            $(".liking").attr("src", "/zerogreen/bootstrap/images/like/disLike.png");
            console.log("likelike");
            console.log(data.count);
            $(".test-count").text(data.totalCount);
            console.log(data.totalCount);

        } else if (data.count === 0) {
            $(".liking").attr("src", "/zerogreen/bootstrap/images/like/like.png");
            console.log("unlikeunlike")
            $(".test-count").text(data.totalCount);
            console.log(data.totalCount);
        }
        })
    }); //end of function


/*
    $(".liking").on("click", function(){
       if(liking.text() == "♡♡♡♡♡") {
       //let cnt = 0;
       console.log("likelike");

       $.ajax({
           url: contextPath+'/detailLikes/'+sno,
           type: "POST",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: {
                sno: sno
           },
            success: function(data){
                $(".liking").html("☆☆☆☆☆");
                $(".like-cnt").html(Number(cnt.text())+1);
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
       }); // end of ajax
       } // end of If statement
   }); // end of function


       //Unlike
       $(".liking").on("click", function(){


       $.ajax({
           url: contextPath+'/detailLikes/'+sno,
           type: "DELETE",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                sno: sno
           }),
            success: function(data){
                console.log("result: "+data);
                //self.location.reload();
            },
            error: function(data){
                //alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
//            $(".liking").html("♡♡♡♡♡");
//            $(".like-cnt").html(Number(cnt.text())-1);
        } //end of else if statement
    }); // end of function
*/

}); //end script
