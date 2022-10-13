const requestURL = 'http://localhost:8080/api/users'

// Get Authenticated User
function fillInformationUser() {
    fetch('http://localhost:8080/api/user')
        .then(res => res.json())
        .then(data => fillInformationAuthenticatedUser(data))
}

const fillInformationAuthenticatedUser = (post) => {
    $("#authenticatedUserTable tr").remove()
    const table = document.getElementById('authenticatedUserTable')
    const rolesNames = []
    post.roles.forEach(role => {
        rolesNames.push(role.name.substring(5))
    })
    document.getElementById('userTitle').textContent =
        post.username + ' with roles: ' + rolesNames.join(' ')
    const newRow = table.insertRow(-1)
    newRow.insertCell(0).innerText = post.id
    newRow.insertCell(1).innerText = post.username
    newRow.insertCell(2).innerText = post.lastName
    newRow.insertCell(3).innerText = post.age
    newRow.insertCell(4).innerText = post.email
    newRow.insertCell(5).innerText = rolesNames.join(' ')
}

// GET All Users
fetch(requestURL)
    .then(res => res.json())
    .then(data => getTableUser(data))


const getTableUser = (posts) => {
    fillInformationUser()
    $("#allUserTable tr").remove()
    const table = document.getElementById('allUserTable')
    posts.forEach(post => {
        const rolesNames = []
        post.roles.forEach(role => {
            rolesNames.push(role.name.substring(5))
        })
        const newRow = table.insertRow(-1)
        newRow.insertCell(0).innerText = post.id
        newRow.insertCell(1).innerText = post.username
        newRow.insertCell(2).innerText = post.lastName
        newRow.insertCell(3).innerText = post.age
        newRow.insertCell(4).innerText = post.email
        newRow.insertCell(5).innerText = rolesNames.join(' ')
        const cloneEditButton = document.getElementById('buttonEditForm').cloneNode(true)
        cloneEditButton.addEventListener('click', () => fillEditModal(post.id))
        newRow.insertCell(6).appendChild(cloneEditButton)
        const cloneDeleteButton = document.getElementById('buttonDeleteForm').cloneNode(true)
        cloneDeleteButton.addEventListener('click', () => fillDeleteModal(post.id))
        newRow.insertCell(7).appendChild(cloneDeleteButton)
    })
}

function fillEditModal(id) {
    $('#editModalUser').modal('show')
    fetch(requestURL + '/' + id)
        .then(res => res.json())
        .then(data => {
            document.getElementById('editId').value = data.id
            document.getElementById('editFirstName').value = data.username
            document.getElementById('editLastName').value = data.lastName
            document.getElementById('editAge').value = data.age
            document.getElementById('editEmail').value = data.email
            document.getElementById('editPassword').value = ""
        })
}

function fillDeleteModal(id) {
    $('#deleteForm').modal('show')
    fetch(requestURL + '/' + id)
        .then(res => res.json())
        .then(data => {
            document.getElementById('deleteID').value = data.id
            document.getElementById('deleteFirstName').value = data.username
            document.getElementById('deleteLastName').value = data.lastName
            document.getElementById('deleteAge').value = data.age
            document.getElementById('deleteEmail').value = data.email
        })
    document.getElementById('buttonDeleteUser')
        .addEventListener('click', () => deleteUser(id))
}

function getRoles(selector) {
    let collection = selector.selectedOptions
    let rolesNewUser = []
    for (let i = 0; i < collection.length; i++) {
        if (collection[i].value === '1') {
            rolesNewUser.push({
                id: 1,
                name: 'ROLE_USER'
            })
        } else if (collection[i].value === '2') {
            rolesNewUser.push({
                id: 2,
                name: 'ROLE_ADMIN'
            })
        }
    }
    return rolesNewUser
}

// POST
function createNewUser() {
    let rolesNewUser = getRoles(document.getElementById('rolesNewUser'))
    fetch(requestURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById('firstNameNewUser').value,
            lastName: document.getElementById('lastNameNewUser').value,
            age: document.getElementById('ageNewUser').value,
            email: document.getElementById('emailNewUser').value,
            password: document.getElementById('passwordNewUser').value,
            roles: rolesNewUser
        })
    })
        .then(res => res.json())
        .then(data => {
            setTimeout(() => {
                getTableUser(data)
            }, 250)
            $('.nav-tabs a[href="#userInTable"]').tab('show');
            $('.nav-tabs a[href="#newUser"]').removeClass('active')
        })
}

//PUT
function putUser() {
    let rolesNewUser = getRoles(document.getElementById('rolesEditUser'))
    fetch(requestURL, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('editId').value,
            username: document.getElementById('editFirstName').value,
            lastName: document.getElementById('editLastName').value,
            age: document.getElementById('editAge').value,
            email: document.getElementById('editEmail').value,
            password: document.getElementById('editPassword').value,
            roles: rolesNewUser
        })
    })
        .then(res => res.json())
        .then(data => {
            setTimeout(() => {
                getTableUser(data)
            }, 250)
        })
}


//DELETE
function deleteUser(id) {
    fetch(requestURL + '/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id
        })
    })
        .then(res => res.json())
        .then(data => {
            setTimeout(() => {
                getTableUser(data)
            }, 250)
        })
}