$(document).ready(function() {
    let page = 1;
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

    //탑 버튼
    $("#btnTop").click(function () {
        $('html, body').animate({scrollTop: 0}, '400');
    });

    // 더보기
    $("#nextShop").click(function () {
        ++page;
        // let keyword = getParameterByName("keyword");
        // let category = getParameterByName("category");
        // let searchType = getParameterByName("searchType");

        $.ajax({
            url: "/zerogreen/shop/list",
            method: "post",
            data: {
                page: page
                // category: category,
                // keyword: keyword,
                // searchType: searchType
            }
        })
            .done(function (fragment) {
                $("#shop-align").append(fragment);
            });
    });

    // 더보기
    $("#nextFood").click(function () {
        ++page;
        // let keyword = getParameterByName("keyword");
        // let category = getParameterByName("category");
        // let searchType = getParameterByName("searchType");

        $.ajax({
            url: "/zerogreen/food/list",
            method: "post",
            data: {
                page: page
                // category: category,
                // keyword: keyword,
                // searchType: searchType
            }
        })
            .done(function (fragment) {
                $("#food-align").append(fragment);
            });
    });
});