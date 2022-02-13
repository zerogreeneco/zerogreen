$(document).ready(function () {

    for (var i = 0; i <= 47; i++) {
        var hour = "";
        var min = ":00";

        if ((Math.ceil(i / 2)) < 13) {
            hour = Math.floor(i / 2);
        } else {
            hour = Math.floor(i / 2);
        }
        if ((Math.ceil(i % 2)) != 0) { <!--나머지 0 또는 1-->
            var min = ":30";
        }

        $(".startTime").append("<option value="
            + ((hour >= 10) ? hour : ('0' + hour)) + min + ">" + ((hour >= 10) ? hour : ("0" + hour)) + min + "</option>");
    }

    for (var i = 47; i >= 0; i--) {
        var hour = "";
        var min = ":00";

        if ((Math.ceil(i / 2)) < 13) {
            hour = Math.floor(i / 2);
        } else {
            hour = Math.floor(i / 2);
        }
        if ((Math.ceil(i % 2)) != 0) { <!--나머지 0 또는 1-->
            var min = ":30";
        }

        $(".closeTime").append("<option value="
            + ((hour >= 10) ? hour : ('0' + hour)) + min + ">" + ((hour >= 10) ? hour : ("0" + hour)) + min + "</option>");
    }
}); //end Update