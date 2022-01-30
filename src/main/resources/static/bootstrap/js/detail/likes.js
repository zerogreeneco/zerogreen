$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let liking = $(".liking");

    //
    $(".liking").on("click", function(){
       console.log("likelike");

       $.ajax({
           url: contextPath+'/addLikes/'+sno,
           type: "POST",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                username: username,
                storeName: storeName
           }),
            success: function(data){
                console.log("result: "+data);
                let id = parseInt(data);
                console.log("result: "+id);
                //self.location.reload();
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");

            }
        }); // end of ajax
    }); // end of function addLikes



}); //end script
