$(document).ready(function() {
    if (window.location.href == "http://localhost:8080/zerogreen/food/list") {
        $("label:first").css('background', '#16a085');
        $("label:first").css('color', '#fff');
    }
    if (window.location.href.indexOf("list?page") > -1) {
        $("label:first").css('background', '#16a085');
        $("label:first").css('color', '#fff');
    }
    if (window.location.href.indexOf("VEGAN_FOOD") > -1) {
        $("label").eq(1).css('background', '#16a085');
        $("label").eq(1).css('color', '#fff');
    }
    if (window.location.href.indexOf("GENERAL_FOOD") > -1) {
        $("label").eq(2).css('background', '#16a085');
        $("label").eq(2).css('color', '#fff');
    }

    //검색
    $('.search_icon').click(function (e) {
        $('#shopSearchForm').submit();
    });

    //검색어 비우기
    $('.truncate_icon').click(function () {
        $('#search-input').val('');
    });
});