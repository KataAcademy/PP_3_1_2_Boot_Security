const url = 'http://localhost:8080/restAdmin';

$(document).ready(function () {
    getAllUsers();
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

/*function deleteUser(id) {
    
}

function editUser(id) {
    
}*/

function userManager(id, type) {
    fetch(url + "?id=" + id, {
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
        $("#userId").val(u.id).prop('placeholder', u.id).data("user-id", u.id);
        $("#userName").val(u.username).prop('placeholder', u.username);
        $("#userFirstName").val(u.firstName).prop('placeholder',u.firstName);
        $("#userLastName").val(u.lastName).prop('placeholder', u.lastName);
        $("#userEmail").val(u.email).prop('placeholder', u.email);
        if (type === 'edit') {
            $("#modalLabel").text("Edit user");
            $("div.modal-body input.form-control#userId")
                .prop('disabled', true)
                .click(function () {
                    event.preventDefault();
                    editUser(u.id);
                });
            $("div.modal-footer #confirmAction").addClass('btn btn-success').text('Save changes');
        }
        if (type === 'delete') {
            $("#modalLabel").text("Delete user");
            $("div.modal-footer #confirmAction").addClass('btn btn-danger')
                .text('Delete')
                .click(function () {
                    event.preventDefault();
                    deleteUser(u.id);
                });
            $("div.modal-body input.form-control").prop('disabled', true)
        }
    }).catch(error => {
        console.error(error);
    })
}
