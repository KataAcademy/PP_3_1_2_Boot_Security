const url = 'http://localhost:8080/restAdmin';

$(document).ready(function () {
    getAllUsers().then(() => {

    }).catch(error => {
        console.error(error);
    });
})

async function getAllUsers() {
    setTimeout(() => {
        fetch(url)
            .then(res => res.json())
            .then(data => {
                fillTable(data)
            })
    }, 200)
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

async function editUser() {
    let form = $("#detailsForm");
    let editId = $("#userId").val();
    let editUsername = $("#userName").val();
    let editFirstName = $("#userFirstName").val();
    let editLastName = $("#userLastName").val();
    let editEmail = $("#userEmail").val();
    let roles = [];
    for (let i = 0; i < form.find("#userRoles").prop('options').length; i++) {
        if (form.find("#userRoles").prop('options')[i].selected) {
            let temp = {};
            temp["id"] = form.find("#userRoles").prop('options')[i].value;
            roles.push(temp);
        }
    }
    let user = {
        id: editId,
        username: editUsername,
        firstName: editFirstName,
        lastName: editLastName,
        email: editEmail
    }
    await fetch(url + "/edit-user", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(user)
        }).then(() => {
            $("#userManager").modal('hide'); //закрываем окно, используя Bootstrap метод
        }).then(() => {
            getAllUsers();
        }).catch(error => {
        console.error(error);
    });
}
