const url = 'http://localhost:8080/restAdmin';

async function getAllUsers() {
    await new Promise(resolve => setTimeout(resolve, 200));
    try {
        const res = await fetch(url);
        const data = await res.json();
        fillTable(data);
    } catch (error) {
        console.error(error);
    }
}

function fillTable(users) {
    let result = '';
    for (let user of users) {
        result +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>
                    <button class="btn btn-info"
                    data-toggle="modal"
                    data-target="#userManager"
                    onclick="event.preventDefault(); userManager(${user.id}, 'edit')">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger"
                    data-toggle="modal"
                    data-target="#userManager"
                    onclick="event.preventDefault(); userManager(${user.id}, 'delete')">Delete</button>
                </td>
            </tr>`;
    }
    $("#allUsersTableBody").html(result);
}

function userManager(id, type) {
    fetch(url + "/get-user-details?id=" + id, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        }
    }).then(res => {
        return res.json();
    }).then(u => {
        $("div.modal-footer #confirmAction").removeAttr('class').off('click');
        $("div.modal-body input.form-control").prop('disabled', false);
        $("div.modal-body select").prop('disabled', false);
        $("#userId").val(u.id).prop('placeholder', u.id).data("user-id", u.id);
        $("#userName").val(u.username).prop('placeholder', u.username);
        $("#userFirstName").val(u.firstName).prop('placeholder',u.firstName);
        $("#userLastName").val(u.lastName).prop('placeholder', u.lastName);
        $("#userEmail").val(u.email).prop('placeholder', u.email);
        $("#userRoles option").each(function () {
            let roles = u.roles;
            let optionVal = $(this).val();
            let roleExists = roles.some(function(role) {
                return role.id.toString() === optionVal.toString();
            });

            // Если роль существует, установим атрибут "selected" для этой опции
            if (roleExists) {
                $(this).prop('selected', true);
            }
        });

        /*Форматируем для окна Edit User*/
        if (type === 'edit') {
            $("#modalLabel").text("Edit user");
            $("div.modal-body input.form-control#userId").prop('disabled', true);
            $("div.modal-footer #confirmAction")
                .addClass('btn btn-success')
                .text('Save changes')
                .click(function () {
                    event.preventDefault();
                    editUser();
                });
        }

        /*Форматируем для окна Delete User*/
        if (type === 'delete') {
            $("#modalLabel").text("Delete user");
            $("div.modal-body input.form-control").prop('disabled', true);
            $("div.modal-body select").prop('disabled', true);
            $("div.modal-footer #confirmAction")
                .addClass('btn btn-danger')
                .text('Delete')
                .click(function () {
                    event.preventDefault();
                    deleteUser(id);
                });
        }
    }).catch(error => {
        console.error(error);
    })
}

async function deleteUser(id) {
    let user = {
        id: id
    }
    try {
        await fetch(url + "/delete-user", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(user)
        });
        $("#userManager").modal('hide');
        await getAllUsers();
    } catch (error) {
        console.error(error);
    }

}

async function createUser() {
    let userRoles = []
    $("#newUserRoles option").each(
        function () {
            if ($(this).prop('selected')) {
                userRoles.push({
                    id: $(this).val()
                })
            }
        })
    let user = {
        username: $("#newUsername").val(),
        firstName: $("#newUserFirstName").val(),
        lastName: $("#newUserLastName").val(),
        email: $("#newUserEmail").val(),
        password: $("#newUserPassword").val(),
        roles: userRoles
    }
    console.log(user);
    fetch(url + "/create-user", {
        method: 'POST',
        headers: {
            /*'Accept': 'application/json',*/
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(user)
    }).then(() => getAllUsers())
}

async function clearForm() {
    $("#newUserTab form input.form-control").val("");
    $("newUserTab form select option").prop('selected', false);
}

$(document).ready(function () {
    getAllUsers().then(() => {

    }).catch(error => {
        console.error(error);
    });
})

$("#createUserButton").click(function () {
    createUser().then(() => {
        clearForm().then(() => {
            getAllUsers().catch(error => {
                console.error();
            })
        })
    })
});


function attemptAddUserMessage(result, error) {
    if (result === 'success') {
        $("#messageAfterAddingAttempt").modal('show')
        $("#messageAfterAddingAttempt div.modal-header").addClass("bg-success");
        $("#messageAfterAddingAttempt div.modal-header h5.modal-title").text("Success!");
        $("#messageAfterAddingAttempt div.modal-body p").text("User has been successfully added to data base.");
    }

    if (result == 'fail') {
        $("#messageAfterAddingAttempt").modal('show')
        $("#messageAfterAddingAttempt div.modal-header").addClass("bg-danger");
        $("#messageAfterAddingAttempt div.modal-header h5.modal-title").text("Attention!");
        $("#messageAfterAddingAttempt div.modal-body p").text(error);
    }

    $("#messageAfterAddingAttemptBackButton").click(function () {
        getAllUsers().then(function () {
            location.href = "/admin";
        })
    });
}