$(document).ready(function(e){

    //리뷰 슬라이드
    $("#review-right").click(function() {
        $(".review-slider").animate({
        marginLeft: "-180px"
        }, function() {
            $(".review-slider .card:first").appendTo(".review-slider");
            $(".review-slider").css({
            marginLeft: 0
            });
        });
    });
    $("#image-left").click(function() {
        $(".review-slider .card:last").prependTo(".review-slider");
        $(".review-slider").css({ "marginLeft": "-180px"});
        $(".review-slider").animate({ marginLeft: 0 });
    });

}); //end script

