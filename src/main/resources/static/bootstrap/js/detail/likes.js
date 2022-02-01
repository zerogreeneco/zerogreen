$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let liking = $(".liking");
    let lno = $(".lno").text();
    let cnt = $(".like-cnt");

    //Like status
    if (lno == "") {
        $(".liking").html("♡♡♡♡♡");
    } else if (lno != ""){
      $(".liking").html("☆☆☆☆☆");
    }

    //Like
    $(".liking").on("click", function(){
       if(liking.text() == "♡♡♡♡♡") {
       //let cnt = 0;
       console.log("likelike");

       $.ajax({
           url: contextPath+'/addLikes/'+sno,
           type: "POST",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                username: username,
                id: sno
           }),
            success: function(data){
                //console.log("result: "+data);
                let id = parseInt(data);
                //console.log("result: "+id);
                //self.location.reload();
                $(".liking").html("☆☆☆☆☆");
                $(".like-cnt").html(Number(cnt.text())+1);
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
       }); // end of ajax
       } // end of If statement

       //Unlike
       else if (liking.text() == "☆☆☆☆☆") {

       $.ajax({
           url: contextPath+'/deleteLikes/'+sno+'/'+lno,
           type: "DELETE",
           contentType:"application/json; charset=utf-8",
           dataType: "json",
           data: JSON.stringify({
                id: lno
           }),
            success: function(data){
                console.log("result: "+data);
                //self.location.reload();
            },
            error: function(data){
                //alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
            $(".liking").html("♡♡♡♡♡");
            $(".like-cnt").html(Number(cnt.text())-1);
        } //end of else if statement
    }); // end of function

}); //end script
