window.onload = function () {

    const slides = document.querySelector('.slider');
    const slideImg = document.querySelectorAll('.shopList-box li');
    let currentIdx = 0;
// index
    const slideCount = slideImg.length;
    const prev = document.querySelector('#prev');
    const next = document.querySelector('.next');
    const slideWidth = 300;
    const slideMargin = 100;

    slides.style.width = (slideWidth + slideMargin) * slideCount + "px";

    function moveSlide(num) {
        slides.style.left = -num * 400 + 'px';
        currentIdx = num;
    }

    prev.addEventListener('click', function () {
        alert("이전");
        if (currentIdx !== 0) moveSlide(currentIdx - 1);
    });
    next.addEventListener('click', function () {
        alert("다음");
        if (currentIdx !== slideCount - 1) {
            moveSlide(currentIdx + 1);
        }
    });
}