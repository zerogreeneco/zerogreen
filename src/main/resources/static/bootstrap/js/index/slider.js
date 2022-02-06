            $(document).ready(function() {
            <!--slick slider JS-->
            $('.visual').slick({
              slidesToShow: 5,
              slidesToScroll: 5,
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

            var filtered = false;

            $('.js-filter').on('click', function(){
              if (filtered === false) {
                $('.visual').slick('slickFilter',':even');
                $(this).text('Unfilter Slides');
                filtered = true;
              } else {
                $('.visual').slick('slickUnfilter');
                $(this).text('Filter Slides');
                filtered = false;
              }
            });

            <!--Lunch slot JS-->
            });