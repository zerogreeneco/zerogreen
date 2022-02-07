$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();
    let review = $("#reviewText");
    let cnt = $(".review-cnt");
    let count = 0;
    //let rno = $(".rno").text();

    //textarea 자동 늘이기
    $('textarea').keyup(function(e){
        $(this).css('height', 'auto');
        $(this).height(this.scrollHeight);
    });


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
           })
        })

        .done (function(fragment){
            console.log("donednone "+ fragment);
            $("#rv-list").replaceWith(fragment);
            $(".review-cnt").html(Number(cnt.text())+1);

            //self.location.reload();
        });
    }); // end of function addReview

/*
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
                $(".review-cnt").html(Number(cnt.text())+1);

                //self.location.reload();
            },
            error: function(data){
                alert("errorrrrrrrrrrrrrrrr");
            }
        }); // end of ajax
    }); // end of function addReview
*/


    //delete review
    $(".mrv-delete").on("click", function(){
       console.log("deletedelete");
       let rno = $(this).parent().parent().children(".rno").text();
       console.log(rno);

       $.ajax({
            url: contextPath + "/deleteReview/"+sno+"/"+rno ,
            type:"DELETE",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: JSON.stringify({
                id: rno
            }),
            success: function(data){
                $(".review-cnt").html(Number(cnt.text())-1);
                ///self.location.reload();
            },
            error:function(data){
                alert("delete_errorrrrrrrrrrrrrrrr");
                console.log(rno);
                console.log("result: " + data);
            }
        }); //ajax end
    }); //delete end


    //edit Reviews
    $(".mrv-modify").on("click", function(){
        console.log("editedit");

        let rno = $(this).parent().parent().children(".rno").text();
        let editText = $(this).parent().parent().children(".mrv-textarea");

        count++;

       if (count == 1) {
            console.log("count");
            editText.removeAttr('readonly');
            editText.css('border','solid 1px #3498db');
            editText.css('cursor','text');
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
                 reviewText: editText.val(),
                 username: username,
                 storeName: storeName
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
