const url = 'http://localhost:8080/restUser';


function getUserPage() {
    fetch(url)
        .then(response => response.json())
        .then(user =>
            fillTable(user));
}
function fillTable(user) {
    let result = '';
    result =
        `<tr>
            <td  class="align-middle">${user.id}</td>
            <td  class="align-middle">${user.username}</td>
            <td  class="align-middle">${user.firstName}</td>
            <td  class="align-middle">${user.lastName}</td>
            <td  class="align-middle">${user.email}</td>
            <td  class="align-middle">`;
             user.roles.forEach(r => {
                 result += `<p>${r.rolename.substring(5)}</p>`;
             });
             result +=`</td>
                    </tr>`;
    $("#tableBody").html(result);
}

$(document).ready(function () {
    getUserPage();
})