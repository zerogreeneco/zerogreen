$(document).ready(function() {
    $('.shopList-box').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        pauseOnHover : true,
        prevArrow : "<button type='button' class='slick-prev'>Previous</button>",
        nextArrow : "<button type='button' class='slick-next'>Next</button>",
        draggable : true,
        responsive: [
        { breakpoint: 1400, settings: { slidesToShow: 4, slidesToScroll: 4 }},
        { breakpoint: 1200, settings: { slidesToShow: 3, slidesToScroll: 3 }},
        { breakpoint: 765, settings: { slidesToShow: 1, slidesToScroll: 1}}
        ]
    });
});
