$(document).ready(function () {
    $("#search").click(function () {
        $.get(jsRoutes.controllers.SearchController.searchCountry($("#country-code").val()), function (data) {
            $("#search-result").html(data);
            console.log(data)
        });
    });

    function getCountryCount() {
        $.get(jsRoutes.controllers.SearchController.countriesWithAirports(), function (data) {
            $("#option-result").html(data);
        });
    }

    function getTypeOfRunways() {
        $.get(jsRoutes.controllers.SearchController.runways(), function (data) {
            $("#option-result").html(data);
        });
    }

    function frequentRunways() {
        $.get(jsRoutes.controllers.SearchController.frequentRunways(), function (data) {
            $("#option-result").html(data);
        });
    }

    $("#select-option").change(function () {
        var v = $("#select-option").val();
        if (v == "1") getCountryCount();
        else if (v == "2") getTypeOfRunways();
        else if (v == "3") frequentRunways();
    });
    });
