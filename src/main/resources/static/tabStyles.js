$(document).ready(function() {
    $("#bodyNav button.nav-link").click(function() {
        // Убираем класс "active" с всех кнопок навигации
        $("#bodyNav a.nav-link").removeClass("active");

        // Добавляем класс "active" только к нажатой кнопке
        $(this).addClass("active");

        var targetTab = $(this).data("tab");

        // Убираем классы "show" и "active" со всех вкладок
        $("#bodyTabContent div.tab-pane").removeClass("show active");

        // Добавляем классы "show" и "active" к целевой вкладке
        $("#" + targetTab).addClass("show active");
    });
});

$(document).ready(function () {
    if (window.location.pathname.match("/user$")) {
        $("nav #admin-panel-link").removeClass("active");
        $("nav #user-panel-link").addClass("active");
    } else {
        $("nav #user-panel-link").removeClass("active");
        $("nav #admin-panel-link").addClass("active");
    }
})