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
//       console.log(sno);
//       console.log(username);
//       console.log(storeName);

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
                //console.log("result1: "+data);
                let rno = parseInt(data);
                //console.log("result2: " +rno);
                //self.location.reload();
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
    }); // end of function addReview

    //MemberReview Listing
/*
    function getMemberReviews() {
        function formatTime(str) {
            var date = new Date(str);
            return date.getFullYear()+'/'+
            (date.getMonth() + 1)+'/' +
            date.getDate() + ' ' +
            date.getHours()+':'+
            date.getMinutes();
        } //end of formatTime

        $.getJSON(contextPath+"/reviewList/"+sno, function(arr) {
            let str = "";
            console.log("Listing"+sno);

            //Dto에서 가져오기때문에 dto에 쓰인 값으로 사용
           $.each(arr, function(idx, memberReview) {
                str += ' <div class="review-body" data-rno="'+memberReview.rno+'">';
                str += ' <b class="rno">'+memberReview.rno+'</b>';
                str += ' <b class="mrv-username">'+memberReview.username+'</b>';
                str += ' <p class="mrv-sno rv-bold">'+memberReview.sno+'</p>';
                str += ' <p class="mrv-reviewText">'+memberReview.reviewText+'</p>';
                //str += ' <p class="reviewTime">'+formatTime(memberReview.regDate)+'</p>';
                str += ' </div>';
                //console.log("review>>>>>>>>>>>"+review);
            });

            $(".rv-list").html(str);
            //console.log(">>>>>>>>>>>"+str);
        });
    } //end of getMemberReviews

    getMemberReviews();
*/



}); //end script
