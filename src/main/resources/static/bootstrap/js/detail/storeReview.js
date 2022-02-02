$(document).ready(function(e){
    let contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
    let sno = $(".js-storeId").text();
    let storeName = $(".js-storeName").text();
    let username = $(".js-username").text();

    //add StoreReviews
    $(".srv-toAdd").on("click", function(){
        console.log("SRVSRV");
        let parent = $(this).parent().parent().parent();
        let srvText = parent.children(".srv-input");
        let toAdd = $(".srv-toAdd");

        srvText.removeAttr('style',"display:none;");
        toAdd.attr('style',"display:none;")
    }); //end add StoreReviews



}); //end script
