// Переключение табов при загрузке страницы
$(document).ready(function () {
    var activeTab = localStorage.getItem('activeTab');
    if (activeTab) {
        $("#bodyNav button.nav-link").removeClass("active");
        $("#" + activeTab + "Tab").addClass("active");

        $("#bodyTabContent div.tab-pane").removeClass("show active");
        $("#" + activeTab).addClass("show active");
    }
});

// Обработка клика по табу
$("#bodyNav button.nav-link").click(function () {
    // Убираем класс "active" с всех кнопок навигации
    $("#bodyNav button.nav-link").removeClass("active");

    // Добавляем класс "active" только к нажатой кнопке
    $(this).addClass("active");

    var targetTab = $(this).data("tab");

    // Убираем классы "show" и "active" со всех вкладок
    $("#bodyTabContent div.tab-pane").removeClass("show active");

    // Добавляем классы "show" и "active" к целевой вкладке
    $("#" + targetTab).addClass("show active");

    // Сохраняем активный таб в локальное хранилище
    localStorage.setItem('activeTab', targetTab);
});

$("#bodyTabContent button.btn").click(function () {
    let action = $(this).data("user-action");
    if (action === 'delete') {
        $("#modalLabel").text("Подтвердите удаление пользователя")
    }

    if (action === 'edit') {
        $("#modalLabel").text("Внесите изменения")
    }
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