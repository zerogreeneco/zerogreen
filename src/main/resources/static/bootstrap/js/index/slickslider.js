$(document).ready(function() {

  // Initialise Selectric Dropdown and Slick Carousel
  $('#categories').selectric();

  $('.responsive').slick({
      // dots: true,
      infinite: true,
      speed: 300,
      slidesToShow: 5,
      slidesToScroll: 1,
      responsive: [{
          breakpoint: 1024,
          settings: {
              slidesToShow: 5,
              slidesToScroll: 1,
              // centerMode: true,

          }
      }, {
          breakpoint: 800,
          settings: {
              slidesToShow: 4,
              slidesToScroll: 2,
              infinite: true,

          }
      }, {
          breakpoint: 600,
          settings: {
              slidesToShow: 3,
              slidesToScroll: 2,
              infinite: true,

          }
      }, {
          breakpoint: 480,
          settings: {
              slidesToShow: 2,
              slidesToScroll: 1,
              infinite: true,
          }
      }, {
          breakpoint: 320,
          settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              infinite: true,
          }
      }]
  });

  var currentIndex = 0;
  $('#categories').on('change', function() {
    currentIndex = $(this).prop('selectedIndex');
    var currentSlide = $('.responsive').slick('slickCurrentSlide');
    $('.responsive').slick('slickGoTo', parseInt(currentIndex));
  });


  $('.category .show-more-link').on('click', function() {
    $(this).next().css('display', 'block');
  });


  $('.category .close').on('click', function() {
    $(this).parent().css('display', 'none');
  });


});